package gui;

import cli.Clothing;
import cli.Electronics;
import cli.Product;
import cli.WestminsterShoppingManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShoppingCenterGUI extends JFrame {
    private JLabel categoryLabel;
    private JComboBox<String> category;
    private JButton shoppingCart;
    private JTable productTable;
    private DefaultTableModel productTableModel;

    private JLabel productTitle;
    private JLabel productDetail;
    private JButton addToCart;
    private ArrayList<Product> products;

    public ShoppingCenterGUI() {
        WestminsterShoppingManager shoppingManager=new WestminsterShoppingManager();
        products=shoppingManager.getProducts();
        initializeFrame();
        createUpperPanel();
        createProductTable();
        createLowerPanel();
        addListeners();
    }
    private void initializeFrame() {
        setTitle("Westminster Shopping Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1024);
        setLayout(new BorderLayout());
    }

    private void createUpperPanel() {
        JPanel upperPanel = new JPanel(new FlowLayout());

        //category label
        categoryLabel = new JLabel("Select Product Category ");
        categoryLabel.setLabelFor(category);

        //category JList
        String[] categories = {"All", "Electronics", "Clothing"};
        category = new JComboBox<>(categories);
        category.setSelectedIndex(0);

        upperPanel.add(categoryLabel);
        upperPanel.add(category);

        this.add(upperPanel, BorderLayout.PAGE_START);
    }

    private void createProductTable() {
        productTable = new JTable();

        productTableModel = new DefaultTableModel();
        productTable.setModel(productTableModel);
        selectCategoryModel("All");

        JScrollPane scrollPane = new JScrollPane(productTable);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void createLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));

        productTitle = new JLabel("Selected Product - Details");
        productDetail = new JLabel("<html>" + "<br/>".repeat(6) + "</html>");

        addToCart = new JButton("Add to Cart");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToCart);

        lowerPanel.add(productTitle);
        lowerPanel.add(productDetail);
        lowerPanel.add(buttonPanel);

        add(lowerPanel, BorderLayout.PAGE_END);
    }

    private void addListeners() {
        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> category = (JComboBox<String>) e.getSource();
                String selectedOption = (String) category.getSelectedItem();
                selectCategoryModel(selectedOption);
            }
        });

        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String productId = (String) productTable.getValueAt(selectedRow, 0);
                    for (Product product : products) {
                        if (product.getProductID().equals(productId)) {// Correct comparison
                            productDetail.setText("<html>" + product.toString().replace("\n","<br/>") + "</html>");
                        }
                    }
                }
            }
        });

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                productDetail.setText("<html>" + "<br/>".repeat(6) + "</html>"); // Clear the product details
            }
        });
    }
    private void selectCategoryModel(String type) {
        ArrayList<Object[]> filteredProducts = new ArrayList<>();

        for (Product product : products) {
            if (isProductOfType(type, product)) {
                Object[] data = createProductData(product);
                filteredProducts.add(data);
            }
        }

        updateTableModel(filteredProducts);
    }

    private boolean isProductOfType(String type, Product product) {
        return type.equals("All") ||
                (type.equals("Electronics") && product instanceof Electronics) ||
                (type.equals("Clothing") && product instanceof Clothing);
    }

    private Object[] createProductData(Product product) {
        String category = "";
        String info = "";

        if (product instanceof Clothing) {
            category = "Clothing";
            info = ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor();
        } else if (product instanceof Electronics) {
            category = "Electronics";
            info = ((Electronics) product).getBrand() + " " + ((Electronics) product).getWarrantyPeriod() + " months warranty";
        }

        return new Object[]{
                product.getProductID(),
                product.getProductName(),
                category,
                product.getPrice(),
                info
        };
    }

    private void updateTableModel(ArrayList<Object[]> filteredProducts) {
        Object[][] newData = filteredProducts.toArray(new Object[0][0]);
        String[] columns = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        productTableModel.setDataVector(newData, columns);
    }

    public static void main(String[] args) {
        ShoppingCenter shoppingCenter=new ShoppingCenter();
        shoppingCenter.setVisible(true);
    }
}
