package pages;

import game.GameController;
import game.GameData;
import game.PlayerData;
import game.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player name input page of the Trivial Compute Game.
 * Allows the user to enter player names and navigate to the next page.
 */
public class PlayerNameInputPage extends JFrame {
   private GameController controller;
   private JTextField[] nameFields;
   private JComboBox<String>[] colorCBList;

   private String[] playerNames = new String[4];
   private Color[] playerColors = new Color[4];
   private static Map<String, Color> nameToColorMap = new HashMap<>();
   private BufferedImage image;

   /**
    * Constructs a pages.PlayerNameInputPage object.
    *
    * @param controller The game controller instance for managing the navigation.
    */
   public PlayerNameInputPage(GameController controller) {
      super("Choose Players");

      this.controller = controller;

      // Set the layout manager
      setLayout(new BorderLayout());

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth = (int) (screenSize.getWidth() * 0.75);
      int screenHeight = (int) (screenSize.getHeight() * 0.75);

      // Load the image
      try {
         image = ImageIO.read(getClass().getResource("/images/BitsPleaseLogo.jpg")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }
      ImageIcon pageIcon = new ImageIcon(image);
      this.setIconImage(pageIcon.getImage()); // change icon of frame

      JLabel nameLabel = new JLabel("Enter player names:", SwingConstants.CENTER);
      nameLabel.setFont(new Font("Roboto", Font.BOLD, 30));
      nameLabel.setBorder(new EmptyBorder(screenWidth / 10, 0, 0, 0));

      JPanel titlePanel = new JPanel();
      titlePanel.setBackground(new Color(248, 237, 212));
      titlePanel.add(nameLabel);
      add(titlePanel, BorderLayout.NORTH);

      int textFieldSize = 24;
      Font textFieldFont = new Font("Roboto", Font.PLAIN, 24);

      JLabel gapLabel = new JLabel(" ");
      nameFields = new JTextField[4];
      for (int i = 0; i < nameFields.length; i++) {
         nameFields[i] = new JTextField(textFieldSize);
         nameFields[i].setFont(textFieldFont); // Add this line
         nameFields[i].setHorizontalAlignment(JTextField.CENTER);
      }

      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(new Color(248, 237, 212));
      JButton nextButton = new JButton("Next");
      nextButton.addActionListener(e -> {
         // For each new game, there shall be new playerData.
         PlayerData.flushPlayerNames();
         PlayerData.flushPlayerColors();
         int numPlayers = 0;

         for (int i = 0; i < nameFields.length; i++) {
            String playerNameTemp = nameFields[i].getText();
            // If the field is empty, don't make a player
            if (!playerNameTemp.trim().isEmpty()) {
               playerNames[i] = playerNameTemp;
               playerColors[i] = nameToColorMap.get(colorCBList[i].getSelectedItem());
               numPlayers++;
            }
         }

         // Initializing the singleton PlayerData instance with all our new player names entered by the user
         PlayerData playerData = PlayerData.getInstance(numPlayers, playerNames, playerColors);

         int currentPlayers = PlayerData.getPlayerCount();
         int uniqColors = PlayerData.getUniqueColorCount();
         System.out.println(uniqColors + " " + currentPlayers + " " + playerNames[0] + " " + playerNames[1] + " "
               + playerNames[2] + " " + playerNames[3]
               + " " + playerColors[0] + " " + playerColors[1] + " " + playerColors[2] + " " + playerColors[3]); // TODO: Remove Me

         if (currentPlayers < 2 || currentPlayers > 4) {
            JOptionPane.showMessageDialog(null, "Invalid Number of Players!\nPlease input 2-4 names.", "Error",
                  JOptionPane.ERROR_MESSAGE);
            return;
         }
         // Edge case issue: if there are two players with the same color and the empty
         // last two are different
         if (uniqColors < currentPlayers) {
            JOptionPane.showMessageDialog(null, "Colors must be unique!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
         }
         // Pass player names to the controller or navigate to the next page
         // Example: navigating to the question editor page

         controller.showGameplayPage(true);
         GameData.setQuestionList(Question.retrieveAllDefaultQuestions());

      });
      JButton backButton = new JButton("Back");
      backButton.addActionListener(e -> controller.showGameSettingsPage());
      buttonPanel.add(backButton);
      buttonPanel.add(nextButton);

      // Create a panel to hold the input components
      JPanel inputPanel = new JPanel();
      inputPanel.setBackground(new Color(248, 237, 212));
      inputPanel.setBorder(new EmptyBorder(screenWidth / 12, screenHeight / 2, screenWidth / 6, screenHeight / 2));

      inputPanel.setLayout(new GridLayout(4, 3));
      // inputPanel.add(gapLabel);
      int playerNum = 1;
      Font labelFont = new Font("Roboto", Font.PLAIN, 24);
      // set colors
      String[] colorChoices = { "Blue", "Red", "Green", "Yellow" };
      JComboBox<String> colorCB = setColorOptions(colorChoices, 0);
      JComboBox<String> color2CB = setColorOptions(colorChoices, 1);
      JComboBox<String> color3CB = setColorOptions(colorChoices, 2);
      JComboBox<String> color4CB = setColorOptions(colorChoices, 3);
      colorCBList = new JComboBox[] { colorCB, color2CB, color3CB, color4CB };
      nameToColorMap.put(colorChoices[0], Color.BLUE);
      nameToColorMap.put(colorChoices[1], Color.RED);
      nameToColorMap.put(colorChoices[2], Color.GREEN);
      nameToColorMap.put(colorChoices[3], Color.YELLOW);

      for (JTextField nameField : nameFields) {
         JLabel playerLabel = new JLabel("Player " + playerNum);

         // add player label
         playerLabel.setFont(labelFont);
         inputPanel.add(playerLabel);

         // add player name input
         inputPanel.add(nameField);

         // add player color input
         inputPanel.add(colorCBList[playerNum - 1]);

         playerNum++;
      }

      // Add components to the frame
      add(inputPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);

      // Set frame properties
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set the size to 75% of the screen's height and width
      setSize(screenWidth, screenHeight);

      setLocationRelativeTo(null); // Center the frame on the screen
   }

   private JComboBox<String> setColorOptions(String[] colorChoices, int i) {
      JComboBox<String> colorCB = new JComboBox<>(colorChoices);
      colorCB.setRenderer(new CustomComboBoxRenderer());
      colorCB.setSelectedIndex(i);
      colorCB.setVisible(true);
      return colorCB;
   }

}
