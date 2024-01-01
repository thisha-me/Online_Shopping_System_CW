package test;

import cli.WestminsterShoppingManager;
import main.Electronics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.InputValidator;

import java.util.Scanner;

public class WestminsterShoppingManagerTest {
    private WestminsterShoppingManager shoppingManager;

    @BeforeEach
    public void setUp(){
        shoppingManager = new WestminsterShoppingManager();
    }

    @Test
    public void testAddProduct() {
        Scanner scanner = Mockito.mock(Scanner.class);
        InputValidator inputValidator = Mockito.mock(InputValidator.class);
        Mockito.when(scanner.next()).thenReturn("123", "TestProduct", "1", "Brand", "3");

        Mockito.when(inputValidator.get_int(Mockito.anyString())).thenReturn(10);
        Mockito.when(inputValidator.get_double(Mockito.anyString())).thenReturn(50.0);


    }

    @Test
    public void testDeleteProduct() {


    }

    private void addSampleElectronicsProduct() {
        Electronics sampleElectronics = new Electronics("E001", "Sample Electronics", 10, 99.99, "Sample Brand", 12);
        shoppingManager.getProducts().add(sampleElectronics);
    }

}
