package pages;

import game.*;
import javax.swing.*;
import java.awt.*;

/**
 * Represents the question editor page of the Trivial Compute Game.
 * Allows the user to enter a question and its answer, and navigate to the next page.
 */
public class QuestionEditorPage extends JFrame {
    private GameController controller;
    private JTextArea questionTextArea;
    private JTextField answerTextField;
    /**
     * Constructs a pages.QuestionEditorPage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public QuestionEditorPage(GameController controller) {
        super("Customize Questions");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create components
        JLabel questionLabel = new JLabel("Enter the question:");
        questionTextArea = new JTextArea(4, 20);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);

        JLabel answerLabel = new JLabel("Enter the answer:");
        answerTextField = new JTextField(15);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String question = questionTextArea.getText();
            String answer = answerTextField.getText();
            // Pass question and answer to the controller or navigate to the next page
            // Example: navigating to the gameplay page
            controller.showGameplayPage();
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showPlayerNameInputPage());
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> controller.showInstructionsPage("EDITOR"));
        buttonPanel.add(instructionsButton);
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        // Create a panel to hold the input components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 1));
        inputPanel.add(questionLabel);
        inputPanel.add(new JScrollPane(questionTextArea));
        inputPanel.add(answerLabel);
        inputPanel.add(answerTextField);

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
