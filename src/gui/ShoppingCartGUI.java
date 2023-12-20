package gui;

import cli.Product;
import cli.ShoppingCart;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ShoppingCartGUI extends JFrame {
    ShoppingCart shoppingCart;
    JTable cartTable;
    JLabel total;
    JLabel summary;


    public ShoppingCartGUI(ShoppingCart shoppingCart){
        this.shoppingCart=shoppingCart;
        initializeFrame();
    }
    private void initializeFrame() {
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1200, 1024);
        setLayout(new BorderLayout());
    }

    private void createTable(){
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Product ID", "Name", "Price", "Availability"}, 0);
        cartTable = new JTable(tableModel);

        for(Product product:shoppingCart.getProducts()){
            tableModel.addRow(new Object[]{product.getProductID()+product,product.getPrice()});
        }
    }


}
