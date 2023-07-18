package game;

import java.awt.*;

public class Square {
    private Point boardPosition;
    private String color;
    private String type;

    public Square(Point boardPosition, String color, String type) {
        this.boardPosition = boardPosition;
        this.color = color;
        this.type = type;
    }

    public Point getBoardPosition() {
        return boardPosition;
    }

    /** the getType() method returns the type of square as a string.
     * The four types of squares are as follows:
     * (1) 'Q', Normal question - the color of the square is assigned by the gameboard.
     * (2) 'HQ', HQ question - these question squares, if answered correctly, contribute to the players score.
     * (3) 'Roll', Roll Again - these squares automatically roll the dice for the user again.
     * (4) 'TC', Trivial Compute - this square is in the center and is part of the end game.
     * @return
     */
    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

