package mychati.app.Client.ProfileAdapter;

public class ProfileAdapter {
    private String Prochitan,shopId,ProductId,Minute,Hour,Date, adress,Moth;

    public ProfileAdapter() {
    }

    public ProfileAdapter(String Prochitan,String ProductId,String shopId,String Moth,String Minute, String Hour, String Date, String adress) {
       this. Minute = Minute;
     this.   Hour = Hour;
     this.Prochitan=Prochitan;
     this.ProductId=ProductId;
     this.Moth=Moth;
     this.shopId=shopId;
       this. Date = Date;
        this.adress = adress;
    }


    public String getProchitan() {
        return Prochitan;
    }

    public void setProchitan(String prochitan) {
        Prochitan = prochitan;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getMoth() {
        return Moth;
    }

    public void setMoth(String moth) {
        Moth = moth;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        Minute = minute;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}
