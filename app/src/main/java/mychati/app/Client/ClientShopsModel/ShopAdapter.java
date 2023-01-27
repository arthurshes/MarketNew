package mychati.app.Client.ClientShopsModel;

public class ShopAdapter {
    private String ZaverName,ProductId,shopId,Value,ShopUid,Zakazstatus,MagName,MagCity,MagAdress,MagUid,MagLogo,MagNumberReg,MagNumber,MagCategory;
    public ShopAdapter() {

    }


    public ShopAdapter(String ZaverName,String ProductId,String shopId,String Value,String ShopUid,String Zakazstatus,String magName, String magCity, String magAdress, String magUid, String magLogo, String magNumberReg, String magNumber, String magCategory) {
       this. MagName = magName;
       this.Zakazstatus=Zakazstatus;
      this.  MagCity = magCity;
      this.Value=Value;
      this.ZaverName=ZaverName;
      this.shopId=shopId;
    this.    MagAdress = magAdress;
     this.   MagUid = magUid;
     this.ShopUid=ShopUid;
    this.    MagLogo = magLogo;
    this.ProductId=ProductId;
        this.      MagNumberReg = magNumberReg;
     this.   MagNumber = magNumber;
     this.   MagCategory = magCategory;
    }


    public String getZaverName() {
        return ZaverName;
    }

    public void setZaverName(String zaverName) {
        ZaverName = zaverName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getShopUid() {
        return ShopUid;
    }

    public void setShopUid(String shopUid) {
        ShopUid = shopUid;
    }

    public String getZakazstatus() {
        return Zakazstatus;
    }

    public void setZakazstatus(String zakazstatus) {
        Zakazstatus = zakazstatus;
    }

    public String getMagName() {
        return MagName;
    }

    public void setMagName(String magName) {
        MagName = magName;
    }

    public String getMagCity() {
        return MagCity;
    }

    public void setMagCity(String magCity) {
        MagCity = magCity;
    }

    public String getMagAdress() {
        return MagAdress;
    }

    public void setMagAdress(String magAdress) {
        MagAdress = magAdress;
    }

    public String getMagUid() {
        return MagUid;
    }

    public void setMagUid(String magUid) {
        MagUid = magUid;
    }

    public String getMagLogo() {
        return MagLogo;
    }

    public void setMagLogo(String magLogo) {
        MagLogo = magLogo;
    }

    public String getMagNumberReg() {
        return MagNumberReg;
    }

    public void setMagNumberReg(String magNumberReg) {
        MagNumberReg = magNumberReg;
    }

    public String getMagNumber() {
        return MagNumber;
    }

    public void setMagNumber(String magNumber) {
        MagNumber = magNumber;
    }

    public String getMagCategory() {
        return MagCategory;
    }

    public void setMagCategory(String magCategory) {
        MagCategory = magCategory;
    }
}
