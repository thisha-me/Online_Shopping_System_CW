package test;

import main.Clothing;
import main.Electronics;
import main.Product;
import main.WestminsterShoppingManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utils.InputValidator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class WestminsterShoppingManagerTest {
    @Mock
    private Scanner mockScanner;

    @InjectMocks
    private WestminsterShoppingManager shoppingManager;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
//        shoppingManager.setScanner(mockScanner);

        // Redirect System.out to capture output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testAddProduct_Electronics() {
        when(mockScanner.next())
                .thenReturn("P001", "iPhone 13");

        when(InputValidator.getPositiveInt("")).thenReturn(10);
        when(InputValidator.getPositiveDouble("")).thenReturn(1000.0);
        when(mockScanner.next()).thenReturn("1","Apple");
        when(InputValidator.getPositiveInt("")).thenReturn(12);

        shoppingManager.addProduct();

        // Verify the output
        assertTrue(outputStream.toString().contains("Add Product"));
        assertTrue(outputStream.toString().contains("Electronics added successfully"));

        // Verify that the product is added
        ArrayList<Product> products = shoppingManager.getProducts();
        assertEquals(1, products.size());
        assertTrue(products.get(0) instanceof Electronics);
        assertEquals("P001", products.get(0).getProductID());
        assertEquals("iPhone 13", products.get(0).getProductName());
        assertEquals(10, products.get(0).getAvailableItems());
        assertEquals(1000.0, products.get(0).getPrice());
        assertEquals("Apple", ((Electronics) products.get(0)).getBrand());
        assertEquals(12, ((Electronics) products.get(0)).getWarrantyPeriod());
    }

    @Test
    void testAddProduct_Clothing() {
        when(mockScanner.next())
                .thenReturn("P002", "T-Shirt");

        when(InputValidator.getPositiveInt("")).thenReturn(20);
        when(InputValidator.getPositiveDouble("")).thenReturn(20.0);
        when(mockScanner.next()).thenReturn("1","M","Blue");

        shoppingManager.addProduct();

        // Verify the output
        assertTrue(outputStream.toString().contains("Add Product"));
        assertTrue(outputStream.toString().contains("Clothing added successfully"));

        // Verify that the product is added
        ArrayList<Product> products = shoppingManager.getProducts();
        assertEquals(1, products.size());
        assertTrue(products.get(0) instanceof Clothing);
        assertEquals("P002", products.get(0).getProductID());
        assertEquals("T-Shirt", products.get(0).getProductName());
        assertEquals(20, products.get(0).getAvailableItems());
        assertEquals(20.0, products.get(0).getPrice());
        assertEquals("M", ((Clothing) products.get(0)).getSize());
        assertEquals("Blue", ((Clothing) products.get(0)).getColor());
    }

    @Test
    void testAddProduct_ExistingProductID() {
        // Simulate a scenario where the product ID already exists
        when(mockScanner.next()).thenReturn("P001");

        shoppingManager.getProducts().add(new Electronics("P001", "Existing Product", 5, 200.0, "Brand", 8));

        shoppingManager.addProduct();

        // Verify the output
        assertTrue(outputStream.toString().contains("Already Existing product id"));

        // Verify that no new product is added
        ArrayList<Product> products = shoppingManager.getProducts();
        assertEquals(1, products.size()); // Check the total number of products is still 1
    }

    @Test
    void testDeleteProduct_Successful() {
        // Mock user input
        ByteArrayInputStream in = new ByteArrayInputStream("P001\ny\n".getBytes());
        System.setIn(in);
        when(mockScanner.next()).thenReturn("P001", "y");

        shoppingManager.deleteProduct();

        // Reset System.in to its original state
        System.setIn(System.in);

        // Verify the output
        assertTrue(outputStream.toString().contains("Delete Product"));
        assertTrue(outputStream.toString().contains("Product Delete "));
        assertTrue(outputStream.toString().contains("Deleted Product"));
        assertTrue(outputStream.toString().contains("Product delete successfully"));
        assertTrue(outputStream.toString().contains("10 left the system"));

        // Verify that the product is removed from the products list
        assertTrue(shoppingManager.getProducts().isEmpty());

    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        // Mock user input
        when(mockScanner.next()).thenReturn("P003");

        shoppingManager.deleteProduct();

        // Reset System.in to its original state
        System.setIn(System.in);

        // Verify the output
        assertTrue(outputStream.toString().contains("Delete Product"));
        assertTrue(outputStream.toString().contains("Product with ID P003 not found."));
        assertFalse(outputStream.toString().contains("Product delete successfully"));

        // Verify that the products list and deletedProducts list are not modified
        assertEquals(2, shoppingManager.getProducts().size());
    }

}