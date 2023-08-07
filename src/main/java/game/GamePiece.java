package game;

import javax.swing.*;
import java.awt.*;

public class GamePiece extends JPanel {
    private Color color;

    public GamePiece(Color color) {
        this.color = color;
        setOpaque(false); // So the panel doesn't draw its background, allowing transparency.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int diameter = Math.min(getWidth(), getHeight());
        g.setColor(color);
        g.fillOval((getWidth() - diameter) / 2, (getHeight() - diameter) / 2, diameter, diameter);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50); // Preferred size of the piece.
    }
}
