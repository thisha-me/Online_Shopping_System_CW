package main;

import utils.DBConnection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShoppingCart implements Serializable {
    private ArrayList<CartItem> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        int availableQuantity = product.getAvailableItems();

        CartItem existingItem = findCartItemByProduct(product);

        if (existingItem != null) {
            int currentQuantity = existingItem.getQuantity();
            if (currentQuantity < availableQuantity) {
                existingItem.incrementQuantity();
            } else {
                throw new RuntimeException("The product is out of stock or the limit has been reached.");
            }
            return;
        }
        if (availableQuantity > 0) {
            items.add(new CartItem(product));
            return;
        }
        throw new RuntimeException("The product is out of stock.");


    }

    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public double calculateTotalCost() {
        double totalCost = 0;
        for (CartItem item : items) {
            totalCost += item.getTotalPrice();
        }
        return totalCost;
    }

    private CartItem findCartItemByProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public int[] categoryCount() {
        int[] count = new int[2];
        for (CartItem item : getItems()) {
            if (item.getProduct() instanceof Electronics) {
                count[0] += item.getQuantity();
            } else if (item.getProduct() instanceof Clothing) {
                count[1] += item.getQuantity();
            }
        }
        return count;
    }

    public void clearCart() {
        items.clear();
    }

    public void updateCartToDB(User user){
        try(Connection connection= DBConnection.getConnection()){
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);

            oos.writeObject(this);
            byte[] serializedCartObject = bos.toByteArray();

            String sql = "UPDATE users SET shoppingCart=? WHERE username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBytes(1, serializedCartObject);
            statement.setString(2,user.getUserName());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("ShoppingCart Object serialized and saved to the database!");
            }

        }catch (SQLException | ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
}