package gui.def;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CenterCellRender extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (rendererComponent instanceof JLabel) {
            ((JLabel) rendererComponent).setHorizontalAlignment(SwingConstants.CENTER);
        }
        return rendererComponent;
    }
}
