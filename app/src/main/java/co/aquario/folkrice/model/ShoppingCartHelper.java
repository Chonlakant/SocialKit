package co.aquario.folkrice.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ShoppingCartHelper  {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<ProductAquery> catalog = new Vector<>();
    private static Map<ProductAquery, ShoppingCartEntry> cartMap = new HashMap<ProductAquery, ShoppingCartEntry>();

    public static List<ProductAquery> getCatalog(){
        return catalog;
    }

    public static void setQuantity(ProductAquery product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(ProductAquery product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(ProductAquery product) {
        cartMap.remove(product);
    }

    public static List<ProductAquery> getCartList() {
        List<ProductAquery> cartList = new Vector<ProductAquery>(cartMap.keySet().size());
        for(ProductAquery p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }


}