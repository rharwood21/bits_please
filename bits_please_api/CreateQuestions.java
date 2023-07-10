package bits_please_api;

import org.json.JSONObject;

import java.io.IOException;

public class CreateQuestions {
    private static CreateQuestions instance;
    private String clientUUID;
    private String apiKey;
    private String apiUrl;
    private BitsPleaseRequest apiRequest;

    private CreateQuestions(){
        ConfigReader configReader = new ConfigReader();
        apiKey = configReader.getAPIKey();
        apiUrl = configReader.getCreateQuestionsEndpoint();

        InitializeClient initialClient = InitializeClient.getInstance();
        clientUUID = initialClient.getClientUUID();
        apiRequest = initialClient.getBitsPleaseRequest();
    }
    public static CreateQuestions getInstance() {
        if (instance == null) {
            instance = new CreateQuestions();
        }
        return instance;
    }

    public String CreateQuestion(String category, String question, String answer) {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category).put("question", question).put("answer", answer);
        try {
            JSONObject jsonObject = apiRequest.sendRequest(apiUrl, jsonPayload);
            String result = jsonObject.getString("result");
            if (result.toLowerCase().equals("success") && jsonObject.has("question_uuid")){
                return jsonObject.getString("question_uuid");
            } else {
                throw new APIRequestException(result);
            }
        } catch (IOException e){
            System.err.println(e);
            return null; // Return null as a placeholder if UUID extraction fails
        }
    }
}
