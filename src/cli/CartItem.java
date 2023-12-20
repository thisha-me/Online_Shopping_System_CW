package cli;

public class CartItem{
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
}

