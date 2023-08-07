package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JDie extends JButton {
    private static final int SIDE = 8;
    private static final Random r = new Random();
    private int value = 6;

    public JDie() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SIDE * 7, SIDE * 7);
    }

    public int rollDice() {
        value = r.nextInt(6) + 1;
        return value;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (value) {
            case 1:
                g.fillRect(3 * SIDE, 3 * SIDE, SIDE, SIDE);
                break;
            case 2:
                g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
                g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
                break;
            case 3:
                g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
                g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
                g.fillRect(3 * SIDE, 3 * SIDE, SIDE, SIDE);
                break;
            case 4:
                g.fillRect(SIDE, SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, 5 * SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
                g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
                break;
            case 5:
                g.fillRect(SIDE, SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, 5 * SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
                g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
                g.fillRect(3 * SIDE, 3 * SIDE, SIDE, SIDE);
                break;
            case 6:
                g.fillRect(SIDE, SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, 5 * SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, SIDE, SIDE, SIDE);
                g.fillRect(SIDE, 5 * SIDE, SIDE, SIDE);
                g.fillRect(SIDE, 3 * SIDE, SIDE, SIDE);
                g.fillRect(5 * SIDE, 3 * SIDE, SIDE, SIDE);
                break;
        }
    }

    public void repaintDice(){
        revalidate();
        repaint();
    }
}