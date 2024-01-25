package main;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public abstract class Product implements Comparable<Product>, Serializable{
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    public Product() {
    }

    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

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

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productID, product.productID);
    }

    @Override
    public int compareTo(Product o) {
        return productID.compareTo(o.getProductID());
    }

    public void updateProductAvailability(int purchasedQuantity) {
        if (availableItems >= purchasedQuantity) {
            availableItems -= purchasedQuantity;

        }
    }

    public static Comparator<Product> compareByName = Comparator.comparing(Product::getProductName);
    public static Comparator<Product> compareByPrice = Comparator.comparingDouble(Product::getPrice);
}
