package cli;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<CartItem> items;

    public ShoppingCart() {
        items=new ArrayList<>();
    }

    public void addProduct(Product product){
        for (CartItem item : items) {
            if (item.getProduct().equals(product)) {
                item.incrementQuantity();
                return;
            }
        }
        // If product not found in cart, add as a new item
        items.add(new CartItem(product));
    }

    public void removeProduct(Product product){
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public double calculateTotalCost() {
        double totalCost = 0;
        for (CartItem item : items) {
            totalCost += item.getTotalPrice();
        }
        return totalCost;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }
}