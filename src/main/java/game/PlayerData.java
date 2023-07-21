package game;

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
    private static String[] playerColors;

    public PlayerData(int numPlayers, String[] playerNames, String[] playerColors) {
        this.playerColors = playerColors;

        for (int i = 0; i < numPlayers; i++) {
            setPlayerName(i, playerNames[i]);
            setPlayerColor(i, playerColors[i]);
        }

        playerPositions = new int[playerCount][2];
        playerScores = new boolean[playerCount][4];
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
    public static void setPlayerColor(int playerIndex, String color) {
        playerColors[playerIndex] = color;
    }

    /**
     * @param playerIndex
     * @return color
     */
    public static String getPlayerColor(int playerIndex) {
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
        playerColors = new String[4];
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

    private static int uniqCount(String[] list) {
        System.out.println(list[0] + list[1] + list[2] + list[3]);
        return (int)Arrays.stream(Arrays.stream(list)
                .filter(val -> val != null)
                .toArray()).distinct().count();
    }
}
