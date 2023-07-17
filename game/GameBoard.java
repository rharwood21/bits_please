package game;

import java.awt.*;

public class GameBoard {
    // a 2D array to create the coordinate system for the board
    private Square[][] squares;
    private static int numCols = 9;
    private static int numRows = 9;
    // creating logical values for valid squares on the gameboard

    public GameBoard() {
        /* Constructor method for GameBoard Class
        *
        *
        * */
        String color = ""; // Default color is 'W' for White.
        String type = "";
        int HQSwitcher = 0;
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numRows; j++) {
                switch (j % 4) {
                    case 0:
                        color = "R";
                        break;
                    case 1:
                        color = "Y";
                        break;
                    case 2:
                        color = "B";
                        break;
                    case 3:
                        color = "G";
                        break;
                }
                if ((i == 0 || i == 8) && (j == 0 || j == 8)) {
                    type = "Roll";
                    //TODO: make enums for types and colors
                } else if ((i == 0 && j == 4) ||
                        (i == 4 && j == 0) ||
                        (i == 8 && j == 4) ||
                        (i == 4 && j == 8)) {
                    type = "HQ";
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
                    type = "TC";
                    color = "W";
                } else if (
                        ((0 < i && i < 4) || (4 < i && i < 8))
                    && ((0 < j && j < 4) || (4 < j && j < 8))
                ) {
                    type = "Dead";
                    color = "W";
                } else {
                    type = "Q";
                }
                squares[i][j] = new Square(new Point(i, j), color, type);
            }
        }
    }
    public Square getSquare(int i, int j) {
        return squares[i][j];
    }
}
