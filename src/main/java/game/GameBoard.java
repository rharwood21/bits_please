package game;

import java.awt.*;

public class GameBoard {
    // a 2D array to create the coordinate system for the board
    private static Square[][] board;
    // creating logical values for valid squares on the gameboard

    public GameBoard() {
        /* Constructor method for GameBoard Class
        *
        *
        * */
        String color = "";
        String type = "";

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch (i % 4) {
                    case 0:
                        color = "R";
                    case 1:
                        color = "Y";
                    case 2:
                        color = "G";
                    case 3:
                        color = "B";
                }
                if ((i == 0 || i == 9) && (j == 0 || j == 9)) {
                    type = "Roll";
                    //TODO: make enums for types and colors
                }
                board[i][j] = new Square(new Point(i, j), color, type);
            }
        }

        // Assigning the squares a value between 1-4 to make the colors later
        // I feel really dumb, but I can't think of a loop to describe the order of colors...
        // ...might have to hardcode them


    }
}
