package pages;

import game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * The pages.InstructionsPage class represents the GUI page that displays the instructions for the Trivial Compute Game.
 * It provides information on how to play the game and navigate through different stages.
 */
public class InstructionsPage extends JFrame {
    private GameController controller;
    private BufferedImage image;
    public String returnPage;
    /**
     * Constructs an pages.InstructionsPage object.
     *
     * @param controller The game controller instance for managing the game flow.
     */
    public InstructionsPage(GameController controller) {
        super("Instructions");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create components
        ImageIcon image = new ImageIcon("BitsPleaseLogo.jpg");
        this.setIconImage(image.getImage());  //change icon of frame
        JLabel imageLabel = new JLabel();

        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        try {
            image = ImageIO.read(getClass().getResource("../resources/images/Rules_Composite.jpg"));
            ImageIcon imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Error loading image.");
            scrollPane.setViewportView(errorLabel);
        }

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
        add(scrollPane, BorderLayout.CENTER);
        add(returnButton, BorderLayout.SOUTH);

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
