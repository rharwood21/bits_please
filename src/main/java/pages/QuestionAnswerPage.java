package pages;

import game.*;
import javax.swing.*;
import java.awt.*;

/**
 * The question and answer page of the Trivial Compute game
 * Displays questions from category, answer is checked, opponents vote on answer's correctness
 */
public class QuestionAnswerPage extends JFrame {
    private GameController controller;
    /**
     * Constructs a pages.QuestionAnswerPage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public QuestionAnswerPage(GameController controller) {
        super("Question And Answer Page");

        this.controller = controller;

        this.setTitle("Question And Answer Page");  //sets title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setResizable(false);  //prevent frame from being resized
        ImageIcon image = new ImageIcon("/images/BitsPleaseLogo.jpg");
        this.setIconImage(image.getImage());  //change icon of frame

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create components
        JPanel topPanel = new JPanel(new GridLayout(0, 1));
        JLabel questionAnswerLabel = new JLabel("Question");
        JLabel categoryLabel = new JLabel("Category: Science");
        JLabel questionAnswer = new JLabel("What is the name of this course?");

        questionAnswerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionAnswer.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(questionAnswerLabel);
        topPanel.add(categoryLabel);

        JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel1.setBorder(BorderFactory.createTitledBorder("Answer Verbally Before Checking Answer"));
        JButton checkAnswerButton = new JButton("Check Answer");
        buttonPanel1.add(checkAnswerButton);
        JButton correct = new JButton("Correct");
        JButton incorrect = new JButton("Incorrect");

        checkAnswerButton.addActionListener(e -> {
       	   questionAnswerLabel.setText("Answer");
       	   questionAnswer.setText("Foundations of Software Engineering");
       	   remove(buttonPanel1);
           JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
           buttonPanel2.setBorder(BorderFactory.createTitledBorder("Opponents To Vote"));
           buttonPanel2.add(correct);
           buttonPanel2.add(incorrect);
           topPanel.remove(categoryLabel);
           add(buttonPanel2, BorderLayout.SOUTH);
           revalidate();
           repaint();
        });

        correct.addActionListener(e -> {
           questionAnswer.setText("You got it right.");
           revalidate();
           repaint();
        });

        incorrect.addActionListener(e -> {
           questionAnswer.setText("You answered incorrectly. Next player's turn.");
           revalidate();
           repaint();
        });

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(questionAnswer, BorderLayout.CENTER);
        add(buttonPanel1, BorderLayout.SOUTH);

        // Set the size to 50% of the screen's height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.5);
        int screenHeight = (int) (screenSize.getHeight() * 0.5);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null); // Center the frame on the screen
        //setVisible(true);
    }
}
