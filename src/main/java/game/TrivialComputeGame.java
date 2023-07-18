package game;

import javax.swing.SwingUtilities;
/**
 * The entry point for the Trivial Compute Game application.
 * Initializes the game controller and starts the application.
 */
public class TrivialComputeGame {
    /**
     * The main method that serves as the entry point for the application.
     * Initializes the game controller and shows the initial GUI screen.
     *
     * @param args The command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            controller.showWelcomePage();
        });
    }
}
