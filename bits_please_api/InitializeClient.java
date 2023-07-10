package bits_please_api;

import org.json.JSONObject;
import java.io.IOException;

public class InitializeClient {
    private static InitializeClient instance;
    private String clientUUID;
    private String apiKey;
    private String apiUrl;
    private BitsPleaseRequest apiRequest;

    private InitializeClient() {
        // Obtain the UUID from the REST API initialization endpoint
        ConfigReader configReader = new ConfigReader();
        apiKey = configReader.getAPIKey();
        apiUrl = configReader.getInitializeClientEndpoint();
        apiRequest = new BitsPleaseRequest(apiKey);
        // Check if clientUUID already exists in the config
        String storedUUID = configReader.getProperties().getProperty("user.uuid");
        if (storedUUID != null && !storedUUID.isEmpty()) {
            clientUUID = storedUUID;
        } else {
            // Initialize UUID from REST API
            clientUUID = initializeUUID();
            configReader.updateConfigFileIfNeeded(clientUUID);
        }
    }
    public static InitializeClient getInstance() {
        if (instance == null) {
            instance = new InitializeClient();
        }
        return instance;
    }

    private String initializeUUID() {
        JSONObject jsonPayload = new JSONObject().put("api_key", apiKey).put("action", "INITIALIZE");
        try {
            JSONObject jsonObject = apiRequest.sendRequest(apiUrl, jsonPayload);
            return jsonObject.getString("result");
        } catch (IOException e){
            System.err.println(e);
            return null; // Return null as a placeholder if UUID extraction fails
        }
    }

    public String getClientUUID() {
        return clientUUID;
    }

    public BitsPleaseRequest getBitsPleaseRequest(){
        return apiRequest;
    }

    public boolean VerifyClient() {
        return isValidUUID(clientUUID);
    }

    public boolean VerifyClient(String uuid) {
        return isValidUUID(uuid);
    }

    private boolean isValidUUID(String uuid) {
        JSONObject jsonPayload = new JSONObject().put("api_key", apiKey).put("action", "VERIFY").put("uuid", uuid);
        try {
            JSONObject jsonObject = apiRequest.sendRequest(apiUrl, jsonPayload);
            String result = jsonObject.getString("result");
            return result.toLowerCase().equals("pass");
        } catch (IOException e){
            System.err.println(e);
        }
        return false;
    }
}
