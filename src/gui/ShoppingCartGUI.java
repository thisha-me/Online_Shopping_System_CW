package gui;

import cli.CartItem;
import cli.Product;
import cli.ShoppingCart;
import cli.User;
import gui.def.CenterCellRender;
import gui.def.NoEditableTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingCartGUI extends JFrame {
    private ShoppingCart shoppingCart;
    private JTable cartTable;
    private JLabel total;
    private JLabel summary;
    private DefaultTableModel tableModel;
    private User user;


    public ShoppingCartGUI(ShoppingCart shoppingCart, User user){
        this.shoppingCart=shoppingCart;
        this.user=user;
        initializeFrame();
        createTable();
    }
    private void initializeFrame() {
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1200, 1024);
        setLayout(new BorderLayout());
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
    }

    private void createTable(){
        tableModel = new NoEditableTableModel(
                new Object[]{"Product ID", "Name", "Price"}, 0);
        cartTable = new JTable(tableModel);
        cartTable.setRowHeight(75);
        cartTable.setDefaultRenderer(Object.class, new CenterCellRender());

        JScrollPane tablePane=new JScrollPane(cartTable);
        this.add(tablePane);
    }

    public void updateCartTable() {
        tableModel.setRowCount(0); // Clear the table
        for (CartItem item : shoppingCart.getItems()) {
            tableModel.addRow(new Object[]{
                    item.getDetails(),
                    item.getQuantity(),
                    item.getTotalPrice()});
        }
    }

}
