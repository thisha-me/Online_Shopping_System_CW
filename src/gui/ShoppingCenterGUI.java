package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cli.Clothing;
import cli.Electronics;
import cli.Product;
import cli.WestminsterShoppingManager;

public class ShoppingCenterGUI extends JFrame {
    private JLabel categoryLabel;
    private JComboBox<String> category;
    private JButton shoppingCart;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    
    public ShoppingCenterGUI() {
        //frame
        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1024);
        setLayout(new BorderLayout());

        JPanel upperPanel=new JPanel(new FlowLayout());

        //category label
        categoryLabel=new JLabel("Select Product Category ");
        categoryLabel.setLabelFor(category);

        //category JList
        String[] categories = {"All", "Electronics", "Clothing"};
        category=new JComboBox<>(categories);
        category.setSelectedIndex(0);
        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> category=(JComboBox<String>) e.getSource();
                String selectedOption=(String) category.getSelectedItem();
                selectCategoryModel(selectedOption);
            }
        });

        //table
        productTable=new JTable();
        productTable.setPreferredScrollableViewportSize(new Dimension(450,63));
        productTable.setFillsViewportHeight(true);

        productTableModel=new DefaultTableModel();
        selectCategoryModel("All");

        productTable.setModel(productTableModel);
        productTable.setFillsViewportHeight(true);

        JScrollPane tableContainer=new JScrollPane(productTable);

        upperPanel.add(categoryLabel);
        upperPanel.add(category);
//        upperPanel.add(tableContainer);

        this.add(upperPanel,BorderLayout.PAGE_START);
        this.add(tableContainer);

    }

    private void selectCategoryModel(String type) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        ArrayList<Product> products = shoppingManager.getProducts();

        ArrayList<Object[]> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            if (type.equals("All") ||
                    (type.equals("Electronics") && product instanceof Electronics) ||
                    (type.equals("Clothing") && product instanceof Clothing)) {

                String category = "";
                String info = "";

                if (product instanceof Clothing) {
                    category = "Clothing";
                    info = ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor();
                } else if (product instanceof Electronics) {
                    category = "Electronics";
                    info = ((Electronics) product).getBrand() + " " + ((Electronics) product).getWarrantyPeriod() + " months warranty";
                }

                Object[] data = {
                        product.getProductID(),
                        product.getProductName(),
                        category,
                        product.getPrice(),
                        info
                };

                filteredProducts.add(data);
            }
        }

        Object[][] newData = filteredProducts.toArray(new Object[0][0]);
        String[] columns = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        productTableModel.setDataVector(newData, columns);
    }


    public static void main(String[] args) {
        ShoppingCenterGUI shoppingCenterGUI=new ShoppingCenterGUI();
        shoppingCenterGUI.setVisible(true);
    }

}
