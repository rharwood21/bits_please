package bits_please_api;

import game.Question;

import java.util.List;

public class APITest {
    public static void main(String[] args) {
        // Test InitializeClient
        System.out.println("----- TEST INITIALIZE CLIENT -----");
        InitializeClient initialClient = InitializeClient.getInstance();
        System.out.println("UUID: " + initialClient.getClientUUID());
        boolean isClientValid = initialClient.VerifyClient();
        System.out.println("Is valid? " + isClientValid);
        if (!isClientValid){
            System.err.println("NOTE: MySQL DB is likely disabled, or Client UUID is invalid.");
            System.err.println("Remaining tests will not finish properly.");
        }


        // Test CreateQuestions
        String questionUuid = null;
        String q2 = null;
        String q3 = null;
        String q4 = null;
        System.out.println("----- TEST CREATE QUESTIONS -----");
        CreateQuestions createQuestions = CreateQuestions.getInstance();
        try {
            questionUuid = createQuestions.CreateQuestion(
                    "History",
                    "Who was the first president of US?",
                    "George Washington",
                    "Bob Ross",
                    "Jimi Hendrix",
                    "Queen Latifa",
                    "George Washington",
                    QuestionDifficulty.MEDIUM
            );
            System.out.println("First Question UUID: " + questionUuid);

            q2 = createQuestions.CreateQuestion(
                    "Entertainment",
                    "Which city hosted the Summer Olympics in 2012",
                    "London",
                    "Paris",
                    "Vermont",
                    "South Africa",
                    "London",
                    QuestionDifficulty.HARD
            );
            q3 = createQuestions.CreateQuestion(
                    "History",
                    "In Greek Mythology, who is the Queen of the Underworld and wife of Hades?",
                    "Persephone",
                    "Hercules",
                    "Persephone",
                    "Aphrodite",
                    "Zeus",
                    QuestionDifficulty.MEDIUM
            );
            q4 = createQuestions.CreateQuestion(
                    "Entertainment",
                    "Which house was Harry Potter almost sorted into?",
                    "Slytherin",
                    "Hufflepuff",
                    "Slytherin",
                    "Gryffindore",
                    "Ravenclaw",
                    QuestionDifficulty.VERY_HARD
            );
            System.out.println("Additional Question UUIDs Created:\n1: " + q2 + "\n2: " + q3 + "\n3: " + q4 + "\n");
        } catch (APIRequestException e){
            System.err.println("Check if MySQL DB is started: "+e);
        }

        // Test RetrieveQuestions
        System.out.println("----- TEST RETRIEVE QUESTIONS -----");
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();

        try {
            JSONQuestions questionJson = retrieveQuestions.RetrieveRandomQuestionFromCategory("History", QuestionDifficulty.MEDIUM);
            System.out.println("Random History Question: \n" + questionJson.toString());

            JSONQuestions questionFromQuestionUUID = retrieveQuestions.RetrieveQuestionFromQuestionUUID(q3);
            System.out.println("\nFrom UUID: \n" + questionFromQuestionUUID.toString());

            JSONQuestions randomQAllCat = retrieveQuestions.RetrieveRandomQuestionFromAllCategories(QuestionDifficulty.MEDIUM);
            System.out.println("\nRandom Question All Categories: \n" + randomQAllCat.toString());

            JSONQuestions allQuestionsFromCategory = retrieveQuestions.RetrieveAllQuestionsFromCategory("History");
            System.out.println("\nAll Questions from History: \n" + allQuestionsFromCategory.toString());

            JSONQuestions allQuestions = retrieveQuestions.RetrieveAllQuestions();
            System.out.println("\nAll your Questions: \n" + allQuestions.toString());
        } catch (APIRequestException e){
            System.err.println("Check if MySQL DB is started: "+e);
        }


        // Test ModifyQuestions
        System.out.println("\n----- TEST MODIFY QUESTIONS -----");
        ModifyQuestions modifyQuestions = ModifyQuestions.getInstance();
        try {
            Question q4Question = new Question(q4);
            System.out.println("Question 4 Pre-Modify: \n\"" + q4Question + "\"");

            System.out.println("\t--- CHANGES TO BE MADE ---");
            System.out.println("\tQuestion: Who lives in a Pineapple under the Sea?");
            System.out.println("\tChoice1/Answer: Spongebob Squarepants");
            System.out.println("\tCategory: Entertainment");
            System.out.println("\tAll Others: Will be the same\n");
            modifyQuestions.ModifyQuestion(
                    q4,
                    "Entertainment",
                    "Who lives in a Pineapple under the Sea?",
                    "Spongebob Squarepants",
                    q4Question.getMultipleChoiceTwo(),
                    q4Question.getMultipleChoiceThree(),
                    q4Question.getMultipleChoiceFour(),
                    "Spongebob Squarepants",
                    QuestionDifficulty.VERY_HARD
            );
            System.out.println("Question 4 Post-Modify (Retrieved from DB): \n\"" + q4Question + "\"");

            System.out.println("--- DELETE Q4 ---");
            modifyQuestions.DeleteQuestion(q4);
        } catch (APIRequestException e){
            System.err.println("Check if MySQL DB is started: "+e);
        }
        try {
            retrieveQuestions.RetrieveQuestionFromQuestionUUID(q4);
        } catch (APIRequestException e){
            System.err.println("POST-DELETE ERROR: " + e);
        }

        // Test Question Class
        try {
            List<Question> clientQuestions = Question.retrieveAllClientQuestions();
            Question question1 = new Question(questionUuid);

            Question question2 = new Question(
                    "History",
                    "Who was the first democratically elected President of Russia?",
                    "Boris Yeltsin",
                    "Spongebob Squarepants",
                    "Nikita Krushchev",
                    "Mikhail Gorbachev",
                    "Boris Yeltsin",
                    3
            );
            question2.modifyQuestion(
                    "History",
                    "Who was the first democratically elected President of Russia?",
                    "Boris Yeltsin",
                    "Vladimir Putin",
                    "Nikita Krushchev",
                    "Mikhail Gorbachev",
                    "Boris Yeltsin",
                    4
            );

            List<Question> defaultCSQuestions = Question.retrieveDefaultQuestionsByCategory("Computer Science");
            System.out.println("Size of Default \"Computer Science\" Questions: " + defaultCSQuestions.size());
            List<Question> allDefaultQuestions = Question.retrieveAllDefaultQuestions();
            System.out.println("Size of All Default Questions: " + allDefaultQuestions.size());
        } catch (APIRequestException e){
            System.err.println("Check if MySQL DB is started: "+e);
        }

        // Clean Up
        System.out.println("Cleaning up created questions ...");
        try {
            modifyQuestions.DeleteQuestion(questionUuid); // ModifyQuestions delete
            Question.deleteQuestionByQuestionUUID(q2); // Question Delete
            Question.deleteQuestionByQuestionUUID(q3);
            Question.deleteAllClientQuestions(); // Delete All Client Questions
        } catch (APIRequestException e){
            System.err.println("Check if MySQL DB is started: "+e);
        }
    }
}
