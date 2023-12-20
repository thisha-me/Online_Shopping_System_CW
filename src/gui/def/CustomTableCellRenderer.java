package gui.def;

import cli.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    ArrayList<Product> products;

    public CustomTableCellRenderer(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Assuming your products are stored in a list called 'products'
        Product product = products.get(row);
        int availability = product.getAvailableItems(); // Use the method to get availability

        Color light_red=new Color(255, 127, 127);
        if (availability < 3) {
            cellComponent.setBackground(light_red);
            cellComponent.setForeground(Color.WHITE);
        } else {
            // Reset background and foreground to default colors
            cellComponent.setBackground(table.getBackground());
            cellComponent.setForeground(table.getForeground());
        }

        return cellComponent;
    }

}
