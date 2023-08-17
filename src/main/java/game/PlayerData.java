package game;

import org.json.JSONArray;

import java.awt.*;
import java.util.Arrays;

/**
 *
 */
public class PlayerData {
    private static String[] playerNames = new String[4];
    private static int playerCount;
    // a 2D array to track player positions
    private static int[][] playerPositions;
    // a 2D array to track player scores
    private static boolean[][] playerScores;
    // array to store player colors
    private static Color[] playerColors;

    // Singleton instance
    private static PlayerData instance = null;
    private static int multiplayerIndex;

    private PlayerData(int numPlayers, String[] playerNames, Color[] playerColors) {
        playerPositions = new int[numPlayers][2];
        for (int i = 0; i < numPlayers; i++) {
            setPlayerName(i, playerNames[i]);  // This will increment playerCount
            setPlayerColor(i, playerColors[i]);  // This will update playerColors
            setPlayerPositions(i, i * 2, 0);  // This will update playerPositions
        }
        playerScores = new boolean[playerCount][4];
    }

    // Singleton getInstance method
    public static PlayerData getInstance(int numPlayers, String[] playerNames, Color[] playerColors) {
        if (instance == null) {
            instance = new PlayerData(numPlayers, playerNames, playerColors);
        }
        return instance;
    }

    /**
     * @param playerIndex
     * @param name
     */
    public static void setPlayerName(int playerIndex, String name) {
        playerNames[playerIndex] = name;
        if(name != null && !name.trim().isEmpty()) {
            // Only count values that are supplied
            playerCount++;
        }
    }

    /**
     * @param playerIndex
     * @return
     */
    public static String getPlayerName(int playerIndex) {
        return playerNames[playerIndex];
    }

    /**
     * @param playerIndex
     * @param color
     */
    public static void setPlayerColor(int playerIndex, Color color) {
        playerColors[playerIndex] = color;
    }

    /**
     * @param playerIndex
     * @return color
     */
    public static Color getPlayerColor(int playerIndex) {
        try {
            return playerColors[playerIndex];
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public static int getUniqueColorCount(){
        return uniqCount(playerColors);
    }

    /**
     * @return
     */
    public static int getPlayerCount(){
        return playerCount;
    }

    /**
     *
     */
    public static void flushPlayerNames(){
        //TODO: may want to make this to flush all player data instead.
        playerNames = new String[4];
        playerCount = 0;
    }

    public static void flushPlayerColors(){
        //TODO: may want to make this to flush all player data instead.
        playerColors = new Color[4];
    }
    public static int[][] getPlayerPositions(int playerIndex) {
        int[][] playerPosition = new int[1][2];
        playerPosition[0][0] = playerPositions[playerIndex][0];
        playerPosition[0][1] = playerPositions[playerIndex][1];
        return playerPosition;
    }
    public static int[] getPlayerPosition(int playerIndex) {
        int[] playerPosition = new int[2];
        playerPosition[0] = playerPositions[playerIndex][0];  // i
        playerPosition[1] = playerPositions[playerIndex][1];  // j
        return playerPosition;
    }

    public static void setPlayerPositions(int playerIndex, int playerX, int playerY) {
        playerPositions[playerIndex][0] = playerX;
        playerPositions[playerIndex][1] = playerY;
    }

    public boolean[][] getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(boolean[][] playerScores) {
        this.playerScores = playerScores;
    }

    private static int uniqCount(Object[] list) {
        return (int)Arrays.stream(Arrays.stream(list)
                .filter(val -> val != null)
                .toArray()).distinct().count();
    }

    public static void incrementPlayerScore(int playerIndex, int categoryIndex){
        if ((playerIndex < 0 || playerIndex > playerCount - 1) || (categoryIndex < 0 || categoryIndex > 3)){
            throw new RuntimeException("Invalid Increment Player Score Option.");
        }
        playerScores[playerIndex][categoryIndex] = true;
    }
    public static int checkWinConditionAndReturnPlayerIndex(){
        for (int i = 0; i < playerCount - 1; i++){ // Iterate Over Players
            boolean isWinner = true;
            for (int j = 0; j < 4; j++){  // Iterate Over Categories
                if (!playerScores[i][j]){
                    isWinner = false;
                }
            }
            if (isWinner){
                return i;
            }
        }
        return -1;
    }

    /* ********** Multiplayer Methods ********** */
    public static void setMultiplayerNamesAndOrder(JSONArray multiplayerPlayerNames, JSONArray multiplayerOrder){
        playerNames = new String[multiplayerPlayerNames.length()];
        for (int i = 0; i < multiplayerPlayerNames.length(); i++){
            int order = multiplayerOrder.getInt(i);
            String name = multiplayerPlayerNames.getString(order);
            playerNames[order] = name;
        }
        playerCount = playerNames.length;
    }

    public static String[] getPlayerNames() {
        return playerNames;
    }

    public static int getClientMultiplayerIndex(){
        return multiplayerIndex;
    }
    public static void setClientMultiplayerIndex(int index){
        multiplayerIndex = index;
    }

    public static void setPlayerColors(JSONArray playerColors) {
        Color[] colors = new Color[playerColors.length()];
        for (int i = 0; i < playerColors.length(); i++){
            colors[i] = GameData.getColorByName(playerColors.getString(i));
        }
        PlayerData.playerColors = colors;
    }
}
