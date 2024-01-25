package main;

import java.util.ArrayList;

public interface ShoppingManager {
    void addProduct();

    void deleteProduct();

    void printProducts();

//    void saveProductsToFile();

    ArrayList<Product> getProducts();
}
