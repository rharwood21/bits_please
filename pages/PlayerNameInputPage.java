package pages;

import game.*;
import javax.swing.*;
import java.awt.*;

/**
 * Represents the player name input page of the Trivial Compute Game.
 * Allows the user to enter player names and navigate to the next page.
 */
public class PlayerNameInputPage extends JFrame {
    private GameController controller;
    private JTextField[] nameFields;
    /**
     * Constructs a pages.PlayerNameInputPage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public PlayerNameInputPage(GameController controller) {
        super("Choose Players");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create components
        JLabel nameLabel = new JLabel("Enter player names:");
        nameFields = new JTextField[4];
        for (int i = 0; i < nameFields.length; i++) {
            nameFields[i] = new JTextField(15);
        }

        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            PlayerData.flushPlayerNames();
            for (int i = 0; i < nameFields.length; i++) {
                String playerNameTemp = nameFields[i].getText();
                PlayerData.setPlayerName(i, playerNameTemp);
            }
            int currentPlayers = PlayerData.getPlayerCount();
            if (currentPlayers < 2 || currentPlayers > 4){
                JOptionPane.showMessageDialog(null, "Invalid Number of Players!\nPlease input 2-4 names.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Pass player names to the controller or navigate to the next page
            // Example: navigating to the question editor page
            controller.showQuestionEditorPage();
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showWelcomePage());
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        // Create a panel to hold the input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2));
        inputPanel.add(nameLabel);
        for (JTextField nameField : nameFields) {
            inputPanel.add(nameField);
        }

        // Add components to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

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
