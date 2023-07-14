package game;

import bits_please_api.*;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a question in the application.
 */
public class Question {
    // Fields
    private String questionUUID;
    private String questionCategory;
    private String questionText;
    private String multipleChoiceOne;
    private String multipleChoiceTwo;
    private String multipleChoiceThree;
    private String multipleChoiceFour;
    private String questionAnswer;
    private int questionDifficulty;
    private ModifyQuestions modifyQuestions;
    private CreateQuestions createQuestions;

    // ---------- CONSTRUCTORS ----------

    /**
     * Creates a new question and stores it in the database.
     *
     * @param questionCategory       the category of the question
     * @param questionText           the text of the question
     * @param questionAnswer         the answer to the question
     * @param multipleChoiceOne      the first multiple-choice option
     * @param multipleChoiceTwo      the second multiple-choice option
     * @param multipleChoiceThree    the third multiple-choice option
     * @param multipleChoiceFour     the fourth multiple-choice option
     * @param questionDifficulty     the difficulty level of the question
     * @throws APIRequestException   if an error occurs during the API request
     */
    public Question(
            String questionCategory, String questionText, String questionAnswer,
            String multipleChoiceOne, String multipleChoiceTwo, String multipleChoiceThree,
            String multipleChoiceFour, int questionDifficulty
    ) {
        // Creates a new Question, stores in DB
        this.questionCategory = questionCategory;
        this.questionText = questionText;
        this.questionAnswer = questionAnswer;
        this.multipleChoiceOne = multipleChoiceOne;
        this.multipleChoiceTwo = multipleChoiceTwo;
        this.multipleChoiceThree = multipleChoiceThree;
        this.multipleChoiceFour = multipleChoiceFour;
        this.questionDifficulty = questionDifficulty;
        this.modifyQuestions = ModifyQuestions.getInstance();

        // Create New Question with Bits Please REST API
        this.createQuestions = CreateQuestions.getInstance();
        try {
            this.questionUUID = createQuestions.CreateQuestion(questionCategory, questionText, questionAnswer, multipleChoiceOne, multipleChoiceTwo, multipleChoiceThree, multipleChoiceFour, QuestionDifficulty.getValue(questionDifficulty));
        } catch (APIRequestException e) {
            System.err.println("Failed to create Question: "+e);
        }
    }

    /**
     * Retrieves an existing question from the database by its UUID.
     *
     * @param questionUUID           the UUID of the question to retrieve
     * @throws APIRequestException   if an error occurs during the API request
     */
    public Question(String questionUUID) throws APIRequestException {
        // Retrieves an existing Question from DB, by Question UUID
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();
        JSONQuestions retrievedQuestion = retrieveQuestions.RetrieveQuestionFromQuestionUUID(questionUUID);
        try {
            this.questionUUID = questionUUID;
            this.questionCategory = retrievedQuestion.getCategories().get(0);
            this.questionText = retrievedQuestion.getQuestions().get(0);
            this.multipleChoiceOne = retrievedQuestion.getFirstMultipleChoiceAnswers().get(0);
            this.multipleChoiceTwo = retrievedQuestion.getSecondMultipleChoiceAnswers().get(0);
            this.multipleChoiceThree = retrievedQuestion.getThirdMultipleChoiceAnswers().get(0);
            this.multipleChoiceFour = retrievedQuestion.getFourthMultipleChoiceAnswers().get(0);
            this.questionAnswer = retrievedQuestion.getAnswers().get(0);
            this.questionDifficulty = (int) retrievedQuestion.getDifficulties().get(0);
            this.modifyQuestions = ModifyQuestions.getInstance();
            this.createQuestions = CreateQuestions.getInstance();
        } catch (JSONException e){
            throw new APIRequestException("Required field not contained in JSONQuestions object.");
        }

    }

    /**
     * Parses a question from a JSONQuestions object by index.
     *
     * @param jsonQuestions         the JSONQuestions object
     * @param questionIndex         the index of the question to parse
     */
    public Question(JSONQuestions jsonQuestions, int questionIndex){
        // Parses a Question from a JSONQuestions object by index
        try {
            this.questionUUID = jsonQuestions.getQuestionUUIDs().get(questionIndex);
            this.questionCategory = jsonQuestions.getCategories().get(questionIndex);
            this.questionText = jsonQuestions.getQuestions().get(questionIndex);
            this.multipleChoiceOne = jsonQuestions.getFirstMultipleChoiceAnswers().get(questionIndex);
            this.multipleChoiceTwo = jsonQuestions.getSecondMultipleChoiceAnswers().get(questionIndex);
            this.multipleChoiceThree = jsonQuestions.getThirdMultipleChoiceAnswers().get(questionIndex);
            this.multipleChoiceFour = jsonQuestions.getFourthMultipleChoiceAnswers().get(questionIndex);
            this.questionAnswer = jsonQuestions.getAnswers().get(questionIndex);
            this.questionDifficulty = (int) jsonQuestions.getDifficulties().get(questionIndex);
            this.modifyQuestions = ModifyQuestions.getInstance();
            this.createQuestions = CreateQuestions.getInstance();
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException("Index for JSONQuestions object is invalid. Initialize Question failed.");
        } catch (JSONException e){
            throw new APIRequestException("Required field not contained in JSONQuestions object.");
        }
    }


    // ---------- OBJECT METHODS ----------

    /**
     * Deletes the question from the database.
     */
    public void deleteQuestion() {
        try {
            modifyQuestions.DeleteQuestion(questionUUID);
        } catch (APIRequestException e){
            System.err.println("Unable to delete Question: "+e);
        }
    }

    /**
     * Modifies the question with new values.
     *
     * @param modifiedCategory           the modified category of the question
     * @param modifiedText               the modified text of the question
     * @param modifiedAnswer             the modified answer to the question
     * @param modifiedMultipleChoiceOne  the modified first multiple-choice option
     * @param modifiedMultipleChoiceTwo  the modified second multiple-choice option
     * @param modifiedMultipleChoiceThree  the modified third multiple-choice option
     * @param modifiedMultipleChoiceFour  the modified fourth multiple-choice option
     * @param modifiedDifficulty         the modified difficulty level of the question
     */
    public void modifyQuestion(
            String modifiedCategory, String modifiedText, String modifiedAnswer,
            String modifiedMultipleChoiceOne, String modifiedMultipleChoiceTwo,
            String modifiedMultipleChoiceThree, String modifiedMultipleChoiceFour,
            int modifiedDifficulty
    ) {
        try {
            modifyQuestions.ModifyQuestion(
                    questionUUID, modifiedCategory, modifiedText, modifiedMultipleChoiceOne,
                    modifiedMultipleChoiceTwo, modifiedMultipleChoiceThree, modifiedMultipleChoiceFour,
                    modifiedAnswer, QuestionDifficulty.getValue(modifiedDifficulty)
            );

            // Update the object's fields with the modified values
            questionCategory = modifiedCategory;
            questionText = modifiedText;
            multipleChoiceOne = modifiedMultipleChoiceOne;
            multipleChoiceTwo = modifiedMultipleChoiceTwo;
            multipleChoiceThree = modifiedMultipleChoiceThree;
            multipleChoiceFour = modifiedMultipleChoiceFour;
            questionAnswer = modifiedAnswer;
            questionDifficulty = modifiedDifficulty;
        } catch (APIRequestException e) {
            System.err.println(e);
        }
    }

    public String getQuestionUUID() {
        return questionUUID;
    }
    public String getQuestionText() {
        return questionText;
    }
    public String getQuestionCategory() {
        return questionCategory;
    }
    public String getQuestionAnswer() {
        return questionAnswer;
    }
    public String getMultipleChoiceOne() {
        return multipleChoiceOne;
    }
    public String getMultipleChoiceTwo() {
        return multipleChoiceTwo;
    }
    public String getMultipleChoiceThree() {
        return multipleChoiceThree;
    }
    public String getMultipleChoiceFour() {
        return multipleChoiceFour;
    }
    public int getQuestionDifficulty() {
        return questionDifficulty;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question UUID: ").append(questionUUID).append("\n");
        sb.append("Category: ").append(questionCategory).append("\n");
        sb.append("Question Text: ").append(questionText).append("\n");
        sb.append("Multiple Choice Options: \n");
        sb.append("    Option 1: ").append(multipleChoiceOne).append("\n");
        sb.append("    Option 2: ").append(multipleChoiceTwo).append("\n");
        sb.append("    Option 3: ").append(multipleChoiceThree).append("\n");
        sb.append("    Option 4: ").append(multipleChoiceFour).append("\n");
        sb.append("Answer: ").append(questionAnswer).append("\n");
        sb.append("Difficulty: ").append(questionDifficulty).append("\n");
        return sb.toString();
    }


    // ---------- STATIC METHODS ----------

    /**
     * Deletes a question by its UUID.
     *
     * @param questionUuid The UUID of the question to delete.
     */
    public static void deleteQuestionByQuestionUUID(String questionUuid){
        ModifyQuestions modifyQuestionsTemp = ModifyQuestions.getInstance();
        try {
            modifyQuestionsTemp.DeleteQuestion(questionUuid);
        } catch (APIRequestException e){
            System.err.println("APIRequestException - Could Not Delete Question: "+ e);
        }
    }

    /**
     * Retrieves all client questions.
     *
     * @return A list of Question objects representing all the client questions.
     *         Returns {@code null} if there was an API request exception.
     */
    public static List<Question> retrieveAllClientQuestions(){
        List<Question> list = new ArrayList<>();
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();
        JSONQuestions retrievedQuestions;

        // Retrieve Client Questions, create JSONQuestions object
        try {
            retrievedQuestions = retrieveQuestions.RetrieveAllQuestions();
        } catch (APIRequestException e){
            return null;
        }

        // Add Question objects to list
        for (int i = 0; i < retrievedQuestions.getSize(); i++) {
            list.add(new Question(retrievedQuestions, i));
        }
        return list;
    }

    /**
     * Retrieves all default questions.
     *
     * @return A list of Question objects representing all the default questions.
     *         Returns {@code null} if there was an API request exception.
     */
    public static List<Question> retrieveAllDefaultQuestions(){
        List<Question> list = new ArrayList<>();
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();
        JSONQuestions retrievedQuestions;

        // Retrieve Client Questions, create JSONQuestions object
        try {
            retrievedQuestions = retrieveQuestions.RetrieveAllDefaultQuestions();
        } catch (APIRequestException e){
            return null;
        }

        // Add Question objects to list
        for (int i = 0; i < retrievedQuestions.getSize(); i++) {
            list.add(new Question(retrievedQuestions, i));
        }
        return list;
    }

    /**
     * Retrieves all default questions by category.
     *
     * @param category Category of default questions.
     *
     * @return A list of Question objects representing all the default questions for a category.
     *         Returns {@code null} if there was an API request exception.
     */
    public static List<Question> retrieveDefaultQuestionsByCategory(String category){
        List<Question> list = new ArrayList<>();
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();
        JSONQuestions retrievedQuestions;

        // Retrieve Client Questions, create JSONQuestions object
        try {
            retrievedQuestions = retrieveQuestions.RetrieveDefaultQuestionsByCategory(category);
        } catch (APIRequestException e){
            return null;
        }

        // Add Question objects to list
        for (int i = 0; i < retrievedQuestions.getSize(); i++) {
            list.add(new Question(retrievedQuestions, i));
        }
        return list;
    }

    /**
     * Deletes all client questions.
     *
     * This method retrieves all client questions and deletes them one by one.
     * If any question deletion fails, an error message is printed to the console.
     */
    public static void deleteAllClientQuestions(){
        List<Question> clientQuestions = retrieveAllClientQuestions();
        for (int i = 0; i < clientQuestions.size(); i++){
            try {
                clientQuestions.get(i).deleteQuestion();
            } catch (APIRequestException e){
                System.err.println("Unable to delete Question: "+clientQuestions.get(i).questionUUID);
            }
        }
    }
}
