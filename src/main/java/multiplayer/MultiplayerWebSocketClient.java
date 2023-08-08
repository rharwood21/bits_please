package multiplayer;

import bits_please_api.ConfigReader;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Map;
import java.util.function.Consumer;

public class MultiplayerWebSocketClient {
    private static MultiplayerWebSocketClient instance;
    private WebSocketClient webSocketClient;
    ConfigReader configReader = new ConfigReader();
    Map<String, Consumer<JSONObject>> commandHandlers = GameplayController.getInstance().getCommandHandlers();
    private MultiplayerWebSocketClient(){
        String wsServerLocation = configReader.getMultiplayerWebSocketEndpoint();
        try {
            webSocketClient = new WebSocketClient(new URI(wsServerLocation), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("WebSocket Connection Open: "+getURI());
                }

                @Override
                public void onMessage(String serverMessage) {
                    try {
                        JSONObject message = new JSONObject(serverMessage);

                        // Handle the command
                        String commandType = message.getString("command");
                        Consumer<JSONObject> handler = commandHandlers.get(commandType);

                        if (handler != null) {
                            handler.accept(message);
                        } else {
                            System.out.println("Unknown command: " + commandType);
                        }

                    } catch (JSONException e){
                        System.out.println("Invalid JSON from Server: "+serverMessage);
                    }

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("WebSocket Connection Closed: "+getURI());
                }

                @Override
                public void onError(Exception e) {
                    throw new MultiplayerException(e.getMessage());
                }
            };
        } catch (Exception e){
            throw new MultiplayerException(e.getMessage());
        }
    }
    public static MultiplayerWebSocketClient getInstance() {
        if (instance == null) {
            instance = new MultiplayerWebSocketClient();
        }
        return instance;
    }

    public void sendMessage(JSONObject message){
        webSocketClient.send(message.toString());
    }

    public void close() {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }
}