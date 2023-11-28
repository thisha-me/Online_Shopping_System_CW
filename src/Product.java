public abstract class Product {
    private String productID;
    private String productName;
    private int numOfAvailable;
    private double price;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumOfAvailable() {
        return numOfAvailable;
    }

    public void setNumOfAvailable(int numOfAvailable) {
        this.numOfAvailable = numOfAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
