package pages;

import game.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.75);
        int screenHeight = (int) (screenSize.getHeight() * 0.75);

        // Create components
        ImageIcon image = new ImageIcon("BitsPleaseLogo.jpg");
        this.setIconImage(image.getImage());  //change icon of frame
        JLabel nameLabel = new JLabel("Enter player names:", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 30));
        nameLabel.setBorder(new EmptyBorder(screenWidth/10,0,0,0));


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(248, 237, 212));
        titlePanel.add(nameLabel);
        add(titlePanel, BorderLayout.NORTH);

        int textFieldSize = 24;
        Font textFieldFont = new Font("Roboto", Font.PLAIN, 24);


        JLabel gapLabel = new JLabel(" ");
        nameFields = new JTextField[4];
        for (int i = 0; i < nameFields.length; i++) {
            nameFields[i] = new JTextField(textFieldSize);
            nameFields[i].setFont(textFieldFont);  // Add this line
            nameFields[i].setHorizontalAlignment(JTextField.CENTER);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(248, 237, 212));
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

            controller.showGameplayPage();
            GameData.setQuestionList(Question.retrieveAllDefaultQuestions());


        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showGameSettingsPage());
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        // Create a panel to hold the input components
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(248, 237, 212));
        inputPanel.setBorder(new EmptyBorder(screenWidth/12,screenHeight/2,screenWidth/6,screenHeight/2));

        inputPanel.setLayout(new GridLayout(4, 1));
//        inputPanel.add(gapLabel);
        int playerNum = 1;
        Font labelFont = new Font("Roboto", Font.PLAIN, 24);
        for (JTextField nameField : nameFields) {
            JLabel playerLabel = new JLabel("Player " + playerNum);
            playerLabel.setFont(labelFont);
            inputPanel.add(playerLabel);
            playerNum++;
            inputPanel.add(nameField);
        }

        // Add components to the frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null); // Center the frame on the screen
    }
}
