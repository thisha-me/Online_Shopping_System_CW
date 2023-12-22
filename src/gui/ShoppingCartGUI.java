package gui;

import cli.CartItem;
import cli.Product;
import cli.ShoppingCart;
import gui.def.CenterCellRender;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingCartGUI extends JFrame {
    ShoppingCart shoppingCart;
    JTable cartTable;
    JLabel total;
    JLabel summary;
    DefaultTableModel tableModel;


    public ShoppingCartGUI(ShoppingCart shoppingCart){
        this.shoppingCart=shoppingCart;
        System.out.println(this.shoppingCart);
        initializeFrame();
        createTable();
    }
    private void initializeFrame() {
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1200, 1024);
        setLayout(new BorderLayout());
    }

    private void createTable(){
        tableModel = new DefaultTableModel(
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
                    "<html>"+item.getDetails().replace("\n","<br>")+"</html>",
                    item.getQuantity(),
                    item.getTotalPrice()});
        }
    }

}
