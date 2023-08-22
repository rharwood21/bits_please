package pages;

import game.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents the gameplay page of the Trivial Compute Game.
 * Displays the gameplay content and allows the user to navigate to the next
 * page.
 */
public class GameplayPage extends JFrame {
   private GameController controller;
   private JPanel playerPanel;
   private GameBoard board = new GameBoard();
   private JDie dice = new JDie();
   private Object[][] gameBoardSquares = new Object[9][9];  // Update to JPanel
   private PlayerPiece[] playerPieces = new PlayerPiece[PlayerData.getPlayerCount()];
   private JPanel gameBoardPanel;
   private BufferedImage image;
   private Map<Color, String> colorToCategoryMap = GameData.getColorToCategoryMap();
   private int scoreboardsCreated = 0;
   private Map<Integer, JButton[]> playerIndexToButtonArrayMap = new HashMap<>();
   private Map<Color, Integer> colorToScoreboardIndexMap = new HashMap<>();
   private static final Insets squareMargin = new Insets(0, 0, 0, 0);
   private JLabel currentPlayerLabel;
   private String currentPlayerName;
   private int currentPlayerIndex = 0;
   private ImageIcon correctIcon = new ImageIcon(getClass().getResource("/images/correct.png"));
   private ImageIcon incorrectIcon = new ImageIcon(getClass().getResource("/images/incorrect.png"));
   public enum Direction {
      UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

      private final int dx;
      private final int dy;

      Direction(int dx, int dy) {
         this.dx = dx;
         this.dy = dy;
      }

      public int getDx() {
         return dx;
      }

      public int getDy() {
         return dy;
      }
   }
   /**
    * Constructs a pages.GameplayPage object.
    *
    * @param controller The game controller instance for managing the navigation.
    */

   Color newRed = new Color(179, 27, 27);   // Deep red
   Color newBlue = new Color(26, 82, 118);  // Deep blue
   Color newGreen = new Color(26, 82, 60);  // Deep green
   Color newYellow = new Color(238, 208, 63); // Gold-toned yellow

   Map<Integer, GamePiece> indexToPlayerPiece = new HashMap<>();
   GamePiece player1 = new GamePiece(PlayerData.getPlayerColor(0));
   GamePiece player2 = new GamePiece(PlayerData.getPlayerColor(1));
   GamePiece player3 = new GamePiece(PlayerData.getPlayerColor(2));
   GamePiece player4 = new GamePiece(PlayerData.getPlayerColor(3));


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

      // Create the gameBoardPanel which will hold all of the buttons / game squares.
      JPanel mainPanel = new JPanel(new BorderLayout());
      gameBoardPanel = new JPanel(new GridBagLayout());
      gameBoardPanel.setBorder(new LineBorder(Color.BLACK));
      gameBoardPanel.setBounds(0, 0, 600, 600);

      // For each square in the "board" instance of the GameBoard class, a graphical square will be drawn as a JButton.
      for (int i = 0; i < 9; i++) {
         for (int j = 0; j < 9; j++) {
            Square square = board.getSquare(i, j);
            if ((i == 1 && j == 1) || (i == 1 && j == 5) || (i == 5 && j == 1) || (i == 5 && j == 5)) {
               // Scoreboard Cell in Gameboard
               JPanel scoreboardPanel;
               if (scoreboardsCreated < PlayerData.getPlayerCount()) { // Still Players to Add
                  scoreboardPanel = createScoreboard(PlayerData.getPlayerName(scoreboardsCreated));
               } else { // No more Players to Add
                  scoreboardPanel = new JPanel();
                  scoreboardPanel.setBackground(Color.WHITE);
               }
               addBoardComponent(gameBoardPanel, scoreboardPanel, i, j, 3, 3, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
            } else if (board.getSquare(i, j).getType().equals("Dead")) {
               // Intentionally Do Nothing
            } else {
               // Normal Game Square
               drawSquare(square, squareMargin);
               Object[] gameSquareObject = (Object[]) gameBoardSquares[i][j];
               JPanel gameSquarePanel = (JPanel) gameSquareObject[0];
               gameSquarePanel.setBorder(new LineBorder(Color.BLACK));
               addBoardComponent(gameBoardPanel, (JPanel)gameSquareObject[0], i, j, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
            }
         }
      }
      for (int i = 0; i < PlayerData.getPlayerCount(); i++) {
         playerPieces[i] = new PlayerPiece(PlayerData.getPlayerColor(i));
         int[][] playerPosition = PlayerData.getPlayerPositions(i);
         addBoardComponent(gameBoardPanel, playerPieces[i], playerPosition[0][0], playerPosition[0][1], 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
      }

      // Create a panel for the player names
      playerPanel = new JPanel();
      playerPanel.setLayout(new GridLayout(1, 4));

      // Next Button/Panel
      currentPlayerLabel = new JLabel("Current Player: " + PlayerData.getPlayerName(currentPlayerIndex));
      currentPlayerLabel.setBorder(new LineBorder(Color.BLACK));
      Font currentFont = currentPlayerLabel.getFont();
      Font newFont = new Font("Roboto", currentFont.getStyle(), 24);
      currentPlayerLabel.setFont(newFont);

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      JButton instructionsButton = new JButton("Instructions");
      instructionsButton.addActionListener(e -> controller.showInstructionsPage("GAMEPLAY"));
      // TODO: Dice ActionListener will ACTUALLY run gameplay, once we have Player board pieces
      dice.addActionListener(e -> {
         // Primary Game Loop
         dice.setBackground(Color.WHITE);
         int roll_value = dice.rollDice(); // TODO: Use this for moving player on gameboard
         dice.repaintDice();

         // Main Move-Player-Piece Loop
         runMovePlayerLoop(roll_value, new OnMovePlayerCompleteListener() {
            @Override
            public void onMovePlayerComplete(int i, int j) {
               // Get New Square
               Square newSquare = board.getSquare(i, j);

               // Main Q&A Game-Loop
               runQuestionAnswerLoop(newSquare);

               dice.setBackground(Color.YELLOW);
            }
         });
      });
      // UNCOMMENT ABOVE ME
      buttonPanel.add(currentPlayerLabel);
      buttonPanel.add(dice);
      buttonPanel.add(instructionsButton);
//      buttonPanel.add(nextButton); // TODO: Remove Me

      // Add component panels to the mainPanel.
      mainPanel.add(buttonPanel, BorderLayout.NORTH);
      mainPanel.add(gameBoardPanel, BorderLayout.CENTER);

      // TODO: commenting out bc I think its not needed?
//      mainPanel.add(playerPanel, BorderLayout.SOUTH);

      // Add components to the frame
      add(mainPanel, BorderLayout.CENTER);

      int numCategories = GameData.getUniqueCategoryCount();
      JPanel legendPanel = new JPanel(new GridLayout(1, numCategories));
      JPanel legendItemPanel;
      for (int i = 0; i < numCategories; i++) {
         Color col = GameData.getColor(i);
         String cat = GameData.getCategory(i) + " \t";
         legendItemPanel = new JPanel();
         if (col.equals(Color.RED)) {
            legendItemPanel.add(new LegendItem(newRed));
         } else if (col.equals(Color.YELLOW)) {
            legendItemPanel.add(new LegendItem(newYellow));
         } else if (col.equals(Color.BLUE)) {
            legendItemPanel.add(new LegendItem(newBlue));
         } else {
            legendItemPanel.add(new LegendItem(newGreen));
         }
         legendItemPanel.add(new JLabel(cat));
         legendPanel.add(legendItemPanel);



      }
      add(legendPanel, BorderLayout.SOUTH);


      // Set frame properties
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set the size to 75% of the screen's height and width
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int size = (int) Math.min(screenSize.getWidth(), screenSize.getHeight()) * 75 / 100;
      setSize(size, size);

      setLocationRelativeTo(null); // Center the frame on the screen

      // Initialize Hashmap
      colorToScoreboardIndexMap.put(Color.RED, 0);
      colorToScoreboardIndexMap.put(Color.YELLOW, 1);
      colorToScoreboardIndexMap.put(Color.GREEN, 2);
      colorToScoreboardIndexMap.put(Color.BLUE, 3);

      indexToPlayerPiece.put(0, player1);
      indexToPlayerPiece.put(1, player2);
      indexToPlayerPiece.put(2, player3);
      indexToPlayerPiece.put(3, player4);

      // Initialize All Players to [0, 0]
      for (int i = 0; i < PlayerData.getPlayerCount(); i++){
         setPlayerPiecePosition(i, 0, 0);
      }
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
      JPanel squareGraphics = new JPanel(new BorderLayout());  // Change to JPanel

      Dimension squareSize = new Dimension(64, 64);  // example size
      squareGraphics.setMinimumSize(squareSize);
      squareGraphics.setMaximumSize(squareSize);
      squareGraphics.setPreferredSize(squareSize);

      // Switch to assign labels to some special squares
      String squareLabelText;
      switch (square.getType()) {
         case "Roll":
            squareLabelText = "Roll Again";
            break;
         case "TC":
            squareLabelText = "<html><div style='text-align: center;'>Trivial<br>Compute</div></html>"; // HTML tags allow multi-line text
            break;
         case "HQ":
            squareLabelText = "HQ";
            break;
         default:
            squareLabelText = "";
      }
      JLabel label = new JLabel(squareLabelText);
      label.setFont(new Font("Century Gothic", Font.PLAIN, 12));

      // Wrap label in a JPanel to center it
      JPanel labelPanel = new JPanel(new GridBagLayout());
      labelPanel.setOpaque(false); // Make the JPanel transparent
      labelPanel.add(label);
      squareGraphics.add(labelPanel, BorderLayout.NORTH);

      // Create a JPanel for holding game pieces and add it to CENTER of squareGraphics
      JPanel piecesPanel = new JPanel(new GridLayout(2,2)); // This will hold the game pieces. Choose an appropriate layout.
      squareGraphics.add(piecesPanel);

      // Ignoring dead squares which are not drawn. This space is used for player score graphics.
      Color bgColor = Color.WHITE;
      if (square.getType().equals("Dead")) {
         squareGraphics.setEnabled(false);
      } else {
         if (square.getColor().equals(Color.RED)) {
            bgColor = newRed;
         } else if (square.getColor().equals(Color.YELLOW)) {
            bgColor = newYellow;
         } else if (square.getColor().equals(Color.BLUE)) {
            bgColor = newBlue;
         } else if (square.getColor().equals(Color.GREEN)) {
            bgColor = newGreen;
         } else if (square.getColor().equals(Color.WHITE)) {// Should only be the Trivial Compute Square in the middle
            bgColor = Color.WHITE;
         } else if (square.getColor().equals(Color.PINK)) {// Only the Roll Again Squares on the corners.
            bgColor = Color.WHITE;
         }
         squareGraphics.setBackground(bgColor);
         piecesPanel.setBackground(bgColor);

      }
      // Finally, add the JButton to gameBoardSquares.
      gameBoardSquares[square.getBoardPosition().x][square.getBoardPosition().y] = new Object[] {squareGraphics, piecesPanel};
   }

   private void setPlayerPiecePosition(int playerIndex, int i, int j){
      // Remove Player from Old Square
      int[] oldPosition = PlayerData.getPlayerPosition(playerIndex);
      Object[] oldSquareObj = (Object[]) gameBoardSquares[oldPosition[0]][oldPosition[1]];
      JPanel oldSquare = (JPanel) oldSquareObj[0];
      JPanel oldPiecesPanel = (JPanel) oldSquareObj[1];

      // Get Reference to Player Piece
      GamePiece playerPiece = indexToPlayerPiece.get(playerIndex);
      oldPiecesPanel.remove(playerPiece);

      // Set New Position
      PlayerData.setPlayerPositions(playerIndex, i, j);

      // Get New Square
      Object[] newSquareObj = (Object[]) gameBoardSquares[i][j];
      JPanel newSquare = (JPanel) newSquareObj[0];
      JPanel newPiecesPanel = (JPanel) newSquareObj[1];

      // Add to Board JPanel Square
      newPiecesPanel.add(playerPiece);

      oldSquare.revalidate();oldSquare.repaint();
      newSquare.revalidate();newSquare.repaint();
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
      playerPanel.revalidate();
      playerPanel.repaint();
   }

   private JPanel createScoreboard(String playerName) {
      // Create the scoreboard main panel
      JPanel scoreboard = new JPanel(new BorderLayout());
      scoreboard.setBorder(new LineBorder(Color.BLACK)); // Add a border if you want
      scoreboard.setBackground(PlayerData.getPlayerColor(scoreboardsCreated));

      // Create a label for the player's name
      JLabel playerNameLabel = new JLabel(playerName); // This is just a placeholder. Replace with the actual name when required.
      playerNameLabel.setFont(new Font("Roboto", Font.BOLD, 16));
      playerNameLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text

      // Add the player's name label to the top
      scoreboard.add(playerNameLabel, BorderLayout.NORTH);

      // Create the 4x4 grid
      JPanel grid = new JPanel(new GridLayout(2, 2));
      grid.setBorder(new EmptyBorder(10, 10, 10, 10)); // 10 pixels margin around the container
      JButton[] scoreButtons = new JButton[4];

      for (int i = 0; i < 4; i++) {
         JButton button = new JButton();
         button.setMargin(squareMargin);
         button.setBackground(Color.WHITE); // Initial color
         grid.add(button);
         scoreButtons[i] = button; // Store it for future references
      }
      playerIndexToButtonArrayMap.put(scoreboardsCreated, scoreButtons);
      scoreboardsCreated++;

      // Add the 2x2 grid to the center of the scoreboard
      scoreboard.add(grid, BorderLayout.CENTER);

      return scoreboard;
   }

   private static void addBoardComponent(
           Container container, Component component, int gridx, int gridy,
           int gridwidth, int gridheight, int anchor, int fill
   ) {
      GridBagConstraints gbc = new GridBagConstraints(
              gridx, gridy, gridwidth, gridheight, 1.0, 1.0, anchor, fill, squareMargin, 0, 0
      );
      container.add(component, gbc);
   }

   private int showQuestionAnswerPage(QuestionAnswerPage questionAnswerPanel) {
      ImageIcon pageIcon = new ImageIcon(image);
      int result = JOptionPane.showConfirmDialog(
              null,
              questionAnswerPanel,
              "Question And Answer Page",
              JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              pageIcon
      );
      return result;
   }

   private void incrementScoreboard(int playerIndex, Color squareColor) {
      JButton[] playersScoreButtons = playerIndexToButtonArrayMap.get(playerIndex);
      int buttonIndex = colorToScoreboardIndexMap.get(squareColor);
      playersScoreButtons[buttonIndex].setBackground(squareColor);
   }

   private void incrementPlayerTurn() {
      // Update Next Player's Values
      if (currentPlayerIndex == PlayerData.getPlayerCount() - 1) {
         currentPlayerIndex = -1; // Reset Current Player
      }
      currentPlayerIndex++;
      currentPlayerName = PlayerData.getPlayerName(currentPlayerIndex);
      currentPlayerLabel.setText("Current Player: " + currentPlayerName);
      dice.setBackground(Color.YELLOW);
      revalidate();
      repaint();
   }

   interface OnMovePlayerCompleteListener {
      void onMovePlayerComplete(int i, int j);
   }
   private void runMovePlayerLoop(int spacesToMove, OnMovePlayerCompleteListener listener) {
      int[] currentPosition = PlayerData.getPlayerPosition(currentPlayerIndex);
      AtomicInteger i = new AtomicInteger(currentPosition[0]);
      AtomicInteger j = new AtomicInteger(currentPosition[1]);
      final Direction[] moveDirection = {null};
      AtomicInteger remainingSpacesToMove = new AtomicInteger(spacesToMove);

      Timer timer = new Timer(500, null); // 500 ms delay
      timer.addActionListener(new ActionListener() {
         boolean showDirectionChooser = true;
         @Override
         public void actionPerformed(ActionEvent e) {
            // Check if all moves have been performed
            if (remainingSpacesToMove.get() == 0) {
               timer.stop();
               if(listener != null) {
                  listener.onMovePlayerComplete(i.get(), j.get());
               }
               return;
            }
            // Get Player Direction Choice
            if (showDirectionChooser){
               moveDirection[0] = chooseMovementDirection();
               showDirectionChooser = false;
            }
            // Validate Move and Increment in Direction
            if (isValidMove(i.get(), j.get(), moveDirection[0])){
               i.addAndGet(moveDirection[0].getDx());
               j.addAndGet(moveDirection[0].getDy());
               setPlayerPiecePosition(currentPlayerIndex, i.get(), j.get());
               remainingSpacesToMove.decrementAndGet(); // Decrement Spaces to Move
            } else {
               showDirectionChooser = true;
            }
            // Show Direction Chooser on Cross-Sections
            if (i.get() == 0 && (j.get() == 0 || j.get() == 4 || j.get() == 8)){
               showDirectionChooser = true;
            } else if (i.get() == 4 && (j.get() == 0 || j.get() == 4 || j.get() == 8)){
               showDirectionChooser = true;
            } else if (i.get() == 8 && (j.get() == 0 || j.get() == 4 || j.get() == 8)){
               showDirectionChooser = true;
            }
         }
      });
      timer.start();
   }

   private void runQuestionAnswerLoop(Square square) {
      Color squareColor = square.getColor();
      String category = (squareColor == Color.WHITE) ?
              launchChooseACategory() :
              colorToCategoryMap.get(squareColor);
      boolean isHQ = Objects.equals(square.getType(), "HQ");
      if (category != null) {
         // TODO: Don't Show Duplicate Questions
         Question randomCategoryQuestion = GameData.getRandomQuestionByCategory(category);
         QuestionAnswerPage questionAnswerPanel = new QuestionAnswerPage(randomCategoryQuestion);
         boolean isCorrectAnswer;
         while (true) { // Loop to Ensure Player Selects Answer
            showQuestionAnswerPage(questionAnswerPanel);
            try {
               isCorrectAnswer = questionAnswerPanel.isCorrectAnswerChoice();
               break;
            } catch (RuntimeException exception) {
               JOptionPane.showMessageDialog(null, "Answer must be selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
         }
         if (isCorrectAnswer) { // Increment Score
            if (isHQ) {
               PlayerData.incrementPlayerScore(currentPlayerIndex, colorToScoreboardIndexMap.get(squareColor));
               incrementScoreboard(currentPlayerIndex, squareColor);
            }
            JOptionPane.showMessageDialog(this, "CORRECT!!! :D\nAnswer: " + randomCategoryQuestion.getQuestionAnswer(), "Correct!", JOptionPane.INFORMATION_MESSAGE, correctIcon);
         } else {
            // INCORRECT
            JOptionPane.showMessageDialog(this, "INCORRECT!!! :(\nAnswer: " + randomCategoryQuestion.getQuestionAnswer(), "Incorrect!", JOptionPane.INFORMATION_MESSAGE, incorrectIcon);
            incrementPlayerTurn();
         }
      }
      // Check if Any Player is Winner
      int possibleWinner = PlayerData.checkWinConditionAndReturnPlayerIndex();
      if (possibleWinner != -1) {
         // Win Condition
         try {
            Thread.sleep(3000);
         } catch (InterruptedException exception) {/* Do Nothing*/}
         controller.showWinnerPage(possibleWinner);
      }
   }

   private String launchChooseACategory() {
      String redCategory = colorToCategoryMap.get(Color.RED);
      String blueCategory = colorToCategoryMap.get(Color.BLUE);
      String greenCategory = colorToCategoryMap.get(Color.GREEN);
      String yellowCategory = colorToCategoryMap.get(Color.YELLOW);
      JPanel choicePanel = new JPanel(new GridLayout(2,4));
      choicePanel.add(new JLabel(redCategory));
      choicePanel.add(new JLabel(blueCategory));
      choicePanel.add(new JLabel(greenCategory));
      choicePanel.add(new JLabel(yellowCategory));
      JButton redButton = new JButton();redButton.setBackground(Color.RED);
      JButton blueButton = new JButton();blueButton.setBackground(Color.BLUE);
      JButton greenButton = new JButton();greenButton.setBackground(Color.GREEN);
      JButton yellowButton = new JButton();yellowButton.setBackground(Color.YELLOW);
      choicePanel.add(redButton);choicePanel.add(blueButton);choicePanel.add(greenButton);choicePanel.add(yellowButton);
      String[] options = {redCategory, blueCategory, greenCategory, yellowCategory};
      int result = -1;
      while (result == -1){
         result = JOptionPane.showOptionDialog(
                 this,
                 choicePanel,
                 "Select a Category",
                 JOptionPane.DEFAULT_OPTION,
                 JOptionPane.QUESTION_MESSAGE,
                 null,
                 options,
                 options[0]
         );
      }
      switch (result) {
         case 0:
            return redCategory;
         case 1:
            return blueCategory;
         case 2:
            return greenCategory;
         case 3:
            return yellowCategory;
         default:
            throw new RuntimeException();
      }

   }

   private Direction chooseMovementDirection(){
      JPanel directionChooserPanel = new JPanel(new GridBagLayout());
      JButton upButton, downButton, leftButton, rightButton;
      ButtonGroup arrowButtons = new ButtonGroup();
      AtomicReference<Direction> directionChoice = new AtomicReference<>();

      // Create the Arrow Buttons with Action Listeners
      upButton = new JButton("↑");
      upButton.addActionListener(e -> directionChoice.set(Direction.UP));

      downButton = new JButton("↓");
      downButton.addActionListener(e -> directionChoice.set(Direction.DOWN));

      leftButton = new JButton("←");
      leftButton.addActionListener(e -> directionChoice.set(Direction.LEFT));

      rightButton = new JButton("→");
      rightButton.addActionListener(e -> directionChoice.set(Direction.RIGHT));

      upButton.setFont(upButton.getFont().deriveFont(Font.BOLD, 24f));
      downButton.setFont(downButton.getFont().deriveFont(Font.BOLD, 24f));
      leftButton.setFont(leftButton.getFont().deriveFont(Font.BOLD, 24f));
      rightButton.setFont(rightButton.getFont().deriveFont(Font.BOLD, 24f));

      arrowButtons.add(upButton);arrowButtons.add(downButton);arrowButtons.add(leftButton);arrowButtons.add(rightButton);
      arrowButtons.clearSelection();

      // Add Buttons to JPanel
      GridBagConstraints gbc = new GridBagConstraints(
              2, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, squareMargin, 0, 0
      );
      directionChooserPanel.add(upButton, gbc);
      gbc = new GridBagConstraints(
              2, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, squareMargin, 0, 0
      );
      directionChooserPanel.add(downButton, gbc);
      gbc = new GridBagConstraints(
              1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, squareMargin, 0, 0
      );
      directionChooserPanel.add(leftButton, gbc);
      gbc = new GridBagConstraints(
              3, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, squareMargin, 0, 0
      );
      directionChooserPanel.add(rightButton, gbc);

      while (true){
         JOptionPane.showConfirmDialog(this, directionChooserPanel, "Select Direction", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
         // TODO: Validate Based on Player Position if Direction Choice is Valid
         if (directionChoice.get() != null){
            System.out.println(directionChoice.get());
            return directionChoice.get();
         }
      }
   }

   private void sleepNSeconds(int seconds){
      try {
         Thread.sleep(seconds * 1000L);
      } catch (InterruptedException e){}
   }

   private boolean isValidMove(int i, int j, Direction direction) {
      int newI = i + direction.getDx();
      int newJ = j + direction.getDy();

      // Check if new position is out of board bounds
      if (newI < 0 || newI >= 9 || newJ < 0 || newJ >= 9) {
         return false;
      }

      Object[] newSquareObj = (Object[]) gameBoardSquares[newI][newJ];
      try {
         JPanel squareGraphics = (JPanel) newSquareObj[0];
         return squareGraphics.isEnabled();
      } catch (NullPointerException e){
         return false;
      }
   }
}
