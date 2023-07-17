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

    public JSONQuestions RetrieveRandomQuestionFromCategory(String category, QuestionDifficulty difficulty) throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category);
        jsonPayload.put("difficulty", difficulty.getValue()).put("select", "RANDOM_CATEGORY");
        return sendRetrieveRequest(jsonPayload);
    }
    public JSONQuestions RetrieveRandomQuestionFromAllCategories(QuestionDifficulty difficulty) throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all");
        jsonPayload.put("difficulty", difficulty.getValue()).put("select", "RANDOM_ALL");
        return sendRetrieveRequest(jsonPayload);
    }
    public JSONQuestions RetrieveAllQuestionsFromCategory(String category) throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category);
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "ALL_CATEGORY"); // Difficulty required, but not used
        return sendRetrieveRequest(jsonPayload);
    }
    public JSONQuestions RetrieveAllQuestions() throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all");
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "ALL"); // Difficulty required, but not used
        return sendRetrieveRequest(jsonPayload);
    }

    public JSONQuestions RetrieveQuestionFromQuestionUUID(String questionUuid) throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all").put("select", "FROM_QUESTION_UUID");
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("question_uuid", questionUuid); // Difficulty required, but not used
        return sendRetrieveRequest(jsonPayload);
    }

    public JSONQuestions RetrieveAllDefaultQuestions() throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", "all"); // Category required, but not used
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "ALL"); // Difficulty required, but not used
        jsonPayload.put("default", "True");
        return sendRetrieveRequest(jsonPayload);
    }

    public JSONQuestions RetrieveDefaultQuestionsByCategory(String category) throws APIRequestException {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category);
        jsonPayload.put("difficulty", QuestionDifficulty.EASY.getValue()).put("select", "ALL_CATEGORY"); // Difficulty required, but not used
        jsonPayload.put("default", "True");
        return sendRetrieveRequest(jsonPayload);
    }

    private JSONQuestions sendRetrieveRequest(JSONObject jsonPayload) throws APIRequestException {
        try {
            JSONObject response = apiRequest.sendRequest(apiUrl, jsonPayload);
            if (response.has("result")){
                throw new APIRequestException(response.getString("result")); // Error has occurred.
            }
            return new JSONQuestions(response);
        } catch (IOException e){
            System.err.println(e);
        }
        return null;
    }
}