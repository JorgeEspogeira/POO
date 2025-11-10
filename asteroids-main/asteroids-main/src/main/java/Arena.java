import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Asteroid> asteroids;
    private List<Coin> coins;
    private List<Monster> monsters;
    private List<Bullet> bullets;
    private int score;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
        this.asteroids = createAsteroids();
        this.coins = createCoins();
        this.monsters = createMonsters();
        this.bullets = new ArrayList<>();
        this.score = 0;
    }

    private List<Asteroid> createAsteroids() {
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int randomY = -(int)(Math.random() * 10);
            asteroids.add(new Asteroid(getRandomX(), randomY));
        }
        return asteroids;
    }

    private List<Coin> createCoins() {
        List<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int randomY = -(int)(Math.random() * 10);
            coins.add(new Coin(getRandomX(), randomY));
        }
        return coins;
    }

    private List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int randomY = -(int)(Math.random() * 10);
            monsters.add(new Monster(getRandomX(), randomY));
        }
        return monsters;
    }

    private int getRandomX() {
        return 1 + (int) (Math.random() * (width - 2));
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        for (int x = 0; x < width; x++) {
            graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
            graphics.putString(new TerminalPosition(x, 0), "-");
            graphics.putString(new TerminalPosition(x, height - 1), "-");
        }
        for (int y = 0; y < height; y++) {
            graphics.putString(new TerminalPosition(0, y), "|");
            graphics.putString(new TerminalPosition(width - 1, y), "|");
        }

        hero.draw(graphics);
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(graphics);
        }
        for (Coin coin : coins) {
            coin.draw(graphics);
        }
        for (Monster monster : monsters) {
            monster.draw(graphics);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(graphics);
        }

        graphics.putString(new TerminalPosition(1, height - 2), "Score: " + score);
    }

    public void updateAsteroids() {
        List<Asteroid> asteroidsToRemove = new ArrayList<>();

        for (Asteroid asteroid : asteroids) {
            asteroid.move();

            if (asteroid.getPosition().getY() >= height) {
                asteroidsToRemove.add(asteroid);
            }
        }

        asteroids.removeAll(asteroidsToRemove);

        for (int i = 0; i < asteroidsToRemove.size(); i++) {
            asteroids.add(new Asteroid(getRandomX(), 0));
        }
    }

    public void updateCoins() {
        List<Coin> coinsToRemove = new ArrayList<>();

        for (Coin coin : coins) {
            coin.move();

            if (coin.getPosition().getY() >= height) {
                coinsToRemove.add(coin);
            }
        }

        coins.removeAll(coinsToRemove);

        for (int i = 0; i < coinsToRemove.size(); i++) {
            coins.add(new Coin(getRandomX(), 0));
        }
    }

    public void moveMonsters() {
        List<Monster> monstersToRemove = new ArrayList<>();

        for (Monster monster : monsters) {
            monster.move();

            if (monster.getPosition().getY() >= height) {
                monstersToRemove.add(monster);
            }
        }

        monsters.removeAll(monstersToRemove);

        for (int i = 0; i < monstersToRemove.size(); i++) {
            monsters.add(new Monster(getRandomX(), 0));
        }
    }

    public void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            bullet.move();

            if (bullet.getPosition().getY() < 1) {
                bulletsToRemove.add(bullet);
            }
        }

        bullets.removeAll(bulletsToRemove);
    }

    public void checkBulletCollisions() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Asteroid> asteroidsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.getPosition().getX() == asteroid.getPosition().getX() &&
                        bullet.getPosition().getY() == asteroid.getPosition().getY()) {
                    bulletsToRemove.add(bullet);
                    asteroidsToRemove.add(asteroid);
                    score += 5;
                    break;
                }
            }
        }

        bullets.removeAll(bulletsToRemove);
        asteroids.removeAll(asteroidsToRemove);

        for (int i = 0; i < asteroidsToRemove.size(); i++) {
            asteroids.add(new Asteroid(getRandomX(), -(int)(Math.random() * 5)));
        }
    }

    public boolean checkCollisions() {
        for (Asteroid asteroid : asteroids) {
            if (hero.getPosition().getX() == asteroid.getPosition().getX() &&
                    hero.getPosition().getY() == asteroid.getPosition().getY()) {
                return true;
            }
        }
        return false;
    }

    public void checkCoinCollection() {
        List<Coin> collectedCoins = new ArrayList<>();
        for (Coin coin : coins) {
            if (hero.getPosition().getX() == coin.getPosition().getX() &&
                    hero.getPosition().getY() == coin.getPosition().getY()) {
                collectedCoins.add(coin);
                score += 10;
            }
        }
        coins.removeAll(collectedCoins);
    }

    public void processKey(KeyStroke key) {
        Position newPosition = null;

        if (key.getKeyType() == KeyType.ArrowUp) {
            newPosition = hero.moveUp();
        } else if (key.getKeyType() == KeyType.ArrowDown) {
            newPosition = hero.moveDown();
        } else if (key.getKeyType() == KeyType.ArrowLeft) {
            newPosition = hero.moveLeft();
        } else if (key.getKeyType() == KeyType.ArrowRight) {
            newPosition = hero.moveRight();
        } else if (key.getKeyType() == KeyType.Character && key.getCharacter() == ' ') {
            shoot();
        }

        if (newPosition != null && heroCanMove(newPosition)) {
            hero.setPosition(newPosition);
        }
    }

    public void shoot() {
        Position heroPos = hero.getPosition();
        bullets.add(new Bullet(heroPos.getX(), heroPos.getY() - 1));
    }

    private boolean heroCanMove(Position position) {
        int x = position.getX();
        int y = position.getY();
        return x > 0 && x < width - 1 && y > 0 && y < height - 1;
    }

    public Hero getHero() {
        return hero;
    }
}