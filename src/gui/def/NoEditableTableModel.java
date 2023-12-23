package gui.def;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class NoEditableTableModel extends DefaultTableModel {

    public NoEditableTableModel() {
    }

    public NoEditableTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
