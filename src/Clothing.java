public class Clothing extends Product{
    private String size;
    private String color;

    public Clothing() {
    }

    public Clothing(String productID, String productName, int numOfAvailable, double price, String size, String color) {
        super(productID, productName, numOfAvailable, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
