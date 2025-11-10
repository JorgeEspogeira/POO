import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        try {
            TerminalSize tSize = new TerminalSize(40, 20);
            DefaultTerminalFactory tF = new DefaultTerminalFactory()
                                    .setInitialTerminalSize(tSize);
            Terminal t = new DefaultTerminalFactory().createTerminal();
            Screen screen = new TerminalScreen(t);
            screen.setCursorPosition(null);
            screen.startScreen();
            screen.doResizeIfNecessary();
            screen.clear();
            screen.setCharacter(10, 10, TextCharacter.fromCharacter('X')[0]);
            screen.refresh();
            Terminal terminal = tF.createTerminal();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        Game game = new Game();
        game.run();
    }
}
