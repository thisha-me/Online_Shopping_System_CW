import java.util.ArrayList;

public interface ShoppingManager {
    void addProduct();

    void deleteProduct();

    void printProducts();

    void saveProductsToFile();
    void loadProductsFromFile();

    ArrayList<Product> getProducts();
}
