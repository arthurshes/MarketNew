package mychati.app.Shops.ZakazAdapter;

public class ZakazAdapter {

    private String itogPrice,ShopUid,ProductId,ClientUid,phone,adress,podezd,kvartira,lvl,domophone;





    public ZakazAdapter(){

    }


    public ZakazAdapter(String itogPrice,String ShopUid,String ProductId,String ClientUid,String phone, String adress, String podezd, String kvartira, String lvl, String domophone) {
        this.phone = phone;
        this.ProductId=ProductId;
        this.ShopUid=ShopUid;
        this.adress = adress;
        this.podezd = podezd;
        this.kvartira = kvartira;
        this.lvl = lvl;
        this.ClientUid=ClientUid;
        this.domophone = domophone;
        this.itogPrice=itogPrice;
    }


    public String getItogPrice() {
        return itogPrice;
    }

    public void setItogPrice(String itogPrice) {
        this.itogPrice = itogPrice;
    }

    public String getShopUid() {
        return ShopUid;
    }

    public void setShopUid(String shopUid) {
        ShopUid = shopUid;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getClientUid() {
        return ClientUid;
    }

    public void setClientUid(String clientUid) {
        ClientUid = clientUid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPodezd() {
        return podezd;
    }

    public void setPodezd(String podezd) {
        this.podezd = podezd;
    }

    public String getKvartira() {
        return kvartira;
    }

    public void setKvartira(String kvartira) {
        this.kvartira = kvartira;
    }

    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public String getDomophone() {
        return domophone;
    }

    public void setDomophone(String domophone) {
        this.domophone = domophone;
    }
}
