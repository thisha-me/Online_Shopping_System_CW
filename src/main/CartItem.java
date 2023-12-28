package main;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity=1;
    }

    public void incrementQuantity(){
        this.quantity++;
    }

    public void incrementQuantity(int quantity){
        this.quantity+=quantity;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice(){
        return product.getPrice()*quantity;
    }

    public String getDetails(){
        if(product instanceof Electronics){
            return "<html>"+product.getProductID()+ "<br>"+
                    product.getProductName()+"<br>"+
                    ((Electronics) product).getBrand()+"<br>"+
                    ((Electronics) product).getWarrantyPeriod()+"months</html>";
        } else if (product instanceof Clothing) {
            return "<html>"+product.getProductID()+ "<br>"+
                    product.getProductName()+"<br>"+
                    ((Clothing) product).getSize()+", "+ ((Clothing) product).getColor()+"</html>";
        }
        return null;
    }
}

