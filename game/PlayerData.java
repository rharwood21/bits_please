package game;

public class PlayerData {
    private static String[] playerNames = new String[4];
    private static int playerCount = 0;

    public static void setPlayerName(int playerIndex, String name) {
        playerNames[playerIndex] = name;
        if(name != null && !name.trim().isEmpty()) {
            // Only count values that are supplied
            playerCount++;
        }
    }

    public static String getPlayerName(int playerIndex) {
        return playerNames[playerIndex];
    }

    public static int getPlayerCount(){
        return playerCount;
    }

    public static void flushPlayerNames(){
        playerNames = new String[4];
        playerCount = 0;
    }
}
