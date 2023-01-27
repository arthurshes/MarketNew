package mychati.app.Shops.MyTovarAdapter;

public class MyTovarAdapter {
    private String ProductTime,TovarName,TovarOpisanie,TovarPrice,TovarImage,ShopPhone,ShopUid;
public MyTovarAdapter(){

}

    public MyTovarAdapter(String ProductTime,String tovarName, String tovarOpisanie, String tovarPrice, String tovarImage, String shopPhone, String shopUid) {
       this. TovarName = tovarName;
       this. TovarOpisanie = tovarOpisanie;
     this.   TovarPrice = tovarPrice;
      this.  TovarImage = tovarImage;
      this.ProductTime=ProductTime;
      this.  ShopPhone = shopPhone;
      this.  ShopUid = shopUid;
    }

    public String getProductTime() {
        return ProductTime;
    }

    public void setProductTime(String productTime) {
        ProductTime = productTime;
    }

    public String getTovarName() {
        return TovarName;
    }

    public void setTovarName(String tovarName) {
        TovarName = tovarName;
    }

    public String getTovarOpisanie() {
        return TovarOpisanie;
    }

    public void setTovarOpisanie(String tovarOpisanie) {
        TovarOpisanie = tovarOpisanie;
    }

    public String getTovarPrice() {
        return TovarPrice;
    }

    public void setTovarPrice(String tovarPrice) {
        TovarPrice = tovarPrice;
    }

    public String getTovarImage() {
        return TovarImage;
    }

    public void setTovarImage(String tovarImage) {
        TovarImage = tovarImage;
    }

    public String getShopPhone() {
        return ShopPhone;
    }

    public void setShopPhone(String shopPhone) {
        ShopPhone = shopPhone;
    }

    public String getShopUid() {
        return ShopUid;
    }

    public void setShopUid(String shopUid) {
        ShopUid = shopUid;
    }
}
