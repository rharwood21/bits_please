package pages;

import javax.swing.*;
import java.awt.*;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;



public class CustomComboBoxRenderer extends DefaultListCellRenderer {
    Font font = new Font("Roboto", Font.BOLD, 18);

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER); // Set text alignment to center
        return label;
    }
}

