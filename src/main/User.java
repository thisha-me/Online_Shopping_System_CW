package main;

public class User {
    private final String userName;
    private String password;
    private boolean firstPurchaseCompleted;

    public User(String userName, boolean firstPurchaseCompleted) {
        this.userName = userName;
        this.firstPurchaseCompleted = firstPurchaseCompleted;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isFirstPurchaseCompleted() {
        return firstPurchaseCompleted;
    }

    public void setFirstPurchaseCompleted(boolean firstPurchaseCompleted) {
        this.firstPurchaseCompleted = firstPurchaseCompleted;
    }

    public String getPassword() {
        return password;
    }
}
