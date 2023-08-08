package multiplayer;

import bits_please_api.InitializeClient;
import game.PlayerData;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GameplayController {

    /* ******************** GameController Fields ******************** */
    private Map<String, Consumer<JSONObject>> commandHandlers = new HashMap<>();
    private static GameplayController instance;
    private static MultiplayerWebSocketClient webSocketClient;

    private static String roomKey;
    private static String clientUUID = InitializeClient.getInstance().getClientUUID();

    private static boolean isRoomMaster = false;

    /* ******************** GameplayPage Fields ******************** */
    // TODO

    /* ******************** Singleton Methods ******************** */

    private GameplayController() {
        commandHandlers.put("initialize", this::handleInitialize);
        commandHandlers.put("join_room", this::handleJoinRoom);
        commandHandlers.put("leave_room", this::handleLeaveRoom);
        commandHandlers.put("close_room", this::handleCloseRoom);
        commandHandlers.put("start_game", this::handleStartGame);
        commandHandlers.put("dice_roll", this::handleDiceRoll);
        commandHandlers.put("update_coordinates", this::handleUpdateCoordinates);
        commandHandlers.put("selected_question", this::handleSelectedQuestion);
        commandHandlers.put("selected_answer", this::handleSelectedAnswer);
        commandHandlers.put("game_state", this::handleGameState);

        webSocketClient = MultiplayerWebSocketClient.getInstance();
    }

    public static GameplayController getInstance(){
        if (instance == null) {
            instance = new GameplayController();
        }
        return instance;
    }

    /* ******************** Commands (Client-to-Server) ******************** */
    public static void doInitialize(){
        JSONObject message = getBaseCommand("initialize");
        message.put("client_uuid", clientUUID);
        message.put("player_order", PlayerData.getClientMultiplayerIndex());
        webSocketClient.sendMessage(message);
    }
    public static void doGetPlayers(){
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("get_players");
        webSocketClient.sendMessage(message);
    }
    public static void doJoinRoom(String roomKey){
        JSONObject message = getBaseCommand("join_room");
        message.put("client_uuid", clientUUID);
        message.put("player_order", PlayerData.getClientMultiplayerIndex());
        webSocketClient.sendMessage(message);
    }
    public static void doLeaveRoom(){
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("leave_room");
        webSocketClient.sendMessage(message);
    }
    public static void doCloseRoom(){
        checkCanInvokeFunction(true, true, false);
        JSONObject message = getBaseCommand("close_room");
        webSocketClient.sendMessage(message);
    }
    public static void doStartGame(){
        checkCanInvokeFunction(true, true, false);
        JSONObject message = getBaseCommand("start_game");
        webSocketClient.sendMessage(message);
    }
    public static void doDiceRoll(int diceRoll){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("dice_roll");
        message.put("roll", diceRoll);
        webSocketClient.sendMessage(message);
    }
    public static void doUpdateCoordinates(int newI, int newJ){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("update_coordinates");
        message.put("i", newI);
        message.put("j", newJ);
        webSocketClient.sendMessage(message);
    }
    public static void doSelectedQuestion(String questionUUID){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("selected_question");
        message.put("question_uuid", questionUUID);
        webSocketClient.sendMessage(message);
    }
    public static void doSelectedAnswer(String questionUUID, String answer){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("selected_answer");
        message.put("question_uuid", questionUUID);
        message.put("answer", answer);
        webSocketClient.sendMessage(message);
    }
    public static void doUpdateGameState(){
        // TODO: Implement
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("game_state");

        webSocketClient.sendMessage(message);
    }


    /* ******************** Command Handlers (Server-to-Client) ******************** */

    private void handleInitialize(JSONObject message) {
        checkServerError(message);
        roomKey = message.getString("room_key");
        isRoomMaster = true;
    }
    private void handleJoinRoom(JSONObject message) {
        checkServerError(message);
        roomKey = message.getString("room_key");
        isRoomMaster = false;
    }
    private void handleLeaveRoom(JSONObject message){
        checkServerError(message);
        webSocketClient.close();
        instance = null;
    }
    private void handleCloseRoom(JSONObject message){
        checkServerError(message);
        webSocketClient.close();
        instance = null;
    }
    private void handleStartGame(JSONObject message){
        checkServerError(message);

    }
    private void handleDiceRoll(JSONObject message){
        checkServerError(message);

    }
    private void handleUpdateCoordinates(JSONObject message){
        checkServerError(message);

    }
    private void handleSelectedQuestion(JSONObject message){
        checkServerError(message);

    }
    private void handleSelectedAnswer(JSONObject message){
        checkServerError(message);

    }
    private void handleGameState(JSONObject message){
        checkServerError(message);

    }

    /* ******************** Helper Methods ******************** */
    public static String getRoomKey() {
        return roomKey;
    }
    public Map<String, Consumer<JSONObject>> getCommandHandlers() {
        return commandHandlers;
    }
    private static JSONObject getBaseCommand(String command){
        JSONObject message = new JSONObject();
        message.put("command", command);
        if (roomKey != null){
            message.put("room_key", roomKey);
        }
        return message;
    }
    private void checkServerError(JSONObject message){
        if (message.has("error")){
            throw new MultiplayerException(message.getString("error"));
        }
    }
    private static boolean hasJoinedRoom(){
        return roomKey != null;
    }
    private static boolean checkCanInvokeFunction(boolean requiresJoinedRoom, boolean requiresIsMaster, boolean requiresCurrentPlayer){
        if (requiresJoinedRoom && !hasJoinedRoom()){
            throw new MultiplayerException("Have not joined room yet.");
        } else if (requiresIsMaster && !isRoomMaster){
            throw new MultiplayerException("Cannot start game unless you are Room Master.");
        }
//        else if (requiresCurrentPlayer && !isCurrentPlayer) {
//
//        }
        return true;
    }
}
