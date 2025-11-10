import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Coin {
    private Position position;
    private char symbol;

    public Coin(int x, int y) {
        this.position = new Position(x, y);
        this.symbol = '$';  // Símbolo para a moeda
    }

    // Método para mover a moeda (desce na tela)
    public void move() {
        position.setY(position.getY() + 1);
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), String.valueOf(symbol));
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}