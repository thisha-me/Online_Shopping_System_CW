package gui;

import cli.*;
import gui.def.CenterCellRender;
import gui.def.NoEditableTableModel;
import utils.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShoppingCartGUI extends JFrame {
    private ShoppingCart shoppingCart;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private User user;

    private JLabel totalLabel,firstPurchaseLabel,threeItemSameCategoryLabel,finalTotalLabel;
    private JButton payBtn;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    ShoppingCenterGUI shoppingCenterGUI;
    ArrayList<Product> products;

    public ShoppingCartGUI(ShoppingCart shoppingCart, User user, ShoppingCenterGUI shoppingCenterGUI){
        this.shoppingCart=shoppingCart;
        this.user = user != null ? user : new User("", false);
        this.shoppingCenterGUI=shoppingCenterGUI;
        this.products=shoppingCenterGUI.getProducts();

        initializeFrame();
        createTable();
        createDiscount();
        actions();
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
                new Object[]{"Product ID", "Quantity", "Price"}, 0);
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

    public void createDiscount(){
        totalLabel=new JLabel();
        firstPurchaseLabel=new JLabel();
        threeItemSameCategoryLabel=new JLabel();
        finalTotalLabel=new JLabel();

        Font labelFont = totalLabel.getFont();
        totalLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f));
        firstPurchaseLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f));
        threeItemSameCategoryLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f));
        finalTotalLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f).deriveFont(Font.BOLD));

        JPanel pricePanel=new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel,BoxLayout.Y_AXIS));
        pricePanel.setBorder(new EmptyBorder(0,20,100,100));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(totalLabel);
        pricePanel.add(totalPanel);

        JPanel firstPurchasePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        firstPurchasePanel.add(firstPurchaseLabel);
        pricePanel.add(firstPurchasePanel);

        JPanel threeItemPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        threeItemPanel.add(threeItemSameCategoryLabel);
        pricePanel.add(threeItemPanel);

        JPanel finalTotalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        finalTotalPanel.add(finalTotalLabel);
        pricePanel.add(finalTotalPanel);

        payBtn=new JButton("Pay");
        payBtn.setPreferredSize(new Dimension(90,60));
        payBtn.setFont(labelFont.deriveFont(labelFont.getSize() + 3f));
        JPanel payBtnPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        payBtnPanel.add(payBtn);
        pricePanel.add(payBtnPanel);


        this.add(pricePanel,BorderLayout.AFTER_LAST_LINE);

    }

    public void updateSummary(){
        totalLabel.setText("Total \t\t\t\t"+shoppingCart.calculateTotalCost());
        threeItemSameCategoryLabel.setText("Three items in Same Category Discount (20%) \t\t\t\t"+threeItemSameCategoryDiscount());
        finalTotalLabel.setText("Final Total \t\t"+finalTotal());

        if(!user.isFirstPurchaseCompleted()){
            firstPurchaseLabel.setText("First Purchase Discount (10%) \t\t\t\t"+firstPurchaseDiscount());
        }else {
            firstPurchaseLabel.setText("");
        }
    }


    private String firstPurchaseDiscount(){
        if(!user.isFirstPurchaseCompleted()){
            return df.format(shoppingCart.calculateTotalCost()/10*(-1));
        }
        return "0";
    }

    private String threeItemSameCategoryDiscount(){
        if(shoppingCart.categoryCount()[0]>=3 || shoppingCart.categoryCount()[1]>=3){
            return df.format(shoppingCart.calculateTotalCost()/5*(-1));
        }
        return "0";
    }

    private double finalTotal(){
        return Double.parseDouble(df.format(shoppingCart.calculateTotalCost()))+Double.parseDouble(firstPurchaseDiscount())+Double.parseDouble(threeItemSameCategoryDiscount());
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void actions(){
        cartTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();
            System.out.println("Select");
            if(selectedRow!=-1){
                CartItem removeItem=shoppingCart.getItems().get(selectedRow);
                int dialogButton = JOptionPane.YES_NO_OPTION;
                dialogButton = JOptionPane.showConfirmDialog(null,
                                "<html>Do you want to remove this item?<br></html>"+removeItem.getDetails(),
                        " ", dialogButton);

                if (dialogButton == JOptionPane.YES_OPTION) {
                    shoppingCart.removeProduct(removeItem.getProduct());
                    updateCartTable();
                    updateSummary();
                    shoppingCart.updateCartToDB(user);
                }
            }
        });

        payBtn.addActionListener(e->{
            int dialogButton = JOptionPane.YES_NO_OPTION;
            dialogButton = JOptionPane.showConfirmDialog(null,
                    "<html>Are you sure you want to proceed with the payment?<br>Amount :"+finalTotal()+"£</html>",
                    "Payment Confirmation",
                    dialogButton);

            if(dialogButton==JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null,
                        "<html>Paid: "+finalTotal()+"<br>Payment successful!</html>",
                        "Payment Status", JOptionPane.INFORMATION_MESSAGE);
                for (CartItem cartItem : shoppingCart.getItems()) {
                    Product product = cartItem.getProduct();
                    int purchasedQuantity = cartItem.getQuantity();
                    product.updateProductAvailability(purchasedQuantity);
                }

                updateProductAvailabilityDB();
                updateFirstPurchase();
                shoppingCart.clearCart();
                updateCartTable();
                updateSummary();
                shoppingCenterGUI.updateProductTable();
                shoppingCart.updateCartToDB(user);
            }
        });
    }

    public void updateProductAvailabilityDB(){
        String sql="";

        try (Connection connection = DBConnection.getConnection()) {
            for(CartItem cartItem:shoppingCart.getItems()){
                if(cartItem.getProduct() instanceof Electronics){
                    sql="UPDATE Electronics SET availableItems = ? WHERE productID = ?";
                }else if(cartItem.getProduct() instanceof Clothing){
                    sql="UPDATE Clothing SET availableItems = ? WHERE productID = ?";
                }

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, cartItem.getProduct().getAvailableItems());
                statement.setString(2, cartItem.getProduct().getProductID());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Product availability updated to db successfully.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateFirstPurchase(){
        if(!user.isFirstPurchaseCompleted()){
            user.setFirstPurchaseCompleted(true);
            try (Connection connection=DBConnection.getConnection()){
                String sql="UPDATE users SET firstPurchaseCompleted = ? WHERE username = ?";

                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, 1);
                statement.setString(2, user.getUserName());

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User First purchase done updated to db successfully.");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
