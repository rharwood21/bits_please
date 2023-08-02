package game;

import javax.swing.SwingUtilities;
import javax.swing.*;
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
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, can set it to another Look and Feel
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            controller.showWelcomePage();
        });
    }
}
