package pages;

import game.GameController;
import game.GameData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the player name input page of the Trivial Compute Game. Allows the
 * user to enter player names and navigate to the next page.
 */
public class GameSettingsPage extends JFrame {
   private GameController controller;
   private BufferedImage image;

   /**
    * Constructs a pages.GameSettingsPage object.
    *
    * @param controller The game controller instance for managing the navigation.
    */
   public GameSettingsPage(GameController controller) {
      super("Choose Settings");

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

      JLabel nameLabel = new JLabel("Select categories and colors:");
      nameLabel.setFont(new Font("Roboto", Font.BOLD, 30));
      nameLabel.setVisible(true);
      JPanel headerPanel = new JPanel();
      headerPanel.setBackground(new Color(248, 237, 212));
      headerPanel.setBorder(new EmptyBorder(screenWidth / 10, 0, 0, 0));
      headerPanel.add(nameLabel);

      JPanel cbPanel = new JPanel();
      cbPanel.setBackground(new Color(248, 237, 212));
      cbPanel.setLayout(new GridLayout(6, 2));
      cbPanel.setBorder(new EmptyBorder(0, screenHeight / 3, screenWidth / 8, screenHeight / 3));

      // TODO: De-duplicate, and dynamically retrieve categories from questionList/defaultQuestionList in GameData
      String[] subjectChoices = { "History", "Entertainment", "Science", "Geography", "Computer Science" }; // Default Categories in DB
      String[] colorChoices = { "Blue", "Red", "Green", "Yellow" };
      Map<String, Color> stringColorMap = new HashMap<>();
      stringColorMap.put(colorChoices[0], Color.BLUE);
      stringColorMap.put(colorChoices[1], Color.RED);
      stringColorMap.put(colorChoices[2], Color.GREEN);
      stringColorMap.put(colorChoices[3], Color.YELLOW);

      // Set the first subject
      final JComboBox<String> subject1CB = new JComboBox<String>(subjectChoices);
      subject1CB.setRenderer(new CustomComboBoxRenderer());
      final JComboBox<String> color1CB = new JComboBox<String>(colorChoices);
      color1CB.setRenderer(new CustomComboBoxRenderer());

      subject1CB.setVisible(true);
      cbPanel.add(subject1CB);
      color1CB.setVisible(true);
      cbPanel.add(color1CB);

      // Set the second subject
      final JComboBox<String> subject2CB = new JComboBox<String>(subjectChoices);
      subject2CB.setRenderer(new CustomComboBoxRenderer());
      final JComboBox<String> color2CB = new JComboBox<String>(colorChoices);
      color2CB.setRenderer(new CustomComboBoxRenderer());

      subject2CB.setSelectedIndex(1);
      subject2CB.setVisible(true);
      cbPanel.add(subject2CB);
      color2CB.setSelectedIndex(1);
      color2CB.setVisible(true);
      cbPanel.add(color2CB);

      // Set the third subject
      final JComboBox<String> subject3CB = new JComboBox<String>(subjectChoices);
      subject3CB.setRenderer(new CustomComboBoxRenderer());
      final JComboBox<String> color3CB = new JComboBox<String>(colorChoices);
      color3CB.setRenderer(new CustomComboBoxRenderer());

      subject3CB.setSelectedIndex(2);
      subject3CB.setVisible(true);
      cbPanel.add(subject3CB);
      color3CB.setSelectedIndex(2);
      color3CB.setVisible(true);
      cbPanel.add(color3CB);

      // Set the fourth subject
      final JComboBox<String> subject4CB = new JComboBox<String>(subjectChoices);
      subject4CB.setRenderer(new CustomComboBoxRenderer());
      final JComboBox<String> color4CB = new JComboBox<String>(colorChoices);
      color4CB.setRenderer(new CustomComboBoxRenderer());

      subject4CB.setSelectedIndex(3);
      subject4CB.setVisible(true);
      cbPanel.add(subject4CB);
      color4CB.setSelectedIndex(3);
      color4CB.setVisible(true);
      cbPanel.add(color4CB);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(new Color(248, 237, 212));

      JButton nextButton = new JButton("Next");
      nextButton.addActionListener(e -> {

         // save the categories and colors
         GameData.flushCategories();
         GameData.setCategoryAndColor(0, (String) subject1CB.getSelectedItem(), stringColorMap.get(color1CB.getSelectedItem()));
         GameData.setCategoryAndColor(1, (String) subject2CB.getSelectedItem(), stringColorMap.get(color2CB.getSelectedItem()));
         GameData.setCategoryAndColor(2, (String) subject3CB.getSelectedItem(), stringColorMap.get(color3CB.getSelectedItem()));
         GameData.setCategoryAndColor(3, (String) subject4CB.getSelectedItem(), stringColorMap.get(color4CB.getSelectedItem()));

         int uniqCategories = GameData.getUniqueCategoryCount();
         int uniqColors = GameData.getUniqueColorCount();
         if (uniqCategories != 4 || uniqColors != 4) {
            JOptionPane.showMessageDialog(null,
                  "Invalid Number of Unique Categories or Colors!\nPlease input 4 unique categories and colors.",
                  "Error", JOptionPane.ERROR_MESSAGE);
            return;
         }
         // navigate to the next page
         controller.showPlayerNameInputPage();
      });
      JButton backButton = new JButton("Back");
      backButton.addActionListener(e -> controller.showWelcomePage());
      buttonPanel.add(backButton);
      buttonPanel.add(nextButton);

      // Add components to the frame
      add(headerPanel, BorderLayout.NORTH);
      add(cbPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.SOUTH);

      // Set frame properties
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set the size to 75% of the screen's height and width
      setSize(screenWidth, screenHeight);

      setLocationRelativeTo(null); // Center the frame on the screen

   }
}
