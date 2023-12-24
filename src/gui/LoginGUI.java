package gui;

import utils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoginGUI extends JFrame {
    private JButton registerButton;
    private JButton loginButton;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private boolean isLoggedIn;

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public LoginGUI() {
        setTitle("Login Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    }

    protected void logging() {
        isLoggedIn=false;
        String username = usernameField.getText().strip();
        char[] passwordChars = passwordField.getPassword();

        String password = new String(passwordChars);

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? and password=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setEscapeProcessing(true); //avoid sql injection

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("successfully login");
                isLoggedIn=true;
            } else {
                passwordField.setText("");
                JOptionPane.showMessageDialog(null, "Login fail!");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }
}
