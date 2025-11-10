import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Bullet {
    private Position position;
    private char symbol;

    public Bullet(int x, int y) {
        this.position = new Position(x, y);
        this.symbol = '|';  // Símbolo para a bala
    }

    // Método para mover a bala (sobe na tela)
    public void move() {
        position.setY(position.getY() - 1);  // Move para cima
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), String.valueOf(symbol));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}