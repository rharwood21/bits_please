package bits_please_api;

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
                "George Washington"
        );
        System.out.println("Question UUID: " + questionUuid);

        String q2 = createQuestions.CreateQuestion("Entertainment", "Which city hosted the Summer Olympics in 2012", "London");
        String q3 = createQuestions.CreateQuestion("History", "In Greek Mythology, who is the Queen of the Underworld and wife of Hades?", "Persephone");
        String q4 = createQuestions.CreateQuestion("Entertainment", "Which house was Harry Potter almost sorted into?", "Slytherin");
        String q5 = createQuestions.CreateQuestion("History", "Which country gifted the Statue of Liberty to the US?", "France");
        System.out.println("Question UUIDs Created:\n"+q2+"\n"+q3+"\n"+q4+"\n"+q5+"\n");

        // Test RetrieveQuestions
        System.out.println("----- TEST RETRIEVE QUESTIONS -----");
        RetrieveQuestions retrieveQuestions = RetrieveQuestions.getInstance();
        JSONObject questionJson = retrieveQuestions.RetrieveRandomQuestionFromCategory("History");
        System.out.println("Random History Question: \n"+questionJson.toString());
        JSONObject allQuestions = retrieveQuestions.RetrieveAllQuestions();
        System.out.println("All your Questions: \n"+allQuestions.toString());
        // TODO: Finish These

        // Test ModifyQuestions
        System.out.println("----- TEST MODIFY QUESTIONS -----");
        // TODO: Finish These

    }
}
