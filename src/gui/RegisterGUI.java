package gui;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {
    LoginGUI loginGUI;
    public RegisterGUI(LoginGUI loginGUI){
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
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton registerButton = new JButton("Register");
        JButton goBackButton = new JButton("Go back");


        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(registerButton);
        panel.add(goBackButton);

        add(panel, BorderLayout.CENTER);
    }

}
