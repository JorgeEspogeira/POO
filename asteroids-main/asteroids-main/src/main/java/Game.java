import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Game {
    private Screen screen;
    private Arena arena;

    public Game() throws IOException {
        // Inicializa o terminal e a tela
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();  // Inicia a tela
        screen.doResizeIfNecessary();  // Ajuste de tamanho, se necessário

        arena = new Arena(40, 20);  // Cria a arena com dimensões
    }

    // Método para desenhar a tela
    private void draw() throws IOException {
        screen.clear();  // Limpa a tela
        TextGraphics graphics = screen.newTextGraphics();  // Cria um objeto para manipulação gráfica
        arena.draw(graphics);  // Desenha a arena, o herói, os asteroides, coins e monstros
        screen.refresh();  // Atualiza a tela
    }

    // Método principal do jogo
    public void run() {
        try {
            int frameCount = 0;  // Contador de frames

            while (true) {
                draw();  // Atualiza a tela

                // FIXED: Use pollInput() instead of readInput() to not block the game
                KeyStroke key = screen.pollInput();  // Lê input sem bloquear

                if (key != null) {
                    arena.processKey(key);  // Processa a tecla pressionada

                    // Verifica se o jogador pressionou 'q' para sair
                    if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') {
                        break;
                    }
                }

                // Atualiza objetos apenas a cada 3 frames (controla velocidade de queda)
                frameCount++;
                if (frameCount % 3 == 0) {
                    arena.updateAsteroids();
                    arena.moveMonsters();
                    arena.updateCoins();
                }

                // Atualiza balas a cada frame (movimento rápido)
                arena.updateBullets();
                arena.checkBulletCollisions();

                // Verifica se o herói colidiu com um asteroide
                if (arena.checkCollisions()) {
                    System.out.println("Game Over! Você colidiu com um asteroide!");
                    break;  // Encerra o jogo em caso de colisão
                }

                // Verifica se o herói coletou uma coin
                arena.checkCoinCollection();

                // Adiciona um pequeno delay para controlar a velocidade do jogo
                Thread.sleep(50);  // 50ms delay = ~20 FPS (more responsive)

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                screen.stopScreen();  // Para a tela quando o jogo termina
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Função principal para iniciar o jogo
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}