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

    public String CreateQuestion(
            String category, String question, String answer, String choice1,
            String choice2, String choice3, String choice4, QuestionDifficulty difficulty
    ) {
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("category", category).put("question", question);
        jsonPayload.put("choice1", choice1).put("choice2", choice2).put("choice3", choice3).put("choice4", choice4);
        jsonPayload.put("answer", answer).put("difficulty", difficulty.getValue());
        try {
            JSONObject jsonObject = apiRequest.sendRequest(apiUrl, jsonPayload);
            String result = jsonObject.getString("result");
            if (result.equalsIgnoreCase("success") && jsonObject.has("question_uuid")){
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
