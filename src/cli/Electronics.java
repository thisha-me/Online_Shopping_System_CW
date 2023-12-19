package cli;

public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod; //months

    public Electronics() {
    }

    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Type: Electronics" +
                "\nID: " + getProductID() +
                "\nName: " + getProductName() +
                "\nBrand: " + getBrand()+
                "\nWarranty: "+getWarrantyPeriod()+" months"+
                "\nPrice: "+getPrice()+
                "\nAvailable : "+getAvailableItems();
    }

    @Override
    public String toCSV() {
        return "Electronics," + getProductID() + "," + getProductName() + "," +
                getAvailableItems() + "," + getPrice() + "," + getBrand() + "," + getWarrantyPeriod()+"\n";
    }

    public static Product fromCSV(String csv) {
        String[] data = csv.split(",");
        return new Electronics(data[1], data[2], Integer.parseInt(data[3]), Double.parseDouble(data[4]), data[5], Integer.parseInt(data[6]));
    }
}
