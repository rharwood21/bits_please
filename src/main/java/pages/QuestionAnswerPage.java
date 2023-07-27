package pages;

import game.GameController;
import game.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * The question and answer page of the Trivial Compute game
 * Displays questions from category, answer is checked, opponents vote on
 * answer's correctness
 */
public class QuestionAnswerPage extends JFrame {
   private GameController controller;
   private BufferedImage image;
   private Question currentQuestion = null;
   private JLabel questionCategoryLabel;
   private JLabel questionText;
   private ButtonGroup multipleChoiceButtonGroup;
   private JRadioButton multipleChoice1 = new JRadioButton();
   private JRadioButton multipleChoice2 = new JRadioButton();
   private JRadioButton multipleChoice3 = new JRadioButton();
   private JRadioButton multipleChoice4 = new JRadioButton();
   private ImageIcon correctIcon = new ImageIcon(getClass().getResource("/images/correct.png"));
   private ImageIcon incorrectIcon = new ImageIcon(getClass().getResource("/images/incorrect.png"));

   /**
    * Constructs a pages.QuestionAnswerPage object.
    *
    * @param controller The game controller instance for managing the navigation.
    */
   public QuestionAnswerPage(GameController controller) {
      super("Question And Answer Page");

      this.controller = controller;

      this.setTitle("Question And Answer Page"); // sets title of frame
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setResizable(false); // prevent frame from being resized

      // Load the image
      try {
         image = ImageIO.read(getClass().getResource("/images/BitsPleaseLogo.jpg")); // Replace with your image path
      } catch (IOException e) {
         e.printStackTrace();
      }
      ImageIcon pageIcon = new ImageIcon(image);
      this.setIconImage(pageIcon.getImage()); // change icon of frame

      // Set the layout manager
      setLayout(new BorderLayout());

      // Create components
      JPanel topPanel = new JPanel(new GridLayout(0, 1));
      JLabel genericQuestionLabel = new JLabel("Question");
      questionCategoryLabel = new JLabel();
      questionText = new JLabel();

      genericQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
      questionCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
      questionText.setHorizontalAlignment(SwingConstants.CENTER);

      topPanel.add(genericQuestionLabel);
      topPanel.add(questionCategoryLabel);

      // Multiple Choice Button Group
      multipleChoiceButtonGroup = new ButtonGroup();
      multipleChoiceButtonGroup.add(multipleChoice1);
      multipleChoiceButtonGroup.add(multipleChoice2);
      multipleChoiceButtonGroup.add(multipleChoice3);
      multipleChoiceButtonGroup.add(multipleChoice4);
      multipleChoiceButtonGroup.clearSelection();

      // Question & Multiple Choice Panel
      JPanel questionAnswerPanel = new JPanel(new GridLayout(5, 1));
      questionAnswerPanel.add(questionText);
      questionAnswerPanel.add(multipleChoice1);
      questionAnswerPanel.add(multipleChoice2);
      questionAnswerPanel.add(multipleChoice3);
      questionAnswerPanel.add(multipleChoice4);

      JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
      buttonPanel1.setBorder(BorderFactory.createTitledBorder("Are you sure about your choice?"));
      JButton checkAnswerButton = new JButton("Check Answer");
      checkAnswerButton.addActionListener(e -> {  // checkAnswerButton: Show Correct/Incorrect Answer, then Return to Gameplay
         boolean correctAnswer;
         try {
            correctAnswer = isCorrectAnswerChoice();
         } catch (RuntimeException ex){
            JOptionPane.showMessageDialog(this, "Please Select an Answer", "Select an Answer", JOptionPane.ERROR_MESSAGE);
            return;
         }
         if (correctAnswer){
            // CORRECT
            JOptionPane.showMessageDialog(this, "CORRECT!!! :D\nAnswer: "+currentQuestion.getQuestionAnswer(), "Correct!", JOptionPane.INFORMATION_MESSAGE, correctIcon);
         } else {
            // INCORRECT
            JOptionPane.showMessageDialog(this, "INCORRECT!!! :(\nAnswer: "+currentQuestion.getQuestionAnswer(), "Incorrect!", JOptionPane.INFORMATION_MESSAGE, incorrectIcon);
         }
         controller.showGameplayPage();
      });
      buttonPanel1.add(checkAnswerButton);

      // Add components to the frame
      add(topPanel, BorderLayout.NORTH);
      add(questionAnswerPanel, BorderLayout.CENTER);
      add(buttonPanel1, BorderLayout.SOUTH);

      // Set the size to 50% of the screen's height and width
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth = (int) (screenSize.getWidth() * 0.5);
      int screenHeight = (int) (screenSize.getHeight() * 0.5);
      setSize(screenWidth, screenHeight);
      setLocationRelativeTo(null); // Center the frame on the screen
   }
   private boolean isCorrectAnswerChoice() throws RuntimeException {
      if (!multipleChoice1.isSelected() && !multipleChoice2.isSelected() && !multipleChoice3.isSelected() && !multipleChoice4.isSelected()){
         throw new RuntimeException("No Choice Selected");
      }
      String answer = currentQuestion.getQuestionAnswer();
      if (multipleChoice1.isSelected() && Objects.equals(multipleChoice1.getText(), answer)){
         return true;
      }
      else if (multipleChoice2.isSelected() && Objects.equals(multipleChoice2.getText(), answer)){
         return true;
      }
      else if (multipleChoice3.isSelected() && Objects.equals(multipleChoice3.getText(), answer)){
         return true;
      }
      else if (multipleChoice4.isSelected() && Objects.equals(multipleChoice4.getText(), answer)){
         return true;
      }
      // Correct Answer Not Selected
      return false;
   }

   public void setCurrentQuestion(Question currentQuestion) {
      this.currentQuestion = currentQuestion;
      refreshQuestion();
   }

   private void refreshQuestion(){
      // Set the question category
      if (currentQuestion != null) {
         questionCategoryLabel.setText("Category: " + currentQuestion.getQuestionCategory());
         // Set the question text
         questionText.setText(currentQuestion.getQuestionText());
         multipleChoice1.setText(currentQuestion.getMultipleChoiceOne());
         multipleChoice2.setText(currentQuestion.getMultipleChoiceTwo());
         multipleChoice3.setText(currentQuestion.getMultipleChoiceThree());
         multipleChoice4.setText(currentQuestion.getMultipleChoiceFour());
         multipleChoiceButtonGroup.clearSelection();
      }
      // If you want the GUI to refresh immediately, you can revalidate and repaint
      revalidate();
      repaint();
   }
}
