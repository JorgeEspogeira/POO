import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Asteroid {
    private Position position;  // Posição do asteroide
    private char symbol;  // Símbolo que representa o asteroide na tela

    public Asteroid(int x, int y) {
        this.position = new Position(x, y);
        this.symbol = '*';  // Símbolo padrão para asteroide
    }

    // Método para mover o asteroide
    public void move() {
        // Lógica para movimentar o asteroide (aqui ele só desce para baixo)
        position.setY(position.getY() + 1);
    }

    // Método para desenhar o asteroide na tela
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), String.valueOf(symbol));
    }

    // Getters e setters
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
