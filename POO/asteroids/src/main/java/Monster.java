import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Monster {
    private Position position;
    private char symbol;

    public Monster(int x, int y) {
        this.position = new Position(x, y);
        this.symbol = 'M';  // Símbolo para o monstro
    }

    public void move() {
        // Movimento do monstro (simplesmente desce)
        position.setY(position.getY() + 1);
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), String.valueOf(symbol));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
