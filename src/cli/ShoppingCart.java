package cli;

import java.util.ArrayList;

public class ShoppingCart {
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
            }else {
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

    public int[] categoryCount(){
        int[] count=new int[2];
        for(CartItem item: getItems()){
            if(item.getProduct() instanceof Electronics){
                count[0]+=item.getQuantity();
            }
            else if(item.getProduct() instanceof Clothing){
                count[1]+=item.getQuantity();
            }
        }
        return count;
    }

    public void clearCart(){
        items.clear();
    }
}