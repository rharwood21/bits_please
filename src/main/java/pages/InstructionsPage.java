package pages;

import game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The pages.InstructionsPage class represents the GUI page that displays the instructions for the Trivial Compute Game.
 * It provides information on how to play the game and navigate through different stages.
 */
public class InstructionsPage extends JFrame {
    private final GameController controller;
    //    private BufferedImage image;
    public String returnPage;

    /**
     * Constructs an pages.InstructionsPage object.
     *
     * @param controller The game controller instance for managing the game flow.
     */
    public InstructionsPage(GameController controller) {
        super("Trivial Compute Instructions");

        this.controller = controller;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.75);
        int screenHeight = (int) (screenSize.getHeight() * 0.75);
        // Set the layout manager
        setLayout(new BorderLayout());

        JLabel objectTitleLabel = new JLabel("Object of the Game");
        objectTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // TODO undo hardcode
        JTextArea objectText = setTextArea("To move along the circular track and the spokes correctly answering " +
                "questions, and to collect colored wedges for correctly answering questions in each of the four category" +
                " headquarters (at the base of each spoke). To win, a player returns to the square hub and correctly " +
                "answers the game-winning question in a category chosen by the other players.", 3, 85);
        JLabel setupLabel = new JLabel("Game Setup");
        setupLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextArea setupText = setTextArea("Select four unique categories and corresponding colors. You can select " +
                "either the default questions or use categories with questions created by teachers. Roll the die; the " +
                "high roller goes first (the system does this automatically. Al players start in the square hub at the " +
                "center of the board and move down one of the spokes and out onto the square track. Play moves in either" +
                " direction around the track, clockwise or counterclockwise. ", 4, 85);
        JLabel gamePlayLabel = new JLabel("Game Play" + "\n ");
        gamePlayLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JTextArea gapText = setTextArea( " ",1, 85);
        JLabel firstLabel = new JLabel("First Turn");
        firstLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextArea firstText = setTextArea("On your first turn, roll the die and select which color /category you want" +
                " to land on and which of the four spokes you'll move down. If you roll a 4, you will immediately land " +
                "on a category headquarters and try for a scoring wedge of that color. Whichever color you land on, the " +
                "game will prompt you with a question in that category. Once you answer the question orally, choose the " +
                "option to continue to the answer. Then the other players can vote on whether you answered correctly. " +
                "Assuming the majority vote you have answered correctly, continue your turn by rolling the die again and" +
                " moving that number of spaces. ", 4, 85);
        JLabel notesLabel = new JLabel("Notes");
        notesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextArea notesText = setTextArea("On each roll of the die, you may select which direction you want to move " +
                "along the track as you attempt to move towards category headquarters to try for scoring wedges in each " +
                "of the four colors. \n" + "You may not move both forward and back on the track (or on a spoke) " +
                "in the same move. \n" + "You must move the number of spaces shown on the die.  \n" +
                "If you answer incorrectly, play passes to the player on your left.",5,85);
        JLabel subLabel = new JLabel("Subsequent Turns");
        subLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextArea subText = setTextArea("Whenever you answer a category headquarters question correctly, the game " +
                "will put that colored wedge into your token. But if you answer incorrectly, on your next turn you must " +
                "move out of that category headquarters for a question before re-entering and trying again for that " +
                "color piece. You do not have to try that same category immediately; you may move elsewhere on the board" +
                " and return to it later. \n" + "There are 4 \"Roll again\" spaces on the track. If you land on one, " +
                "continue your turn by rolling the die and moving again. \n" + "NOTE: Any number of tokens may occupy " +
                "the same space at the same time. ",5,85);
        JLabel hubLabel = new JLabel("Moving through the hub");
        hubLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JTextArea hubText = setTextArea("You may cut across the board by moving your token up the spokes to the " +
                "center hub and out again, moving either straight across the hub, or \"turning\" and going down another " +
                "spoke. \n" + "If you land by exact count right in the hub - but can't try to win the game because you " +
                "do not yet have color wedges in all four colors--you may pick whichever category you want for your " +
                "question. ", 5, 85);


        JPanel instPanel = new JPanel();
        instPanel.add(objectTitleLabel);
        instPanel.add(objectText);
        instPanel.add(setupLabel);
        instPanel.add(setupText);
        instPanel.add(gamePlayLabel);
        instPanel.add(gapText);
        instPanel.add(firstLabel);
        instPanel.add(firstText);
        instPanel.add(notesLabel);
        instPanel.add(notesText);
        instPanel.add(subLabel);
        instPanel.add(subText);
        instPanel.add(hubLabel);
        instPanel.add(hubText);

        // Create components
//        JLabel imageLabel = new JLabel();
//
//        JScrollPane scrollPane = new JScrollPane(imageLabel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

//        try {
//            image = ImageIO.read(getClass().getResource("/images/Rules_Composite.jpg"));
//            ImageIcon imageIcon = new ImageIcon(image);
//            imageLabel.setIcon(imageIcon);
//        } catch (IOException e) {
//            e.printStackTrace();
//            JLabel errorLabel = new JLabel("Error loading image.");
//            scrollPane.setViewportView(errorLabel);
//        }

        // Return Button
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> {
            switch (returnPage) {
                case "WELCOME" -> controller.showWelcomePage();
                case "EDITOR" -> controller.showQuestionEditorPage();
                case "GAMEPLAY" -> controller.showGameplayPage();
            }
        });

        // Add components to the frame
//        add(scrollPane, BorderLayout.CENTER);
        add(instPanel);
        add(returnButton, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JTextArea setTextArea(String s, int r, int c) {
        JTextArea textArea = new JTextArea(s, r, c);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);

        return textArea;
    }

}
