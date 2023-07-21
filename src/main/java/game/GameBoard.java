package game;

import java.awt.*;

/** The GameBoard class represents the underlying Square objects that combine to make a 9x9 grid.
 *  This Grid is used to play the trivial compute board game.
 */
public class GameBoard {
    // a 2D array to create the coordinate system for the board
    private Square[][] squares;
    private static int numCols = 9;
    private static int numRows = 9;

    /** The Constructor Method for the GameBoard Class. All GameBoards are the same so no inputs are necessary.
     * Note: A future version might seek for each board to be unique.
     */
    public GameBoard() {
        squares = new Square[numCols][numRows];
        String color = "";
        String type = "";
        int HQSwitcher = 0;     // This is used to make sure there is an HQ square for each color.
        int k = 0;      // Using this variable to test ways to generate the colors for normal question squares.
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                switch (k % 4) {
                    case 0:
                        color = "R";    // R - Red
                        break;
                    case 1:
                        color = "Y";        // Y - Yellow
                        break;
                    case 2:
                        color = "B";        // B - Blue
                        break;
                    case 3:
                        color = "G";        // G - Green
                        break;
                }
                k++;
                // Making the corners of the board in to Roll Again Squares.
                if ((i == 0 || i == 8) && (j == 0 || j == 8)) {
                    type = "Roll";      // Roll - short for Roll Again
                    color = "P";        // P - Pink
                    //TODO: make enums for types and colors
                } else if ((i == 0 && j == 4) ||
                        (i == 4 && j == 0) ||
                        (i == 8 && j == 4) ||
                        (i == 4 && j == 8)) {
                    type = "HQ";
                    // Ensuring that each HQ Square is a different color and the game is winnable!
                    switch (HQSwitcher) {
                        case 0:
                            color = "R";
                            HQSwitcher++;
                            break;
                        case 1:
                            color = "Y";
                            HQSwitcher++;
                            break;
                        case 2:
                            color = "B";
                            HQSwitcher++;
                            break;
                        case 3:
                            color = "G";
                            HQSwitcher++;
                            break;
                    }
                } else if (i == 4 && j == 4) {
                    // The center of the board is the Trivial Compute Square
                    type = "TC";
                    color = "W";
                } else if (
                        ((0 < i && i < 4) || (4 < i && i < 8))
                    && ((0 < j && j < 4) || (4 < j && j < 8))
                ) {
                    // Four different 3x3 sections of the board do not contain game squares
                    // These "Dead" areas will be home to the players' score graphics.
                    type = "Dead";
                    //color = "W";        // Color won't matter but they can be white.
                } else {
                    type = "Q";         // Q - Normal Question square.
                }
                // Adding the new square to the array.
                squares[i][j] = new Square(new Point(i, j), color, type);
                //System.out.print(color); // Debugging for Text version of Board
            }
            //System.out.println(); // Debugging for Text version of board
        }
    }

    /** Method to retrieve a square from a GameBoard using it's position within the 9x9 grid.
     * @param i - represents the row of the grid. Min value of 0 and Max value of 8.
     * @param j - represents the column of the grid. Min value of 0 and Max value of 8.
     * @return - returns a Square object at the i,j location requested.
     */
    public Square getSquare(int i, int j) {
        return squares[i][j];
    }
}
