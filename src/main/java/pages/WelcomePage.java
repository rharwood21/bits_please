package pages;

import game.GameController;
import game.PlayerData;
import multiplayer.GameplayController;
import multiplayer.MultiplayerException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the welcome page of the Trivial Compute Game.
 * Displays a welcome message and an image.
 * Allows the user to navigate to the next page.
 */
public class WelcomePage extends JFrame {
   private GameController controller;
   private BufferedImage image, image2;

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
         image = ImageIO.read(getClass().getResource("/images/Bits_Please_Logo.png")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }

      // Load the image
      try {
         image2 = ImageIO.read(getClass().getResource("/images/BitsPleaseLogo.jpg")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }
      ImageIcon pageIcon = new ImageIcon(image2);
      this.setIconImage(pageIcon.getImage()); // change icon of frame

      JPanel welcomePanel = new JPanel();
      welcomePanel.setLayout(new GridLayout(2, 1));
      welcomePanel.setBackground(new Color(248, 237, 212));

      JLabel welcomeLabel = new JLabel("Bits Please Presents:");
      welcomeLabel.setFont(new Font("Roboto", Font.PLAIN, 30));

      welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
      welcomeLabel.setBorder(new EmptyBorder(screenHeight / 12, 0, 0, 0));

      JLabel titleLabel = new JLabel("Trivial Compute");
      titleLabel.setFont(new Font("Roboto", Font.BOLD, 40));
      titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      titleLabel.setBorder(new EmptyBorder(0, 0, screenHeight / 20, 0));

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
            setPreferredSize(new Dimension(screenWidth / 4, screenHeight / 3));
            setBorder(new EmptyBorder(0, 0, screenHeight / 5, 0));
         }
      };

      JPanel buttonPanel = new JPanel() {
         ;
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setPreferredSize(new Dimension(screenWidth / 3, screenHeight / 3));
            setBorder(new EmptyBorder(0, (int) (screenWidth / 4), 0, (int) (screenWidth / 4)));
         }
      };
      buttonPanel.setLayout(new GridLayout(3, 1));
      buttonPanel.setBackground(new Color(248, 237, 212));

      JPanel playOptionsPanel = new JPanel(new GridLayout(1,2));
      JButton playLocallyButton = new JButton("Play Locally");
      playLocallyButton.setFont(new Font("Roboto", Font.BOLD, 20));
      playLocallyButton.addActionListener(e -> {
         // Pass control to the controller or navigate to the next page
         // Example: navigating to the game setup input page
         controller.setOnlineGame(false);
         controller.setMultiplayerController(false);
         controller.showGameSettingsPage();
      });
      JButton playOnlineButton = new JButton("Play Online");
      playOnlineButton.setFont(new Font("Roboto", Font.BOLD, 20));
      playOnlineButton.addActionListener(e -> {
         controller.initializeMultiplayerController();
         GameplayController gameplayController = controller.getMultiplayerController();

         // Prompt User to Start Game or Join Existing Game
         String[] options = {"Join a game (with code)", "Start a new game"};
         int choice = JOptionPane.showOptionDialog(null,
                 "Would you like to join a game with a code, or start a new game?",
                 "Game Option",
                 JOptionPane.DEFAULT_OPTION,
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 options,
                 options[0]);

         // Join Game with Code
         if (choice == 0){
            String nameChoice = promptForUsername();
            String roomKey = promptForRoomKey();
            try {
               GameplayController.doJoinRoom(roomKey, nameChoice);
            } catch (MultiplayerException ex){
               JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
               return;
            }

            controller.setOnlineGame(true);
            controller.setMultiplayerController(false);
            controller.showPlayerNameInputPage();
         }
         // Start New Game
         else if (choice == 1) {
            String nameChoice = promptForUsername();
            try {
               PlayerData.setPlayerName(0, nameChoice);
               GameplayController.doInitialize(nameChoice);
            } catch (MultiplayerException ex){
               JOptionPane.showMessageDialog(this, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            gameplayController.showRoomKey(true);

            controller.setOnlineGame(true);
            controller.setMultiplayerController(true);
            controller.showPlayerNameInputPage();
         }
      });
      playOptionsPanel.add(playOnlineButton);
      playOptionsPanel.add(playLocallyButton);

      JButton instructionsButton = new JButton("Instructions");
      instructionsButton.setFont(new Font("Roboto", Font.BOLD, 20)); // change the font and its size
      instructionsButton.addActionListener(e -> controller.showInstructionsPage("WELCOME"));

      // Adding a button that takes user to the teacher page where questions are
      // answered.
      JButton teacherPageButton = new JButton("Teacher Home");
      teacherPageButton.setFont(new Font("Roboto", Font.BOLD, 20)); // change the font and its size
      teacherPageButton.addActionListener(e -> controller.showQuestionEditorPage());
      // TODO: make action listener and actual page class for teacher homepage.
      buttonPanel.add(instructionsButton);
      buttonPanel.add(playOptionsPanel);
      buttonPanel.add(teacherPageButton);

      // Add components to the frame
      add(welcomePanel, BorderLayout.NORTH);
      add(imagePanel, BorderLayout.SOUTH);
      add(buttonPanel, BorderLayout.CENTER);

      // Set frame properties
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set the size to 75% of the screen's height and width
      setSize(screenWidth, screenHeight);
      getContentPane().setBackground(new Color(248, 237, 212));

      setLocationRelativeTo(null); // Center the frame on the screen
   }

   /* ********** Multiplayer Methods ********** */
   public String promptForUsername() {
      String username = null;
      while (username == null || username.trim().isEmpty()) {
         username = JOptionPane.showInputDialog(null, "Enter your public username:", "Username Prompt", JOptionPane.QUESTION_MESSAGE);
         // Handle if user presses cancel
         if (username == null) {
            int decision = JOptionPane.showConfirmDialog(null, "Username is required. Do you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (decision == JOptionPane.YES_OPTION) {
               System.exit(0);  // Or any other action you'd like to perform if they wish to exit
            }
         }
      }
      return username.trim();
   }

   public String promptForRoomKey() {
      String roomKey = null;
      while (roomKey == null || roomKey.trim().isEmpty()) {
         roomKey = JOptionPane.showInputDialog(null, "Enter your the room key for your game:", "Room Key", JOptionPane.QUESTION_MESSAGE);
         // Handle if user presses cancel
         if (roomKey == null) {
            int decision = JOptionPane.showConfirmDialog(null, "Room Key is required. Do you want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (decision == JOptionPane.YES_OPTION) {
               System.exit(0);  // Or any other action you'd like to perform if they wish to exit
            }
         }
      }
      return roomKey.trim();
   }
}
