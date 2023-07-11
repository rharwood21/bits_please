package bits_please_api;

import org.json.JSONObject;

import java.io.IOException;

public class ModifyQuestions {
    private static ModifyQuestions instance;
    private String clientUUID;
    private String apiKey;
    private String apiUrl;
    private BitsPleaseRequest apiRequest;

    private ModifyQuestions(){
        ConfigReader configReader = new ConfigReader();
        apiKey = configReader.getAPIKey();
        apiUrl = configReader.getModifyQuestionEndpoint();

        InitializeClient initialClient = InitializeClient.getInstance();
        clientUUID = initialClient.getClientUUID();
        apiRequest = initialClient.getBitsPleaseRequest();
    }
    public static ModifyQuestions getInstance() {
        if (instance == null) {
            instance = new ModifyQuestions();
        }
        return instance;
    }

    public boolean DeleteQuestion(String question_uuid){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("question_uuid", question_uuid).put("action", "DELETE");
        try {
            JSONObject jsonObject = apiRequest.sendRequest(apiUrl, jsonPayload);
            return jsonObject.getString("result").toLowerCase().equals("success");
        } catch (IOException e){
            System.err.println(e);
        }
        return false;
    }
    public boolean ModifyQuestion(String question_uuid, String modified_category, String modified_question,
                                  String modified_choice1, String modified_choice2, String modified_choice3,
                                  String modified_choice4, String modified_answer, QuestionDifficulty modified_difficulty
    ){
        JSONObject jsonPayload = new JSONObject().put("uuid", clientUUID).put("question_uuid", question_uuid).put("action", "MODIFY");
        jsonPayload.put("modified_category", modified_category).put("modified_question", modified_question);
        jsonPayload.put("modified_answer", modified_answer).put("modified_difficulty", modified_difficulty.getValue());
        jsonPayload.put("modified_choice1", modified_choice1).put("modified_choice2", modified_choice2);
        jsonPayload.put("modified_choice3", modified_choice3).put("modified_choice4", modified_choice4);
        try {
            JSONObject jsonObject = apiRequest.sendRequest(apiUrl, jsonPayload);
            return jsonObject.getString("result").toLowerCase().equals("success");
        } catch (IOException e){
            System.err.println(e);
        }
        return false;
    }
}