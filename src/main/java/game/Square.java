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
}

