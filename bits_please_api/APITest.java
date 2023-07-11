package bits_please_api;

import org.json.JSONArray;
import org.json.JSONObject;
public class APITest {
    public static void main(String[] args) {
        // Test InitializeClient
        System.out.println("----- TEST INITIALIZE CLIENT -----");
        InitializeClient initialClient = InitializeClient.getInstance();
        System.out.println("UUID: " + initialClient.getClientUUID());
        System.out.println("Is valid? " + String.valueOf(initialClient.VerifyClient()));


        // Test CreateQuestions
        System.out.println("----- TEST CREATE QUESTIONS -----");
        CreateQuestions createQuestions = CreateQuestions.getInstance();
        String questionUuid = createQuestions.CreateQuestion(
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

        String q2 = createQuestions.CreateQuestion(
                "Entertainment",
                "Which city hosted the Summer Olympics in 2012",
                "London",
                "Paris",
                "Vermont",
                "South Africa",
                "London",
                QuestionDifficulty.HARD
        );
        String q3 = createQuestions.CreateQuestion(
                "History",
                "In Greek Mythology, who is the Queen of the Underworld and wife of Hades?",
                "Persephone",
                "Hercules",
                "Persephone",
                "Aphrodite",
                "Zeus",
                QuestionDifficulty.MEDIUM
        );
        String q4 = createQuestions.CreateQuestion(
                "Entertainment",
                "Which house was Harry Potter almost sorted into?",
                "Slytherin",
                "Hufflepuff",
                "Slytherin",
                "Gryffindore",
                "Ravenclaw",
                QuestionDifficulty.VERY_HARD
        );
        System.out.println("Additional Question UUIDs Created:\n1: "+q2+"\n2: "+q3+"\n3: "+q4+"\n");


        // Test RetrieveQuestions
        System.out.println("----- TEST RETRIEVE QUESTIONS -----");
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();

        JSONObject questionJson = retrieveQuestions.RetrieveRandomQuestionFromCategory("History", QuestionDifficulty.MEDIUM);
        System.out.println("Random History Question: \n"+questionJson.toString());

        JSONObject questionFromQuestionUUID = retrieveQuestions.RetrieveQuestionFromQuestionUUID(q3);
        System.out.println("\nFrom UUID: \n"+questionFromQuestionUUID.toString());

        JSONObject randomQAllCat = retrieveQuestions.RetrieveRandomQuestionFromAllCategories(QuestionDifficulty.MEDIUM);
        System.out.println("\nRandom Question All Categories: \n"+randomQAllCat.toString());

        JSONObject allQuestionsFromCategory = retrieveQuestions.RetrieveAllQuestionsFromCategory("History");
        System.out.println("\nAll Questions from History: \n"+allQuestionsFromCategory.toString());

        JSONObject allQuestions = retrieveQuestions.RetrieveAllQuestions();
        System.out.println("\nAll your Questions: \n"+allQuestions.toString());


        // Test ModifyQuestions
        System.out.println("\n----- TEST MODIFY QUESTIONS -----");
        JSONObject q4Question = retrieveQuestions.RetrieveQuestionFromQuestionUUID(q4);
        System.out.println("Question 4 Pre-Modify: \n"+q4Question.toString());

        ModifyQuestions modifyQuestions = ModifyQuestions.getInstance();
        System.out.println("\t--- CHANGES TO BE MADE ---");
        System.out.println("\tQuestion: Who lives in a Pineapple under the Sea?");
        System.out.println("\tChoice1/Answer: Spongebob Squarepants");
        System.out.println("\tCategory: Entertainment");
        System.out.println("\tAll Others: Will be the same");
        JSONArray choice2array = (JSONArray) q4Question.get("choice2s");
        JSONArray choice3array = (JSONArray) q4Question.get("choice3s");
        JSONArray choice4array = (JSONArray) q4Question.get("choice4s");
        modifyQuestions.ModifyQuestion(
                q4,
                "Entertainment",
                "Who lives in a Pineapple under the Sea?",
                "Spongebob Squarepants",
                (String) choice2array.get(0),
                (String) choice3array.get(0),
                (String) choice4array.get(0),
                "Spongebob Squarepants",
                QuestionDifficulty.VERY_HARD
        );
        JSONObject q4QuestionUpdated = retrieveQuestions.RetrieveQuestionFromQuestionUUID(q4);
        System.out.println("Question 4 Post-Modify (Retrieved from DB): \n"+q4QuestionUpdated.toString());

        System.out.println("--- DELETE Q4 ---");
        modifyQuestions.DeleteQuestion(q4);
        JSONObject q4PostDelete = retrieveQuestions.RetrieveQuestionFromQuestionUUID(q4);
        System.out.println("Question 4 Post-Deletion: \n"+q4PostDelete.toString());

        // Clean Up
        System.out.println("Cleaning up created questions ...");
        modifyQuestions.DeleteQuestion(questionUuid);
        modifyQuestions.DeleteQuestion(q2);
        modifyQuestions.DeleteQuestion(q3);
    }
}
