package gui;

import cli.*;
import gui.def.ColorChangeCellRender;
import gui.def.NoEditableTableModel;
import main.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCenterGUI extends JFrame {
    private JLabel categoryLabel;
    private JComboBox<String> category;
    private JButton shoppingCartBtn;
    private JTable productTable;
    private DefaultTableModel productTableModel;
    private JButton sortByProductIdBtn;
    private JButton sortByNameBtn;
    private JButton sortByPriceBtn;
    private JLabel productTitle;
    private JLabel productDetail;
    private JButton addToCartBtn;
    private ArrayList<Product> products;
    private ArrayList<Product> updatedProductsByCategory;
    private ShoppingCart shoppingCart;
    private ShoppingCartGUI shoppingCartGUI;
    private User user;
    private JLabel welcomeMsg;

    public ShoppingCenterGUI(User user) {
        this.user=user;
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        products = shoppingManager.getProducts();
        updatedProductsByCategory = (ArrayList<Product>) products.clone();
        shoppingCart = new ShoppingCart();
        shoppingCartGUI = new ShoppingCartGUI(shoppingCart, user,this);
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
        setLocationRelativeTo(null);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
    }

    private void createUpperPanel() {
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setPreferredSize(new Dimension(100, 150));

        // Center panel for other components
        JPanel centerPanel = new JPanel(new FlowLayout());

        welcomeMsg = new JLabel();
        JPanel topPanel = new JPanel();
        topPanel.add(welcomeMsg);

        // Category label
        categoryLabel = new JLabel("Select Product Category ");
        categoryLabel.setLabelFor(category);

        // Category JList
        String[] categories = {"All", "Electronics", "Clothing"};
        category = new JComboBox<>(categories);
        category.setSelectedIndex(0);

        JPanel sortPanel = new JPanel(new FlowLayout());
        sortByNameBtn = new JButton("Sort By Name");
        sortByProductIdBtn = new JButton("Sort By ID");
        sortByPriceBtn = new JButton("Sort By Price");
        sortPanel.add(sortByProductIdBtn);
        sortPanel.add(sortByNameBtn);
        sortPanel.add(sortByPriceBtn);

        // Add components to the center panel
        centerPanel.add(categoryLabel);
        centerPanel.add(category);

        // Cart button aligned near the right page margin
        shoppingCartBtn = new JButton("Shopping Cart");
        JPanel cartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartPanel.add(shoppingCartBtn);

        // Add components to the upper panel
        upperPanel.add(topPanel, BorderLayout.PAGE_START);
        upperPanel.add(centerPanel, BorderLayout.CENTER);
        upperPanel.add(cartPanel, BorderLayout.LINE_END);
        upperPanel.add(sortPanel, BorderLayout.PAGE_END);

        this.add(upperPanel, BorderLayout.PAGE_START);
    }


    private void createProductTable() {
        productTable = new JTable();
        productTable.setDefaultRenderer(Object.class, new ColorChangeCellRender(updatedProductsByCategory));// change cell color

        productTableModel = new NoEditableTableModel();
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

        addToCartBtn = new JButton("Add to Cart");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToCartBtn);

        lowerPanel.add(productTitle);
        lowerPanel.add(productDetail);
        lowerPanel.add(buttonPanel);

        this.add(lowerPanel, BorderLayout.PAGE_END);
    }

    String selectedOption = "All";
    Product selectedProduct = null;

    private void addListeners() {

        category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> category = (JComboBox<String>) e.getSource();
                selectedOption = (String) category.getSelectedItem();
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
                            selectedProduct = product;
                            productDetail.setText("<html>" + product.toString().replace("\n", "<br/>") + "</html>");
                        }
                    }
                }
            }
        });

        sortByProductIdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(products);
                selectCategoryModel(selectedOption);
            }
        });

        sortByNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                products.sort(Product.compareByName);
                selectCategoryModel(selectedOption);
            }
        });

        sortByPriceBtn.addActionListener(e -> {
            products.sort(Product.compareByPrice);
            selectCategoryModel(selectedOption);
        });

        addToCartBtn.addActionListener(e -> {
            if (selectedProduct == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Before add to cart you should select the product", "Dialog",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int dialogButton = JOptionPane.YES_NO_OPTION;
            dialogButton = JOptionPane.showConfirmDialog(null,
                    "<html>Do you want to add this product to cart?<br>" +
                            selectedProduct.toString().replace("\n", "<br>") +
                            "<html>",
                    " ", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                try {
                    shoppingCart.addProduct(selectedProduct);
                } catch (RuntimeException error) {
                    JOptionPane.showMessageDialog(new JFrame(), error.getMessage(), "", JOptionPane.WARNING_MESSAGE);
                }

                shoppingCartGUI.updateCartTable();
                shoppingCartGUI.updateSummary();
                System.out.println(selectedProduct.getProductID() + " product added");
                shoppingCart.updateCartToDB(user);
            }
        });

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                productDetail.setText("<html>" + "<br/>".repeat(6) + "</html>"); // Clear the product details
                selectedProduct = null;
            }
        });

        shoppingCartBtn.addActionListener(e -> shoppingCartGUI.setVisible(!shoppingCartGUI.isVisible()));
    }

    private void selectCategoryModel(String type) {
        ArrayList<Object[]> filteredProducts = new ArrayList<>();
        updatedProductsByCategory.clear();

        for (Product product : products) {
            if (isProductOfType(type, product)) {
                Object[] data = createProductData(product);
                filteredProducts.add(data);
                updatedProductsByCategory.add(product);
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
                info,
        };
    }

    private void updateTableModel(ArrayList<Object[]> filteredProducts) {
        Object[][] newData = filteredProducts.toArray(new Object[0][0]);
        String[] columns = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        productTableModel.setDataVector(newData, columns);
    }

    public void setUser(User user) {
        this.user = user;
        shoppingCartGUI.setUser(user);
        welcomeMsg.setText("Welcome " + user.getUserName());
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void updateProductTable() {
        selectCategoryModel(selectedOption);
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        shoppingCartGUI.setShoppingCart(shoppingCart);
        shoppingCartGUI.updateCartTable();
        shoppingCartGUI.updateSummary();

    }
}