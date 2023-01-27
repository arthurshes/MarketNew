package mychati.app.Client.CartAdapter;

public class MartAdapter {
    private String Fixprice,tovarcartShopuid,TovarValue,ProductId,Price,tovarname,tovarImage;


    public MartAdapter() {
    }

    public MartAdapter(String Fixprice,String tovarcartShopuid, String tovarValue, String productId, String price, String tovarname, String tovarImage) {
        this.tovarcartShopuid = tovarcartShopuid;
        this.Fixprice=Fixprice;
        this.TovarValue = tovarValue;
       this. ProductId = productId;
      this.  Price = price;
        this.tovarname = tovarname;
        this.tovarImage = tovarImage;
    }

    public String getFixprice() {
        return Fixprice;
    }

    public void setFixprice(String fixprice) {
        Fixprice = fixprice;
    }

    public String getTovarcartShopuid() {
        return tovarcartShopuid;
    }

    public void setTovarcartShopuid(String tovarcartShopuid) {
        this.tovarcartShopuid = tovarcartShopuid;
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
