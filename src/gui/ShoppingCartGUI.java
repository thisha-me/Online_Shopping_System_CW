package gui;

import cli.CartItem;
import cli.Product;
import cli.ShoppingCart;
import cli.User;
import gui.def.CenterCellRender;
import gui.def.NoEditableTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class ShoppingCartGUI extends JFrame {
    private ShoppingCart shoppingCart;
    private JTable cartTable;
    private DefaultTableModel tableModel;
    private User user;

    private JLabel totalLabel,firstPurchaseLabel,threeItemSameCategoryLabel,finalTotalLabel;

    private static final DecimalFormat df = new DecimalFormat("0.00");


    public ShoppingCartGUI(ShoppingCart shoppingCart, User user){
        this.shoppingCart=shoppingCart;
        this.user = user != null ? user : new User("", false);
        initializeFrame();
        createTable();
        createDiscount();
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
        totalLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f).deriveFont(Font.BOLD));
        firstPurchaseLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f).deriveFont(Font.BOLD));
        threeItemSameCategoryLabel.setFont(labelFont.deriveFont(labelFont.getSize() + 5f).deriveFont(Font.BOLD));
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

        this.add(pricePanel,BorderLayout.AFTER_LAST_LINE);

    }

    public void updateSummary(){
        totalLabel.setText("Total \t\t\t\t"+shoppingCart.calculateTotalCost());
        threeItemSameCategoryLabel.setText("Three items in Same Category Discount (20%) \t\t\t\t"+threeItemSameCategoryDiscount());
        finalTotalLabel.setText("Final Total \t\t"+finalTotal());

        if(!user.isFirstPurchaseCompleted()){
            firstPurchaseLabel.setText("First Purchase Discount (10%) \t\t\t\t"+firstPurchaseDiscount());
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
        return shoppingCart.calculateTotalCost()+Double.parseDouble(firstPurchaseDiscount())+Double.parseDouble(threeItemSameCategoryDiscount());
    }

    public void setUser(User user) {
        this.user = user;
    }
}
