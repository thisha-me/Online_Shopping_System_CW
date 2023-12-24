package gui;

import javax.swing.*;

public class GUIController{
    private LoginGUI loginGUI;
    private JButton gotoRegisterBtn;
    private JButton loginBtn;
    private boolean isLogin;

    private RegisterGUI registerGUI;
    private JButton registerBtn;
    private JButton goBackBtn;

    private ShoppingCenterGUI shoppingCenterGUI;

    public GUIController() {
        initializeFrames();
        loginGUI.setVisible(true);
        actions();
    }

    private void initializeFrames() {
        loginGUI = new LoginGUI();
        gotoRegisterBtn = loginGUI.getRegisterButton();
        loginBtn = loginGUI.getLoginButton();

        registerGUI = new RegisterGUI();
        registerBtn = registerGUI.getRegisterButton();
        goBackBtn = registerGUI.getGoBackButton();

        shoppingCenterGUI = new ShoppingCenterGUI();
    }


    private void actions() {
        gotoRegisterBtn.addActionListener(e -> {
            loginGUI.setVisible(false);
            loginGUI.dispose();

            registerGUI.setVisible(true);
        });

        goBackBtn.addActionListener(e -> {
            registerGUI.setVisible(false);
            registerGUI.dispose();

            loginGUI.setVisible(true);
        });

        loginBtn.addActionListener(e -> {
            loginGUI.logging();
            isLogin=loginGUI.isLoggedIn();
            if(isLogin){
                loginGUI.setVisible(false);
                shoppingCenterGUI.setVisible(true);
            }
        });


    }

    public static void main(String[] args) {
        new GUIController();
    }
}
