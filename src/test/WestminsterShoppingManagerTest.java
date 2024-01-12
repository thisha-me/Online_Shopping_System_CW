package test;

import main.Clothing;
import main.Electronics;
import main.Product;
import main.WestminsterShoppingManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager shoppingManager;
    private Product product1, product2, product3;

    @BeforeEach
    void setUp() {
        shoppingManager = new WestminsterShoppingManager();

        product1 = new Electronics("P001", "iPhone 13", 10, 1000.0, "Apple", 12);
        product2 = new Clothing("P002", "T-Shirt", 20, 20.0, "M", "Blue");
        product3 = new Electronics("P003", "Samsung Galaxy S22", 15, 900.0, "Samsung", 18);
    }

    @Test
    void testAddProduct() {
        shoppingManager.addProduct();

        ArrayList<Product> products = shoppingManager.getProducts();
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
        assertTrue(products.contains(product3));
    }

    @Test
    void testDeleteProduct() {
        shoppingManager.getProducts().add(product1);
        shoppingManager.getProducts().add(product2);

        shoppingManager.deleteProduct();

        ArrayList<Product> products = shoppingManager.getProducts();
        assertFalse(products.contains(product1));
        assertTrue(products.contains(product2));
    }

//    @Test
//    void testPrintProducts() {
//        shoppingManager.getProducts().add(product1);
//        shoppingManager.getProducts().add(product2);
//
//        shoppingManager.printProducts();
//
//        String expectedOutput =
//                "Product ID: P001\n" +
//                        "Product Name: iPhone 13\n" +
//                        "Available Items: 10\n" +
//                        "Price: 1000.0\n" +
//                        "Brand: Apple\n" +
//                        "Warranty Period (Months): 12\n\n" +
//
//                        "Product ID: P002\n" +
//                        "Product Name: T-Shirt\n" +
//                        "Available Items: 20\n" +
//                        "Price: 20.0\n" +
//                        "Size: M\n" +
//                        "Color: Blue\n\n";
//
//        assertEquals(expectedOutput, shoppingManager.printProducts());
//    }
}