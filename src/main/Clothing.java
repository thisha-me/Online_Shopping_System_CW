package main;

public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing() {
    }

    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
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

    @Override
    public String toString() {
        return "Product ID: " + getProductID() +
                "\nCategory: Clothing" +
                "\nName: " + getProductName() +
                "\nSize: " + getSize() +
                "\nColor: " + getColor() +
                "\nPrice: " + getPrice() +
                "\nItem Available: " + getAvailableItems();
    }



    public static Clothing fromCSV(String csv) {
        String[] data = csv.split(",");
        return new Clothing(data[1], data[2], Integer.parseInt(data[3]), Double.parseDouble(data[4]), data[5], data[6]);
    }
}
