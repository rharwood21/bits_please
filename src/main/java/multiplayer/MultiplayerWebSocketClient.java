package multiplayer;

import bits_please_api.ConfigReader;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class MultiplayerWebSocketClient {
    private static MultiplayerWebSocketClient instance;
    private WebSocketClient webSocketClient;
    ConfigReader configReader;
    Map<String, Consumer<JSONObject>> commandHandlers;
    private final Map<String, CompletableFuture<JSONObject>> pendingRequests = new HashMap<>();
    private MultiplayerWebSocketClient(){
        configReader = new ConfigReader();
        String wsServerLocation = configReader.getMultiplayerWebSocketEndpoint();
        try {
            webSocketClient = new WebSocketClient(new URI(wsServerLocation), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("WebSocket Connection Open: "+getURI());
                }

                @Override
                public void onMessage(String serverMessage) {
                    System.out.println("Server Message Received:\n"+serverMessage);
                    try {
                        JSONObject message = new JSONObject(serverMessage);
                        // Handle the command
                        String commandType = message.getString("command");
                        Consumer<JSONObject> handler = getCommandHandlers().get(commandType);

                        if (message.has("request_id")){ // Handle Synchronous Messsage
                            String requestId = message.getString("request_id");
                            CompletableFuture<JSONObject> future = pendingRequests.remove(requestId);
                            if (handler != null) {
                                handler.accept(message);
                            }
                            if (future != null) {
                                future.complete(message);
                            }
                        }
                        // Asynchronous Messages
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

            webSocketClient.connect();
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

    public void sendAsyncMessage(JSONObject message){
        System.out.println("Client Sent Async Message:\n"+message.toString());
        webSocketClient.send(message.toString());
    }

    public JSONObject sendSyncMessage(JSONObject message) throws MultiplayerException {
        System.out.println("Client Sent Synchronous Message:\n"+message.toString());
        String requestId = UUID.randomUUID().toString();
        message.put("request_id", requestId);

        CompletableFuture<JSONObject> future = new CompletableFuture<>();
        pendingRequests.put(requestId, future);

        webSocketClient.send(message.toString());

        // Wait for the response (with a timeout of, say, 10 seconds).
        // You can adjust the timeout or remove it entirely if you're sure the server will always respond.
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e){
            throw new MultiplayerException(e.toString());
        }
    }

    public void close() {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }

    public Map<String, Consumer<JSONObject>> getCommandHandlers() {
        if (commandHandlers == null) {
            commandHandlers = GameplayController.getInstance().getCommandHandlers();
        }
        return commandHandlers;
    }

}