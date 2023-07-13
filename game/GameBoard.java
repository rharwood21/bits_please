package game;

public class GameBoard {
    // a 2D array to create the coordinate system for the board
    private static int[][] coordinateSystem = new int[9][9];
    // creating logical values for valid squares on the gameboard

    public GameBoard() {
        /* Constructor method for GameBoard Class
        *
        *
        * */
        // Making the corners of the board 6 - "Roll Again"
        coordinateSystem[0][0] = 6;
        coordinateSystem[8][0] = 6;
        coordinateSystem[8][8] = 6;
        coordinateSystem[0][8] = 6;
        // Making the center square 7 - "Trivial Compute"
        coordinateSystem[4][4] = 7;
        // Assigning the squares a value between 1-4 to make the colors later
        // I feel really dumb, but I can't think of a loop to describe the order of colors...
        // ...might have to hardcode them


    }
}
