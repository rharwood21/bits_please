package bits_please_api;

import org.json.JSONObject;

import java.io.IOException;

public class RetrieveQuestions {
    private static RetrieveQuestions instance;
    private String clientUUID;
    private String apiKey;
    private String apiUrl;
    private BitsPleaseRequest apiRequest;

    private RetrieveQuestions(){
        ConfigReader configReader = new ConfigReader();
        apiKey = configReader.getAPIKey();
        apiUrl = configReader.getRetrieveQuestionEndpoint();

        InitializeClient initialClient = InitializeClient.getInstance();
        clientUUID = initialClient.getClientUUID();
        apiRequest = initialClient.getBitsPleaseRequest();
    }
    public static RetrieveQuestions getInstance() {
        if (instance == null) {
            instance = new RetrieveQuestions();
        }
        return instance;
    }

    public JSONObject RetrieveRandomQuestionFromCategory(String category, QuestionDifficulty difficulty){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category);
        jsonPayload.put("difficulty", difficulty.getValue()).put("select", "RANDOM_CATEGORY");
        return sendRetrieveRequest(jsonPayload);
    }
    public JSONObject RetrieveRandomQuestionFromAllCategories(QuestionDifficulty difficulty){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all");
        jsonPayload.put("difficulty", difficulty.getValue()).put("select", "RANDOM_ALL");
        return sendRetrieveRequest(jsonPayload);
    }
    public JSONObject RetrieveAllQuestionsFromCategory(String category){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category);
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "ALL_CATEGORY"); // Difficulty required, but not used
        return sendRetrieveRequest(jsonPayload);
    }
    public JSONObject RetrieveAllQuestions(){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all");
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "ALL"); // Difficulty required, but not used
        return sendRetrieveRequest(jsonPayload);
    }

    public JSONObject RetrieveQuestionFromQuestionUUID(String questionUuid){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all").put("question_uuid", questionUuid);
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "FROM_QUESTION_UUID"); // Difficulty required, but not used
        return sendRetrieveRequest(jsonPayload);
    }

    private JSONObject sendRetrieveRequest(JSONObject jsonPayload){
        try {
            return apiRequest.sendRequest(apiUrl, jsonPayload);
        } catch (IOException e){
            System.err.println(e);
        }
        return null;
    }
}