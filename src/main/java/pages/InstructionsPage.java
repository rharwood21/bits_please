package pages;

import game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        JLabel objectTitleLabel = new JLabel("Object of the Game", SwingConstants.CENTER);
        objectTitleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        // TODO undo hardcode
        JTextArea objectText = setTextArea("To move along the circular track and the spokes correctly answering " +
                "questions, and to collect colored wedges for correctly answering questions in each of the four category" +
                " headquarters (at the base of each spoke). To win, a player returns to the square hub and correctly " +
                "answers the game-winning question in a category chosen by the other players.");
        JLabel setupLabel = new JLabel("Game Setup", SwingConstants.CENTER);
        setupLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        JTextArea setupText = setTextArea("Select four unique categories and corresponding colors. You can select " +
                "either the default questions or use categories with questions created by teachers. Roll the die; the " +
                "high roller goes first (the system does this automatically. Al players start in the square hub at the " +
                "center of the board and move down one of the spokes and out onto the square track. Play moves in either" +
                " direction around the track, clockwise or counterclockwise. ");
        JLabel gamePlayLabel = new JLabel("Game Play", SwingConstants.CENTER);
        gamePlayLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        JLabel firstLabel = new JLabel("First Turn", SwingConstants.CENTER);
        firstLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        JTextArea firstText = setTextArea("On your first turn, roll the die and select which color /category you want" +
                " to land on and which of the four spokes you'll move down. If you roll a 4, you will immediately land " +
                "on a category headquarters and try for a scoring wedge of that color. Whichever color you land on, the " +
                "game will prompt you with a question in that category. Once you answer the question orally, choose the " +
                "option to continue to the answer. Then the other players can vote on whether you answered correctly. " +
                "Assuming the majority vote you have answered correctly, continue your turn by rolling the die again and" +
                " moving that number of spaces. ");
        JLabel notesLabel = new JLabel("Notes", SwingConstants.CENTER);
        notesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextArea notesText = setTextArea("On each roll of the die, you may select which direction you want to move " +
                "along the track as you attempt to move towards category headquarters to try for scoring wedges in each " +
                "of the four colors. \n" + "You may not move both forward and back on the track (or on a spoke) " +
                "in the same move. \n" + "You must move the number of spaces shown on the die.  \n" +
                "If you answer incorrectly, play passes to the player on your left.");
        JLabel subLabel = new JLabel("Subsequent Turns", SwingConstants.CENTER);
        subLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        JTextArea subText = setTextArea("Whenever you answer a category headquarters question correctly, the game " +
                "will put that colored wedge into your token. But if you answer incorrectly, on your next turn you must " +
                "move out of that category headquarters for a question before re-entering and trying again for that " +
                "color piece. You do not have to try that same category immediately; you may move elsewhere on the board" +
                " and return to it later. There are 4 \"Roll again\" spaces on the track. If you land on one, " +
                "continue your turn by rolling the die and moving again. \n" + "NOTE: Any number of tokens may occupy " +
                "the same space at the same time. ");
        JLabel hubLabel = new JLabel("Moving Through the Hub", SwingConstants.CENTER);
        hubLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        JTextArea hubText = setTextArea("You may cut across the board by moving your token up the spokes to the " +
                "center hub and out again, moving either straight across the hub, or \"turning\" and going down another " +
                "spoke. If you land by exact count right in the hub - but can't try to win the game because you " +
                "do not yet have color wedges in all four colors--you may pick whichever category you want for your " +
                "question. ");
        JLabel winLabel = new JLabel("Winning the Game", SwingConstants.CENTER);
        winLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        JTextArea winText = setTextArea("Once you've collected one scoring wedge in each color, make your way to the " +
                "hub and try to answer the game-winning question. You must land in the hub by exact count; if you " +
                "overshoot the hub, pick the spoke you want to move down and answer the question in the category you " +
                "land on; then, on your next move, try again to hit the hub by exact count. When you do hit the hub, " +
                "the other players select the category of the game-winning question from the next card in the box. " +
                "Answer the question correctly, and you win! Answer it incorrectly and you must wait for your next turn," +
                " leave the hub, answer a question and then re-enter the hub again - by exact count! - for another " +
                "question.");
        JTextArea note2Text = setTextArea("NOTE: Since a player continues his or her turn until a question is answered " +
                "incorrectly, it is possible for one player to move around the board and collect all six scoring wedges," +
                " then move into the hub and win the game - all on one turn. If this happens, any player who has not yet" +
                " had a turn is permitted a chance to duplicate the feat and create a tie. ");



        JPanel instPanel = new JPanel(new GridLayout(16,1));
        instPanel.setBorder(new EmptyBorder(0,screenHeight/10,0,screenHeight/10));
        instPanel.setBackground( new Color(248, 237, 212) );

        instPanel.add(objectTitleLabel);
        instPanel.add(objectText);
        instPanel.add(setupLabel);
        instPanel.add(setupText);
        instPanel.add(gamePlayLabel);
        instPanel.add(firstLabel);
        instPanel.add(firstText);
        instPanel.add(notesLabel);
        instPanel.add(notesText);
        instPanel.add(subLabel);
        instPanel.add(subText);
        instPanel.add(hubLabel);
        instPanel.add(hubText);
        instPanel.add(winLabel);
        instPanel.add(winText);
        instPanel.add(note2Text);

        JScrollPane scrollPanel = new JScrollPane(instPanel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBackground( new Color(248, 237, 212) );

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
        add(scrollPanel, BorderLayout.CENTER);
        add(returnButton, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JTextArea setTextArea(String s) {
        JTextArea textArea = new JTextArea(s);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);

        return textArea;
    }

}
