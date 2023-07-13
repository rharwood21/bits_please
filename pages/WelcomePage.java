package pages;

import game.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.75);
        int screenHeight = (int) (screenSize.getHeight() * 0.75);
        
        // Set the layout manager
        setLayout(new BorderLayout());

        // Load the image
        try {
            image = ImageIO.read(getClass().getResource("../resources/images/Bits_Please_Logo.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create components
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(2,1));
        welcomePanel.setBackground(new Color(248, 237, 212));
        
        JLabel welcomeLabel = new JLabel("Bits Please Presents:");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(new EmptyBorder(screenHeight/12,0,0,0));
        
        JLabel titleLabel = new JLabel("Trivial Compute");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0,0,screenHeight/20,0));
        
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(titleLabel);
        
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
                setBackground(new Color(248, 237, 212));
                setPreferredSize(new Dimension(screenWidth/4,screenHeight/3));
	            setBorder(new EmptyBorder(0,0,screenHeight/5,0));
            }
        };

        JPanel buttonPanel = new JPanel() {;
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            setPreferredSize(new Dimension(screenWidth/3,screenHeight/3));
	            setBorder(new EmptyBorder(0,(int)(screenWidth/2.5),0,(int)(screenWidth/2.5)));
	        }
        };
        buttonPanel.setLayout(new GridLayout(3,1));
        buttonPanel.setBackground( new Color(248, 237, 212) );

        
        JButton nextButton = new JButton("Begin Gameplay");
        nextButton.addActionListener(e -> {
            // Pass control to the controller or navigate to the next page
            // Example: navigating to the game setup input page
            controller.showGameSettingsPage();
        });
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> controller.showInstructionsPage("WELCOME"));

        // Adding a button that takes user to the teacher page where questions are answered.
        JButton teacherPageButton = new JButton("Teacher Home");
        // TODO: make action listener and actual page class for teacher homepage.
        buttonPanel.add(instructionsButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(teacherPageButton);
        
        // Add components to the frame
        add(welcomePanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        setSize(screenWidth, screenHeight);
        getContentPane().setBackground( new Color(248, 237, 212) );
        
        setLocationRelativeTo(null); // Center the frame on the screen
    }
}
