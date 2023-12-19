package cli;

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
        return "Type: Clothing" +
                "\nID: " + getProductID() +
                "\nName: " + getProductName() +
                "\nSize: " + getSize() +
                "\nColor: " + getColor() +
                "\nPrice: " + getPrice() +
                "\nAvailable: " + getAvailableItems();
    }

    @Override
    public String toCSV() {
        return "Clothing," + getProductID() + "," + getProductName() + "," +
                getAvailableItems() + "," + getPrice() + "," + getSize() + "," + getColor()+"\n";
    }


    public static Clothing fromCSV(String csv) {
        String[] data = csv.split(",");
        return new Clothing(data[1], data[2], Integer.parseInt(data[3]), Double.parseDouble(data[4]), data[5], data[6]);
    }
}
