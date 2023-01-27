package mychati.app.Client.ClientTovarsAdaoter;

public class TovarsAdapter {

    private String MagLogo,MagName,TovarStatus,ProductTime,TovarName,TovarOpisanie,TovarPrice,TovarImage,ShopPhone,ShopUid;

    public TovarsAdapter(){

    }

    public TovarsAdapter(String MagLogo,String MagName,String TovarStatus,String ProductTime,String tovarName, String tovarOpisanie, String tovarPrice, String tovarImage, String shopPhone, String shopUid) {
       this. TovarName = tovarName;
      this.  TovarOpisanie = tovarOpisanie;
      this.MagName=MagName;
      this.MagLogo=MagLogo;
      this.  TovarPrice = tovarPrice;
      this.  TovarImage = tovarImage;
      this.TovarStatus=TovarStatus;
       this. ShopPhone = shopPhone;
     this.   ShopUid = shopUid;
     this.ProductTime=ProductTime;
    }

    public String getMagLogo() {
        return MagLogo;
    }

    public void setMagLogo(String magLogo) {
        MagLogo = magLogo;
    }

    public String getMagName() {
        return MagName;
    }

    public void setMagName(String magName) {
        MagName = magName;
    }

    public String getTovarStatus() {
        return TovarStatus;
    }

    public void setTovarStatus(String tovarStatus) {
        TovarStatus = tovarStatus;
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
