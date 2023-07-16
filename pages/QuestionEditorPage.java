package pages;

import bits_please_api.APIRequestException;
import game.GameController;
import game.GameData;
import game.Question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * Represents the question editor page of the Trivial Compute Game.
 * Allows the user to enter a question and its answer, and navigate to the next page.
 */
public class QuestionEditorPage extends JFrame {
    private GameController controller;
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JLabel lastUpdatedLabel;
    private List<Question> tempQuestionList;
    private final int COLUMN_QUESTION_TEXT = 0;
    private final int COLUMN_QUESTION_CATEGORY = 1;
    private final int COLUMN_QUESTION_ANSWER = 2;
    private final int COLUMN_MULTIPLE_CHOICE_ONE = 3;
    private final int COLUMN_MULTIPLE_CHOICE_TWO = 4;
    private final int COLUMN_MULTIPLE_CHOICE_THREE = 5;
    private final int COLUMN_MULTIPLE_CHOICE_FOUR = 6;
    private final int COLUMN_QUESTION_DIFFICULTY = 7;
    private final int COLUMN_QUESTION_IS_ALTERED = 8; // Used Internally, Not in Table

    /**
     * Constructs a pages.QuestionEditorPage object.
     *
     * @param controller The game controller instance for managing the navigation.
     */
    public QuestionEditorPage(GameController controller) {
        super("Customize Questions");

        this.controller = controller;

        // Set the layout manager
        setLayout(new BorderLayout());

        // Create the last updated label and set its properties
        lastUpdatedLabel = new JLabel("Last Updated: N/A");
        lastUpdatedLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lastUpdatedLabel.setFont(lastUpdatedLabel.getFont().deriveFont(Font.BOLD));

        // Create a panel to hold the last updated label
        JPanel lastUpdatedPanel = new JPanel(new BorderLayout());
        lastUpdatedPanel.add(lastUpdatedLabel, BorderLayout.EAST);

        // Question Button Panel
        JPanel questionButtonPanel = new JPanel();

        JButton newQuestionButton = new JButton("New Question");
        newQuestionButton.addActionListener(e -> tableModel.addRow(new Object[tableModel.getColumnCount()]));

        JButton saveQuestionButton = new JButton("Save Questions");
        saveQuestionButton.addActionListener(e -> saveQuestions());

        JButton refreshQuestionsButton = new JButton("Refresh Questions");
        refreshQuestionsButton.addActionListener(e -> refreshQuestions());

        JButton deleteQuestionButton = new JButton("Delete Question");
        deleteQuestionButton.addActionListener(e -> deleteSelectedQuestion());

        questionButtonPanel.add(newQuestionButton);
        questionButtonPanel.add(saveQuestionButton);
        questionButtonPanel.add(refreshQuestionsButton);
        questionButtonPanel.add(deleteQuestionButton);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());

        // Create table model with column names
        String[] columnNames = {"Question", "Category", "Answer", "Multiple Choice 1", "Multiple Choice 2", "Multiple Choice 3", "Multiple Choice 4", "Difficulty"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create table and set table model
        questionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(questionTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // GUI Control Button Panel
        JPanel buttonPanel = new JPanel();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> controller.showGameplayPage());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> controller.showPlayerNameInputPage());
        JButton instructionsButton = new JButton("Instructions");
        instructionsButton.addActionListener(e -> controller.showInstructionsPage("EDITOR"));
        buttonPanel.add(instructionsButton);
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        // Add components to the frame
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(lastUpdatedPanel, BorderLayout.NORTH);
        northPanel.add(questionButtonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size to 75% of the screen's height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.getWidth() * 0.75);
        int screenHeight = (int) (screenSize.getHeight() * 0.75);
        setSize(screenWidth, screenHeight);

        setLocationRelativeTo(null); // Center the frame on the screen

        // Display the retrieved questions in the table
        try {
            tempQuestionList = Question.retrieveAllClientQuestions();
        } catch (APIRequestException e){
            tempQuestionList = null;
        }
        displayQuestions(tempQuestionList);
        updateLastUpdatedLabel();
    }


    // BUTTON FUNCTIONS
    /**
     * Saves the questions entered by the user.
     */
    private void saveQuestions(){
        int rowCount = tableModel.getRowCount();
        int currentQuestionsSize = tempQuestionList.size();
        Object[][] questionsTableContent = parseAndValidate();
        if (questionsTableContent == null){
            return;
        }
        if (rowCount != questionsTableContent.length){
            throw new RuntimeException("Table size is not the same size as parsed content.");
        }

        // Iterate Questions from table, get values, modify Question with REST API
        for (int row = 0; row < rowCount; row++) {
            if (row <= currentQuestionsSize - 1 && (boolean) questionsTableContent[row][COLUMN_QUESTION_IS_ALTERED]) {
                // Edit Existing Question
                Question tempQuestion = tempQuestionList.get(row);
                tempQuestion.modifyQuestion(
                        questionsTableContent[row][COLUMN_QUESTION_CATEGORY].toString(),
                        questionsTableContent[row][COLUMN_QUESTION_TEXT].toString(),
                        questionsTableContent[row][COLUMN_QUESTION_ANSWER].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_ONE].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_TWO].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_THREE].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_FOUR].toString(),
                        (int) questionsTableContent[row][COLUMN_QUESTION_DIFFICULTY]
                );
            } else if (row > currentQuestionsSize - 1) {
                // Create new Question in DB
                new Question(
                        questionsTableContent[row][COLUMN_QUESTION_CATEGORY].toString(),
                        questionsTableContent[row][COLUMN_QUESTION_TEXT].toString(),
                        questionsTableContent[row][COLUMN_QUESTION_ANSWER].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_ONE].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_TWO].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_THREE].toString(),
                        questionsTableContent[row][COLUMN_MULTIPLE_CHOICE_FOUR].toString(),
                        (int) questionsTableContent[row][COLUMN_QUESTION_DIFFICULTY]
                );
            }
        }

        // Retrieve new Client Questions from DB
        refreshQuestions();
    }
    /**
     * Refreshes the displayed questions by retrieving the latest questions from the database.
     */
    private void refreshQuestions(){
        // Retrieve new Client Questions from DB
        try {
            tempQuestionList = Question.retrieveAllClientQuestions();
        } catch (APIRequestException e){
            JOptionPane.showMessageDialog(this, "API Request Failed: Please Ensure DB is Active.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        GameData.setQuestionList(tempQuestionList);
        displayQuestions(tempQuestionList);
        updateLastUpdatedLabel();
    }
    /**
     * Deletes the selected question from the table.
     */
    private void deleteSelectedQuestion() {
        int selectedRow = questionTable.getSelectedRow();
        int currentQuestionsSize = tempQuestionList.size();
        if (selectedRow != -1) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected question?", "Delete Question", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                if (selectedRow <= currentQuestionsSize - 1 ){
                    // Existing Question
                    Question deleteQuestion = tempQuestionList.get(selectedRow);
                    try {
                        deleteQuestion.deleteQuestion();
                    } catch (APIRequestException e){
                        JOptionPane.showMessageDialog(this, "API Request Failed: Please Ensure DB is Active.\n" +
                                "Delete Question Failed.", "Delete Question", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                // Delete the question from the table model
                tableModel.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a question to delete.", "Delete Question", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // HELPER FUNCTIONS
    /**
     * Displays the retrieved questions in the table.
     *
     * @param questions The list of questions to display.
     */
    private void displayQuestions(List<Question> questions) {
        tableModel.setRowCount(0);
        if (questions == null){
            return;
        }
        for (Question question : questions) {
            Object[] rowData = {
                    question.getQuestionText(),
                    question.getQuestionCategory(),
                    question.getQuestionAnswer(),
                    question.getMultipleChoiceOne(),
                    question.getMultipleChoiceTwo(),
                    question.getMultipleChoiceThree(),
                    question.getMultipleChoiceFour(),
                    question.getQuestionDifficulty()
            };
            tableModel.addRow(rowData);
        }

        // Resize columns to fit content
        TableColumnModel columnModel = questionTable.getColumnModel();
        for (int columnIndex = 0; columnIndex < columnModel.getColumnCount(); columnIndex++) {
            TableColumn column = columnModel.getColumn(columnIndex);
            int preferredWidth = calculatePreferredColumnWidth(column);
            column.setPreferredWidth(preferredWidth);
        }
    }
    /**
     * Updates the last updated label with the current time.
     */
    private void updateLastUpdatedLabel() {
        LocalTime currentTime = LocalTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));

        lastUpdatedLabel.setText("Last Updated: " + formattedTime);
    }
    /**
     * Parses and validates the values entered in the question table.
     *
     * @return A 2D array of parsed and validated values.
     */
    private Object[][] parseAndValidate() {
        int rowCount = tableModel.getRowCount();
        Object[][] parsedValues = new Object[rowCount][9];
        int currentQuestionsSize = tempQuestionList.size();
        String question, category, answer, choice1, choice2, choice3, choice4, difficultyStr;

        for (int row = 0; row < rowCount; row++) {
            try {
                question = tableModel.getValueAt(row, COLUMN_QUESTION_TEXT).toString();
                category = tableModel.getValueAt(row, COLUMN_QUESTION_CATEGORY).toString();
                answer = tableModel.getValueAt(row, COLUMN_QUESTION_ANSWER).toString();
                choice1 = tableModel.getValueAt(row, COLUMN_MULTIPLE_CHOICE_ONE).toString();
                choice2 = tableModel.getValueAt(row, COLUMN_MULTIPLE_CHOICE_TWO).toString();
                choice3 = tableModel.getValueAt(row, COLUMN_MULTIPLE_CHOICE_THREE).toString();
                choice4 = tableModel.getValueAt(row, COLUMN_MULTIPLE_CHOICE_FOUR).toString();
                difficultyStr = tableModel.getValueAt(row, COLUMN_QUESTION_DIFFICULTY).toString();
            } catch (NullPointerException e){
                JOptionPane.showMessageDialog(this, "INVALID INPUT: All question fields must be populated.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            boolean isAltered = true;

            // Validate Difficulty
            int difficulty;
            try {
                difficulty = Integer.parseInt(difficultyStr);
                if (difficulty < 1 || difficulty > 5) {
                    JOptionPane.showMessageDialog(this, "INVALID INPUT: Difficulty must be in the range 1 - 5.\n" +
                            "(1 = Easiest, 5 = Hardest)", "Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "INVALID INPUT: Please enter a valid integer in the range 1 - 5.\n" +
                        "(1 = Easiest, 5 = Hardest)", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Validate Answer & Multiple Choices
            if (!answer.equals(choice1) && !answer.equals(choice2) && !answer.equals(choice3) && !answer.equals(choice4)) {
                JOptionPane.showMessageDialog(this, "INVALID INPUT: Answer must match one of the multiple choices.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // TODO: Validate Short Strings Provided

            // Validate Any Values Changes
            if (row <= currentQuestionsSize - 1) {
                Question questionObject = tempQuestionList.get(row);
                if (
                        Objects.equals(question, questionObject.getQuestionText()) && Objects.equals(category, questionObject.getQuestionCategory()) &&
                        Objects.equals(answer, questionObject.getQuestionAnswer()) && Objects.equals(choice1, questionObject.getMultipleChoiceOne()) &&
                        Objects.equals(choice2, questionObject.getMultipleChoiceTwo()) && Objects.equals(choice3, questionObject.getMultipleChoiceThree()) &&
                        Objects.equals(choice4, questionObject.getMultipleChoiceFour()) && Objects.equals(difficulty, questionObject.getQuestionDifficulty())
                ) {
                    isAltered = false;
                }
            }

            // Store the parsed values in the array
            parsedValues[row] = new Object[] { question, category, answer, choice1, choice2, choice3, choice4, difficulty, isAltered };
        }

        return parsedValues; // Return the parsed values as a 2D array
    }
    /**
     * Calculates the preferred column width for a given table column.
     *
     * @param column The TableColumn object representing the column.
     * @return The preferred width for the column.
     */
    private int calculatePreferredColumnWidth(TableColumn column) {
        int maxWidth = 0;

        // Get the header renderer component
        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if (headerRenderer == null) {
            headerRenderer = questionTable.getTableHeader().getDefaultRenderer();
        }

        Component headerComponent = headerRenderer.getTableCellRendererComponent(
                questionTable, column.getHeaderValue(), false, false, 0, 0);

        // Calculate the maximum width based on the header text
        maxWidth = Math.max(maxWidth, headerComponent.getPreferredSize().width);

        // Calculate the maximum width based on the column cells
        for (int rowIndex = 0; rowIndex < questionTable.getRowCount(); rowIndex++) {
            TableCellRenderer cellRenderer = questionTable.getCellRenderer(rowIndex, column.getModelIndex());
            Component cellComponent = cellRenderer.getTableCellRendererComponent(
                    questionTable, questionTable.getValueAt(rowIndex, column.getModelIndex()), false, false, rowIndex, column.getModelIndex());

            maxWidth = Math.max(maxWidth, cellComponent.getPreferredSize().width);
        }

        // Add padding to the maximum width
        maxWidth += 10; // Adjust the padding value as needed

        return maxWidth;
    }
}

