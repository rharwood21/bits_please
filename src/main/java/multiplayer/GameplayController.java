package multiplayer;

import bits_please_api.InitializeClient;
import game.GameController;
import game.GameData;
import game.PlayerData;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
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
    private static GameController controller;

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
        commandHandlers.put("new_player", this::handleNewPlayer);
        commandHandlers.put("get_players", this::handleGetPlayers);
        commandHandlers.put("set_category_colors", this::handleSetCategoryColor);
        commandHandlers.put("set_player_colors", this::handleSetPlayerColors);
        commandHandlers.put("color_choice", this::handleColorChoice);

        webSocketClient = MultiplayerWebSocketClient.getInstance();
    }
    public static GameplayController getInstance(GameController guiController){
        controller = guiController;
        if (instance == null) {
            instance = new GameplayController();
        }
        return instance;
    }
    public static GameplayController getInstance(){
        if (instance == null) {
            instance = new GameplayController();
        }
        return instance;
    }


    /* ******************** Commands (Client-to-Server) ******************** */
    public static void doInitialize(String playerName) throws MultiplayerException {
        JSONObject message = getBaseCommand("initialize");
        message.put("client_uuid", clientUUID);
        message.put("player_name", playerName);
        webSocketClient.sendSyncMessage(message);
    }
    public static void doGetPlayers(){
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("get_players");
        webSocketClient.sendSyncMessage(message);
    }
    public static void doJoinRoom(String roomKey, String playerName){
        JSONObject message = getBaseCommand("join_room");
        message.put("room_key", roomKey);
        message.put("client_uuid", clientUUID);
        message.put("player_name", playerName);
        webSocketClient.sendSyncMessage(message);
    }

    public static void doSetCategoryColors(JSONArray categories, JSONArray colors){
        checkCanInvokeFunction(true, true, false);
        JSONObject message = getBaseCommand("set_category_colors");
        message.put("categories", categories);
        message.put("colors", colors);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doSetPlayerColors(JSONArray colors){
        checkCanInvokeFunction(true, true, false);
        JSONObject message = getBaseCommand("set_player_colors");
        message.put("ordered_colors", colors);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doColorChoice(String color){
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("color_choice");
        message.put("selected_color", color);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doLeaveRoom(){
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("leave_room");
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doCloseRoom(){
        checkCanInvokeFunction(true, true, false);
        JSONObject message = getBaseCommand("close_room");
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doStartGame(){
        checkCanInvokeFunction(true, true, false);
        JSONObject message = getBaseCommand("start_game");
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doDiceRoll(int diceRoll){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("dice_roll");
        message.put("roll", diceRoll);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doUpdateCoordinates(int newI, int newJ){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("update_coordinates");
        message.put("i", newI);
        message.put("j", newJ);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doSelectedQuestion(String questionUUID){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("selected_question");
        message.put("question_uuid", questionUUID);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doSelectedAnswer(String questionUUID, String answer){
        checkCanInvokeFunction(true, false, true);
        JSONObject message = getBaseCommand("selected_answer");
        message.put("question_uuid", questionUUID);
        message.put("answer", answer);
        webSocketClient.sendAsyncMessage(message);
    }
    public static void doUpdateGameState(){
        // TODO: Implement
        checkCanInvokeFunction(true, false, false);
        JSONObject message = getBaseCommand("game_state");

        webSocketClient.sendAsyncMessage(message);
    }


    /* ******************** Command Handlers (Server-to-Client) ******************** */

    private void handleInitialize(JSONObject message) {
        checkServerError(message);
        roomKey = message.getString("room_key");
        isRoomMaster = true;
        PlayerData.setClientMultiplayerIndex(message.getInt("player_order"));
    }
    private void handleJoinRoom(JSONObject message) {
        checkServerError(message);
        roomKey = message.getString("room_key");
        isRoomMaster = false;
        PlayerData.setClientMultiplayerIndex(message.getInt("player_order"));
    }
    private void handleSetCategoryColor(JSONObject message){
        checkServerError(message);
        JSONArray categories = message.getJSONArray("categories");
        JSONArray colors = message.getJSONArray("colors");
        // Save the categories and colors
        GameData.setMultiplayerCategoriesAndColor(categories, colors);
    }
    private void handleNewPlayer(JSONObject message) {
        checkServerError(message);
        JSONArray playerNames = message.getJSONArray("player_names");
        JSONArray playerIndices = message.getJSONArray("player_order");
        PlayerData.setMultiplayerNamesAndOrder(playerNames, playerIndices);
        controller.refreshNameInputPage();
    }
    private void handleSetPlayerColors(JSONObject message){
        checkServerError(message);
        JSONArray colors = message.getJSONArray("ordered_colors");
        PlayerData.setPlayerColors(colors);
    }
    private void handleGetPlayers(JSONObject message){
        checkServerError(message);
        JSONArray playerNames = message.getJSONArray("player_names");
        JSONArray playerIndices = message.getJSONArray("player_order");
        PlayerData.setMultiplayerNamesAndOrder(playerNames, playerIndices);
        controller.refreshNameInputPage();
    }
    private void handleColorChoice(JSONObject message){
        checkServerError(message);
        int playerIndex = message.getInt("player_index");
        String color = message.getString("selected_color");
        controller.playerColorChoice(playerIndex, color);
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
    public void showRoomKey(boolean isRoomCreator) {
        String message = isRoomCreator ? "Share this room key with other players: " : "Your room key is: ";

        // Use a layout manager to control the display
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(new JLabel(message), BorderLayout.NORTH);

        // Create a JTextField to display the room key
        JTextField roomKeyField = new JTextField(roomKey);
        roomKeyField.setEditable(false); // So users can't modify it
        roomKeyField.setBorder(null); // To make it look like a JLabel
        roomKeyField.setBackground(messagePanel.getBackground());

        // Increase the font size of the room key
        Font currentFont = roomKeyField.getFont();
        roomKeyField.setFont(new Font(currentFont.getFontName(), currentFont.getStyle(), 20)); // Set size to 20, for example

        messagePanel.add(roomKeyField, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(null, messagePanel, "Room Key", JOptionPane.INFORMATION_MESSAGE);
    }

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
    private void checkServerError(JSONObject message) throws MultiplayerException {
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
