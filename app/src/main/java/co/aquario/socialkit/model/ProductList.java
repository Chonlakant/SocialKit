package co.aquario.socialkit.model;

import java.util.List;

/**
 * Created by root1 on 9/11/15.
 */
public class ProductList {

    private List<Products> shots;

    public ProductList(List<Products> shots) {
        this.shots = shots;
    }

    public List<Products> getShots() {
        return shots;
    }

    public void setShots(List<Products> shots) {
        this.shots = shots;
    }
}
