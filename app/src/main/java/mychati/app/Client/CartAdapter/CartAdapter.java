package mychati.app.Client.CartAdapter;

public class CartAdapter {
    private String MagName,MagLogo,ShopUid,TovarValue,ProductId,Price,tovarname,tovarImage;
    private int Valueprice;
    public CartAdapter(){

    }

    public CartAdapter(String MagName,String MagLogo,String tovarValue, String productId, String price, String tovarname, String ShopUid, String tovarImage) {
        this.TovarValue = tovarValue;
        this.MagName=MagName;
      this.  ProductId = productId;
     this.   Price = price;
     this.ShopUid=ShopUid;
        this.tovarname = tovarname;
this.MagLogo=MagLogo;
        this.tovarImage = tovarImage;

    }


    public String getShopUid() {
        return ShopUid;
    }

    public void setShopUid(String shopUid) {
        ShopUid = shopUid;
    }

    public String getMagName() {
        return MagName;
    }

    public void setMagName(String magName) {
        MagName = magName;
    }

    public String getMagLogo() {
        return MagLogo;
    }

    public void setMagLogo(String magLogo) {
        MagLogo = magLogo;
    }

    public int getValueprice() {
        return Valueprice;
    }

    public void setValueprice(int valueprice) {
        Valueprice = valueprice;
    }

    public String getTovarValue() {
        return TovarValue;
    }

    public void setTovarValue(String tovarValue) {
        TovarValue = tovarValue;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTovarname() {
        return tovarname;
    }

    public void setTovarname(String tovarname) {
        this.tovarname = tovarname;
    }



    public String getTovarImage() {
        return tovarImage;
    }

    public void setTovarImage(String tovarImage) {
        this.tovarImage = tovarImage;
    }





}
