package pages;

import game.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Represents the gameplay page of the Trivial Compute Game.
 * Displays the gameplay content and allows the user to navigate to the next
 * page.
 */
public class GameplayPage extends JFrame {
   private GameController controller;
   private JPanel playerPanel;
   private GameBoard board = new GameBoard();
   private JButton[][] gameBoardSquares = new JButton[9][9];
   private JPanel gameBoardPanel;
   private BufferedImage image;

   /**
    * Constructs a pages.GameplayPage object.
    *
    * @param controller The game controller instance for managing the navigation.
    */
   public GameplayPage(GameController controller) {
      super("Trivial Compute");

      this.controller = controller;

      // Set the layout manager
      setLayout(new BorderLayout());

      // Load the image
      try {
         image = ImageIO.read(getClass().getResource("/images/BitsPleaseLogo.jpg")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }
      ImageIcon pageIcon = new ImageIcon(image);
      this.setIconImage(pageIcon.getImage()); // change icon of frame

      JPanel mainPanel = new JPanel(new BorderLayout());
      // Create the gameBoardPanel which will hold all of the buttons / game squares.
      // The GridLayout is passed as
      // input to hold all of the game squares as a 9 x 9 grid.
      JPanel gameBoardPanel = new JPanel(new GridLayout(0, 9));
      gameBoardPanel.setBorder(new LineBorder(Color.BLACK));
      gameBoardPanel.setBounds(0, 0, 600, 600);
      Insets squareMargin = new Insets(0, 0, 0, 0);

      // For each square in the "board" instance of the GameBoard class,
      // a graphical square will be drawn as a JButton.
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            // Ignore squares that are type="Dead". This space will be used to hold player
            // score graphics.
            // if (!board.getSquare(i,j).getType().equals("Dead")) {
            drawSquare(board.getSquare(i, j), squareMargin);
            gameBoardPanel.add(gameBoardSquares[i][j]);
            // }
         }
      }

      // Create a panel for the player names
      playerPanel = new JPanel();
      // playerPanel.setLayout(new GridLayout(1, game.PlayerData.getPlayerCount()));
      // // Set the number of columns dynamically
      playerPanel.setLayout(new GridLayout(1, 4));

      // Next Button/Panel
      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      JButton nextButton = new JButton("Next");
      nextButton.addActionListener(e -> {
         // Pass control to the controller or navigate to the next page
         // Example: navigating to the winner page
         controller.showWinnerPage();
      });
      JButton instructionsButton = new JButton("Instructions");
      instructionsButton.addActionListener(e -> controller.showInstructionsPage("GAMEPLAY"));
      buttonPanel.add(instructionsButton);
      buttonPanel.add(nextButton);
      // REMOVE BELOW ME
      JButton showAQuestion = new JButton("Show a Question");
      showAQuestion.addActionListener(e -> controller.showQuestionAnswerPage());
      buttonPanel.add(showAQuestion);
      // REMOVE ABOVE ME

      // Add component panels to the mainPanel.
      mainPanel.add(playerPanel, BorderLayout.SOUTH);
      mainPanel.add(buttonPanel, BorderLayout.NORTH);
      mainPanel.add(gameBoardPanel, BorderLayout.CENTER);

      // Add components to the frame
      add(mainPanel, BorderLayout.CENTER);

      // Set frame properties
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set the size to 75% of the screen's height and width
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth = (int) (screenSize.getWidth() * 0.75);
      int screenHeight = (int) (screenSize.getHeight() * 0.75);
      setSize(screenWidth, screenHeight);

      setLocationRelativeTo(null); // Center the frame on the screen
   }

   /**
    * The drawSquare method transforms Square objects into JButtons to be added to
    * the gameBoardSquares
    * JButton[][] private variable within the GamePlayPage class.
    * 
    * @param square       - this instance of the Square class contains all the
    *                     necessary information to illustrate a square
    *                     that represents a tile of the trivial compute gameboard.
    * @param squareMargin - Helps with the construction of the Jbuttons.
    */
   private void drawSquare(Square square, Insets squareMargin) {
      JButton squareGraphics = new JButton();
      squareGraphics.setMargin(squareMargin);

      ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
      squareGraphics.setIcon(icon);

      // Switch to assign labels to some special squares
      String squareLabelText = "";
      switch (square.getType()) {
         case "Roll":
            squareLabelText = "Roll Again";
            break;
         case "TC":
            squareLabelText = "Trivial Compute";
            break;
         case "HQ":
            squareLabelText = "HQ";
            break;
         default:
            squareLabelText = "";
      }
      squareGraphics.add(new JLabel(squareLabelText));
      squareGraphics.setHorizontalAlignment(SwingConstants.CENTER);

      // Ignoring dead squares which are not drawn. This space is used for player
      // score graphics.
      if (!square.getType().equals("Dead")) {
         switch (square.getColor()) {
            case "R":
               squareGraphics.setBackground(Color.red);
               break;
            case "Y":
               squareGraphics.setBackground(Color.yellow);
               break;
            case "B":
               squareGraphics.setBackground(Color.blue);
               break;
            case "G":
               squareGraphics.setBackground(Color.green);
               break;
            case "W":
               // Should only be the Trivial Compute Square in the middle
               squareGraphics.setBackground(Color.white);
               break;
            case "P":
               // Only the Roll Again Squares on the corners.
               squareGraphics.setBackground(Color.PINK);
               break;
            default:
               // squareGraphics.setBackground(Color.white);
         }
         squareGraphics.setOpaque(true);
         squareGraphics.setContentAreaFilled(true);
         squareGraphics.setBorderPainted(false);
      }
      // Finally, add the JButton to gameBoardSquares.
      gameBoardSquares[square.getBoardPosition().x][square.getBoardPosition().y] = squareGraphics;
   }

   // Method to update the player names in the GUI
   public void updatePlayerNames() {
      playerPanel.removeAll();

      int playerCount = PlayerData.getPlayerCount();
      playerPanel.setLayout(new GridLayout(1, playerCount));

      for (int i = 0; i < playerCount; i++) {
         String playerName = PlayerData.getPlayerName(i);
         if (playerName != null) {
            JLabel playerLabel = new JLabel(playerName);
            playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            playerPanel.add(playerLabel);
         }
      }
      // Add the playerPanel back to the frame
      // add(playerPanel, BorderLayout.SOUTH);
      playerPanel.revalidate();
      playerPanel.repaint();
   }
}
