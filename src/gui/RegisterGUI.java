package gui;

import main.User;
import utils.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterGUI extends JFrame {
    private JButton registerButton;
    private JButton goBackButton;

    private JTextField usernameField;
    private JPasswordField passwordField,confirmPasswordField;

    LoginGUI loginGUI;

    public RegisterGUI(LoginGUI loginGUI) {
        this.loginGUI=loginGUI;
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(420, 240);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField();

        registerButton = new JButton("Register");
        goBackButton = new JButton("Go back");


        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(goBackButton);
        panel.add(registerButton);

        add(panel, BorderLayout.CENTER);

        actions();
    }

    private void actions(){
        registerButton.addActionListener(e->{
            registration();
                this.setVisible(false);
                loginGUI.setVisible(true);
        });

        goBackButton.addActionListener(e->{
            this.setVisible(false);
            loginGUI.setVisible(true);
        });
    }
    private void registration(){
        String username = usernameField.getText().strip();
        char[] passwordChars = passwordField.getPassword();
        char[] confirmPasswordChars = confirmPasswordField.getPassword();

        String password = new String(passwordChars);
        String confirmPassword = new String(confirmPasswordChars);

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "All fields are required. Please fill them.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(new JFrame(),
                    "The password and confirm password you entered do not match. Please try again",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        User user=new User(username,password);

        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO users(username, password) VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setEscapeProcessing(true); //avoid sql injection

            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());


            int rowsAffected = statement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Registration successfully");
                JOptionPane.showMessageDialog(new JFrame(),
                        "Registration Successfully please Log again",
                        "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "Registration fail!","",JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            if (ex.getMessage().contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(new JFrame(), "Username already exists. Please choose a different username.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Registration failed! Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println(ex.getMessage());
        }
    }

}
