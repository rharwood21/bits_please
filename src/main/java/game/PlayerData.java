package game;

import java.awt.*;
import java.util.Arrays;

/**
 *
 */
public class PlayerData {
    private static String[] playerNames;
    private static int playerCount;
    // a 2D array to track player positions
    private static int[][] playerPositions;
    // a 2D array to track player scores
    private boolean[][] playerScores;
    // array to store player colors
    private static Color[] playerColors;

    // Singleton instance
    private static PlayerData instance = null;

    private PlayerData(int numPlayers, String[] playerNames, Color[] playerColors) {
        this.playerColors = playerColors;

        for (int i = 0; i < numPlayers; i++) {
            setPlayerName(i, playerNames[i]);
            setPlayerColor(i, playerColors[i]);
        }

        playerPositions = new int[playerCount][2];
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
        return playerColors[playerIndex];
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
    public static int[][] getPlayerPositions() {
        return playerPositions;
    }

    public static void setPlayerPositions(int[][] playerPositions) {
        PlayerData.playerPositions = playerPositions;
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
}
