package pages;

import game.*;
import javax.swing.*;
import java.awt.*;

/**
 * Represents the gameplay page of the Trivial Compute Game.
 * Displays the gameplay content and allows the user to navigate to the next page.
 */
public class GameplayPage extends JFrame {
    private GameController controller;
    private JPanel playerPanel;
    private GameBoard board = new GameBoard();

    /**
     * Constructs a pages.GameplayPage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public GameplayPage(GameController controller) {
        super("Trivial Compute");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create components
        JLabel gameLabel = new JLabel("Gameplay Page");
        JPanel mainPanel = new JPanel(new BorderLayout());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                drawSquare(board.getSquare(i,j));
            }
        }


        // Create a panel for the player names
        playerPanel = new JPanel();
        //playerPanel.setLayout(new GridLayout(1, game.PlayerData.getPlayerCount())); // Set the number of columns dynamically
        playerPanel.setLayout(new GridLayout(1, 4));

        // Next Button/Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            // Pass control to the controller or navigate to the next page
            // Example: navigating to the winner page
            controller.showWinnerPage();
        });
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> controller.showInstructionsPage("GAMEPLAY"));
        buttonPanel.add(instructionsButton);
        buttonPanel.add(nextButton);

        // Add components to the panels
        mainPanel.add(playerPanel, BorderLayout.SOUTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add components to the frame
        add(gameLabel, BorderLayout.CENTER);
        //add(playerPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.75);
        int screenHeight = (int) (screenSize.getHeight() * 0.75);
        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void drawSquare(Square square) {
        // TODO: Draw a square in the GamePanel(?) for each square. Squares contain position and color so this shouldn't be so bad.
    }

    // Method to update the player names in the GUI
    public void updatePlayerNames() {
        playerPanel.removeAll();

        int playerCount = PlayerData.getPlayerCount();
        playerPanel.setLayout(new GridLayout(1, playerCount));

        for (int i = 0; i < playerCount; i++) {
            String playerName = PlayerData.getPlayerName(i);
            if (playerName != null) {
                JLabel playerLabel = new JLabel(playerName);
                playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
                playerPanel.add(playerLabel);
            }
        }
        // Add the playerPanel back to the frame
        //add(playerPanel, BorderLayout.SOUTH);

        playerPanel.revalidate();
        playerPanel.repaint();
    }
}
