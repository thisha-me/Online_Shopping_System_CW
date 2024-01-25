package gui;

import main.ShoppingCart;
import main.User;
import utils.DBConnection;
import utils.LoggerUtil;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;

public class LoginGUI extends JFrame {
    private JButton registerButton;
    private JButton loginButton;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private ShoppingCenterGUI shoppingCenterGUI;
    private RegisterGUI registerGUI;

    private User user;
    ShoppingCart shoppingCart;

    public LoginGUI() {
        setTitle("User Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        registerButton = new JButton("Create new account");
        loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        registerGUI=new RegisterGUI(this);
        shoppingCenterGUI=new ShoppingCenterGUI(user);
        actions();

    }

    private void actions(){
        loginButton.addActionListener(e->{
            if(logging()){
                this.setVisible(false);
                shoppingCenterGUI.setVisible(true);
                shoppingCenterGUI.setUser(user);
                shoppingCenterGUI.setShoppingCart(shoppingCart);
            }
        });

        registerButton.addActionListener(e -> {
            this.setVisible(false);
            registerGUI.setVisible(true);
        });
    }

    private boolean logging() {
        String username = usernameField.getText().strip();
        char[] passwordChars = passwordField.getPassword();

        String password = new String(passwordChars).strip();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "All fields are required. Please fill them.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? and password=? LIMIT 1";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setEscapeProcessing(true); //avoid sql injection

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String resUsername=resultSet.getString("username");
                boolean resFirstPurchaseCompleted=resultSet.getInt("firstPurchaseCompleted")==1;
                user=new User(resUsername,resFirstPurchaseCompleted);
                byte[] serializedObject = resultSet.getBytes("shoppingCart");

                if (serializedObject != null) {
                    // Deserialize the object
                    ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    shoppingCart = (ShoppingCart) ois.readObject();
                    LoggerUtil.logInfo("Cart Object Load successfully");

                } else {
                    shoppingCart=new ShoppingCart();
                    LoggerUtil.logInfo("Create Cart Object");
                }
                LoggerUtil.logInfo("user login successful");
                return true;
            } else {
                passwordField.setText("");
                JOptionPane.showMessageDialog(new JFrame(), "Invalid username or password!","error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            LoggerUtil.logError("Login Error: ",ex);
        }
        return false;
    }
}
