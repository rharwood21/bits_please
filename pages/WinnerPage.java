package pages;

import game.*;
import javax.swing.*;
import java.awt.*;

/**
 * Represents the winner page of the Trivial Compute Game.
 * Displays the winner message and allows the user to restart the game or navigate to the next page.
 */
public class WinnerPage extends JFrame {
    private GameController controller;
    /**
     * Constructs a pages.WinnerPage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public WinnerPage(GameController controller) {
        super("Winner Page");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create components
        ImageIcon image = new ImageIcon("BitsPleaseLogo.jpg");
        this.setIconImage(image.getImage());  //change icon of frame
        JLabel winnerLabel = new JLabel("Congratulations, Player X! You are the winner!");
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            // Pass control to the controller or navigate to the next page
            // Example: restarting the game by going back to the welcome page
            controller.showWelcomePage();
        });

        // Add components to the frame
        add(winnerLabel, BorderLayout.CENTER);
        add(restartButton, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.75);
        int screenHeight = (int) (screenSize.getHeight() * 0.75);
        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null); // Center the frame on the screen
    }
}
