import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;

public class Hero {
    private Position position;  // Usando a classe Position para armazenar a posição do herói

    public Hero(int x, int y) {
        this.position = new Position(x, y);  // Inicializa a posição
    }

    // Getters e Setters para a posição
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    // Métodos de movimento que retornam uma nova posição
    public Position moveUp() {
        return new Position(position.getX(), position.getY() - 1);
    }

    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }

    // Método para desenhar o herói na tela
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF33"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "X"); // Representa o herói com a letra "X"
    }
}
