package pages;

import javax.swing.*;
import java.awt.*;

public class PlayerPiece extends JPanel {
    public PlayerPiece(Color pieceColor) {
        setOpaque(false);
        setBackground(pieceColor);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(70, 70);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int dim = Math.max(getWidth() - 4, getHeight() - 4);
        int x = (getWidth() - dim) / 4;
        int y = (getHeight() - dim) / 4;
        g2d.setColor(getBackground());
        g2d.fillOval(x, y, dim, dim);
        g2d.dispose();
    }

}
