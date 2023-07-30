package pages;

import game.Question;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class QuestionAnswerPage extends JPanel {
   private Question currentQuestion;
   private JRadioButton multipleChoice1;
   private JRadioButton multipleChoice2;
   private JRadioButton multipleChoice3;
   private JRadioButton multipleChoice4;
   public QuestionAnswerPage(Question question) {
      currentQuestion = question;

      // Title set in JOptionPane call
      // Icon set in JOptionPane call
      // Set the layout manager
      setLayout(new BorderLayout());

      // Create components
      JPanel topPanel = new JPanel(new GridLayout(0, 1));
      JLabel genericQuestionLabel = new JLabel("Question");
      JLabel questionCategoryLabel = new JLabel(currentQuestion.getQuestionCategory());

      genericQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
      questionCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);

      topPanel.add(genericQuestionLabel);
      topPanel.add(questionCategoryLabel);

      // Multiple Choice Button Group
      multipleChoice1 = new JRadioButton(currentQuestion.getMultipleChoiceOne());
      multipleChoice2 = new JRadioButton(currentQuestion.getMultipleChoiceTwo());
      multipleChoice3 = new JRadioButton(currentQuestion.getMultipleChoiceThree());
      multipleChoice4 = new JRadioButton(currentQuestion.getMultipleChoiceFour());

      ButtonGroup multipleChoiceButtonGroup = new ButtonGroup();
      multipleChoiceButtonGroup.add(multipleChoice1);
      multipleChoiceButtonGroup.add(multipleChoice2);
      multipleChoiceButtonGroup.add(multipleChoice3);
      multipleChoiceButtonGroup.add(multipleChoice4);
      multipleChoiceButtonGroup.clearSelection();

      // Question & Multiple Choice Panel
      JPanel questionAnswerPanel = new JPanel(new GridLayout(5, 1));
      JLabel questionText = new JLabel(currentQuestion.getQuestionText());
      questionText.setHorizontalAlignment(SwingConstants.CENTER);
      questionAnswerPanel.add(questionText);
      questionAnswerPanel.add(multipleChoice1);
      questionAnswerPanel.add(multipleChoice2);
      questionAnswerPanel.add(multipleChoice3);
      questionAnswerPanel.add(multipleChoice4);

      // TODO: Set Message Dialog for Correct/Incorrect in Gameplay GUI

      // Add components to the panel
      add(topPanel, BorderLayout.NORTH);
      add(questionAnswerPanel, BorderLayout.CENTER);
   }
   public boolean isCorrectAnswerChoice() throws RuntimeException {
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
}
