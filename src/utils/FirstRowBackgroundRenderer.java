package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class FirstRowBackgroundRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row == 0) {
            setForeground(Color.black);
            setBackground(Color.green);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return cell;
    }

}
