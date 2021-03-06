package co.aquario.folkrice.model;

import org.parceler.Parcel;

/**
 * Created by root1 on 9/11/15.
 */

@Parcel
public class ProductAquery {
    int productId ;
    int lineProductId ;
    int farmerId;
    int catId;
    String name;
    String name2;
    String nameTh;
    String desc;
    Double price;
    String howtouse;
    String nutrition;
    int stock ;
    int size ;
    String image;
    String thumb;
    boolean mHeart;
    public boolean selected;

    public ProductAquery(){

    }

    public ProductAquery(int productId, int lineProductId, int farmerId, int catId, String name, String name2, String nameTh, String desc, Double price, String howtouse, String nutrition, int stock, int size, String image, String thumb) {
        this.productId = productId;
        this.lineProductId = lineProductId;
        this.farmerId = farmerId;
        this.catId = catId;
        this.name = name;
        this.name2 = name2;
        this.nameTh = nameTh;
        this.desc = desc;
        this.price = price;
        this.howtouse = howtouse;
        this.nutrition = nutrition;
        this.stock = stock;
        this.size = size;
        this.image = image;
        this.thumb = thumb;
        mHeart = false;
    }

    public boolean ismHeart() {
        return mHeart;
    }

    public void setmHeart(boolean mHeart) {
        this.mHeart = mHeart;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getLineProductId() {
        return lineProductId;
    }

    public void setLineProductId(int lineProductId) {
        this.lineProductId = lineProductId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getHowtouse() {
        return howtouse;
    }

    public void setHowtouse(String howtouse) {
        this.howtouse = howtouse;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
