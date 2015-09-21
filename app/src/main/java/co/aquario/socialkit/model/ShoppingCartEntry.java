package co.aquario.socialkit.model;


public class ShoppingCartEntry {

    private ProductAquery mProduct;
    private int mQuantity;

    public ShoppingCartEntry(ProductAquery product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public ProductAquery getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}