package game;

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

    public PlayerData(int numPlayers, String[] playerNames) {
        this.playerCount = numPlayers;

        for (int i = 0; i < numPlayers; i++) {
            setPlayerName(i, playerNames[i]);
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
}
