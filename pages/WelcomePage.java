package pages;

import game.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents the welcome page of the Trivial Compute Game.
 * Displays a welcome message and an image.
 * Allows the user to navigate to the next page.
 */
public class WelcomePage extends JFrame {
    private GameController controller;
    private BufferedImage image;
    /**
     * Constructs a pages.WelcomePage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public WelcomePage(GameController controller) {
        super("Trivial Compute");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Load the image
        try {
            image = ImageIO.read(getClass().getResource("../resources/images/Bits_Please_Logo.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create components
        JLabel welcomeLabel = new JLabel("Welcome to Bits Please' Trivial Compute Game");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    int width = getWidth();
                    int height = getHeight();
                    int imgWidth = image.getWidth();
                    int imgHeight = image.getHeight();

                    // Calculate the scaled dimensions
                    int newWidth, newHeight;
                    if (imgWidth > imgHeight) {
                        newWidth = width;
                        newHeight = (int) (imgHeight * ((double) width / imgWidth));
                    } else {
                        newWidth = (int) (imgWidth * ((double) height / imgHeight));
                        newHeight = height;
                    }

                    // Calculate the position to center the image
                    int x = (width - newWidth) / 2;
                    int y = (height - newHeight) / 2;

                    // Draw the scaled image
                    g.drawImage(image, x, y, newWidth, newHeight, this);
                }
            }
        };

        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            // Pass control to the controller or navigate to the next page
            // Example: navigating to the player name input page
            controller.showPlayerNameInputPage();
        });
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> controller.showInstructionsPage("WELCOME"));
        buttonPanel.add(instructionsButton);
        buttonPanel.add(nextButton);

        // Add components to the frame
        add(welcomeLabel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
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
