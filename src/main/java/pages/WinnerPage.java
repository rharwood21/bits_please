package pages;

import game.GameController;
import game.PlayerData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the winner page of the Trivial Compute Game.
 * Displays the winner message and allows the user to restart the game or
 * navigate to the next page.
 */
public class WinnerPage extends JFrame {
   private GameController controller;
   private BufferedImage winnerImage;
   private BufferedImage image;

   /**
    * Constructs a pages.WinnerPage object.
    *
    * @param controller The game controller instance for managing the navigation.
    */
   public WinnerPage(GameController controller, int winnerIndex) {
      super("Winner Page");

      this.controller = controller;
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth = (int) (screenSize.getWidth() * 0.75);
      int screenHeight = (int) (screenSize.getHeight() * 0.75);

      // Set the layout manager
//      setLayout(new BorderLayout());
      setBackground(new Color(248, 237, 212));

      // Load the image
      try {
         winnerImage = ImageIO.read(getClass().getResource("/images/winner.png")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }

      // Load the image
      try {
         image = ImageIO.read(getClass().getResource("/images/BitsPleaseLogo.jpg")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }
      ImageIcon pageIcon = new ImageIcon(image);
      this.setIconImage(pageIcon.getImage()); // change icon of frame

      JPanel imagePanel = new JPanel() {
         @Override
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
//            setLayout(new BorderLayout());
            if (winnerImage != null) {
               int width = getWidth();
               int height = getHeight();
               int imgWidth = winnerImage.getWidth();
               int imgHeight = winnerImage.getHeight();

               // Calculate the scaled dimensions
               int newWidth, newHeight;
               if (imgWidth > imgHeight) {
                  newWidth = (int) (width/(1.5));
                  newHeight = (int) ((imgHeight * ((double) width / imgWidth))/(1.5));
               } else {
                  newWidth = (int) ((imgWidth * ((double) height / imgHeight))/(1.5));
                  newHeight = (int)(height/(1.5));
               }

               // Calculate the position to center the image
               int x = (width - newWidth) / 2;
               int y = (height - newHeight) / 2 - newHeight/4;

               // Draw the scaled image
               g.drawImage(winnerImage, x, y, newWidth, newHeight, this);
               setPreferredSize(new Dimension(newWidth, newHeight));
            }
            setBackground(new Color(248, 237, 212));

//            setBorder(new EmptyBorder(0, 0, 0, 0));
         }
      };

      JPanel textPanel = new JPanel();
      textPanel.setBackground(new Color(248, 237, 212));
      JLabel winnerLabel = new JLabel("Congratulations, "+ PlayerData.getPlayerName(winnerIndex) +"!");
      winnerLabel.setFont(new Font("Roboto", Font.BOLD, 40));
//      winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
      winnerLabel.setBackground(new Color(248, 237, 212));
      textPanel.add(winnerLabel);
      textPanel.setBorder(new EmptyBorder(screenHeight/6, 0, 0, 0));

      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(new Color(248, 237, 212));
      JButton restartButton = new JButton("Restart");
      buttonPanel.add(restartButton);
      restartButton.addActionListener(e -> {
         // Pass control to the controller or navigate to the next page
         // Example: restarting the game by going back to the welcome page
//         PlayerData.flushPlayerScores();
         this.setVisible(false);
         controller.showWelcomePage();
      });

//      JPanel p = new JPanel(new GridLayout(3,1));

      // Add components to the frame
      add(textPanel, BorderLayout.NORTH);
      add(imagePanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);

//      add(p);

      // Set frame properties
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set the size to 75% of the screen's height and width
      setSize(screenWidth, screenHeight);

      setLocationRelativeTo(null); // Center the frame on the screen
   }
}
