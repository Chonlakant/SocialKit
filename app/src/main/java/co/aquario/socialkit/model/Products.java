package co.aquario.socialkit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root1 on 9/11/15.
 */
public class Products {

    /**
     * error : false
     * products : [{"farmerId":1,"desc":"ข้าวหอมนิล เป็นข้าวคัดพันธุ์ผสมระหว่างข้าวหอมมะลิ 105 และ ข้าวเหนียวดำสายพันธุ์จีน","image":"http://www.folkrice.com/api/img/product1.jpg","lineProductId":4940101,"name_th":"หอมนิล","productId":1,"size":0.7,"nutrition":"","price":99.5,"stock":20,"howtouse":"วิธีหุง ข้าว 1 ส่วน : น้ำ 2 ส่วน","name":"Black folk rice","catId":1,"thumb":"http://www.folkrice.com/api/img/thumb/product1.jpg","name2":"Hom-nin"}]
     */
    @SerializedName("error")
    private boolean error;
    @SerializedName("products")
    private List<ProductsEntity> products;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setProducts(List<ProductsEntity> products) {
        this.products = products;
    }

    public boolean isError() {
        return error;
    }

    public List<ProductsEntity> getProducts() {
        return products;
    }

    public class ProductsEntity {
        /**
         * farmerId : 1
         * desc : ข้าวหอมนิล เป็นข้าวคัดพันธุ์ผสมระหว่างข้าวหอมมะลิ 105 และ ข้าวเหนียวดำสายพันธุ์จีน
         * image : http://www.folkrice.com/api/img/product1.jpg
         * lineProductId : 4940101
         * name_th : หอมนิล
         * productId : 1
         * size : 0.7
         * nutrition :
         * price : 99.5
         * stock : 20
         * howtouse : วิธีหุง ข้าว 1 ส่วน : น้ำ 2 ส่วน
         * name : Black folk rice
         * catId : 1
         * thumb : http://www.folkrice.com/api/img/thumb/product1.jpg
         * name2 : Hom-nin
         */
        @SerializedName("farmerId")
        private int farmerId;
        @SerializedName("desc")
        private String desc;
        @SerializedName("image")
        private String image;
        @SerializedName("lineProductId")
        private int lineProductId;
        @SerializedName("name_th")
        private String name_th;
        @SerializedName("productId")
        private int productId;
        @SerializedName("size")
        private double size;
        @SerializedName("nutrition")
        private String nutrition;
        @SerializedName("price")
        private double price;
        @SerializedName("stock")
        private int stock;
        @SerializedName("howtouse")
        private String howtouse;
        @SerializedName("name")
        private String name;
        @SerializedName("catId")
        private int catId;
        @SerializedName("thumb")
        private String thumb;
        @SerializedName("name2")
        private String name2;




        public void setFarmerId(int farmerId) {
            this.farmerId = farmerId;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setLineProductId(int lineProductId) {
            this.lineProductId = lineProductId;
        }

        public void setName_th(String name_th) {
            this.name_th = name_th;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public void setSize(double size) {
            this.size = size;
        }

        public void setNutrition(String nutrition) {
            this.nutrition = nutrition;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void setHowtouse(String howtouse) {
            this.howtouse = howtouse;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public int getFarmerId() {
            return farmerId;
        }

        public String getDesc() {
            return desc;
        }

        public String getImage() {
            return image;
        }

        public int getLineProductId() {
            return lineProductId;
        }

        public String getName_th() {
            return name_th;
        }

        public int getProductId() {
            return productId;
        }

        public double getSize() {
            return size;
        }

        public String getNutrition() {
            return nutrition;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        public String getHowtouse() {
            return howtouse;
        }

        public String getName() {
            return name;
        }

        public int getCatId() {
            return catId;
        }

        public String getThumb() {
            return thumb;
        }

        public String getName2() {
            return name2;
        }
    }
}
