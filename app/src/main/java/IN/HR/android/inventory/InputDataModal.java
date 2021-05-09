package IN.HR.android.inventory;

public class InputDataModal {
    private  String ProductName,ProductPrice , ProductModel,
            ProductCompany,PurchaseDate, PurchasedFrom, SerialNumber;

    // create constructor to set and get the values

    public InputDataModal(String productName, String productPrice, String productModel,
                          String productCompany, String purchaseDate, String purchasedFrom, String serialNumber) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductModel = productModel;
        ProductCompany = productCompany;
        PurchaseDate = purchaseDate;
        PurchasedFrom = purchasedFrom;
        SerialNumber = serialNumber;
    }

    //generate getter and setter for the above constructor

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductModel() {
        return ProductModel;
    }

    public void setProductModel(String productModel) {
        ProductModel = productModel;
    }

    public String getProductCompany() {
        return ProductCompany;
    }

    public void setProductCompany(String productCompany) {
        ProductCompany = productCompany;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getPurchasedFrom() {
        return PurchasedFrom;
    }

    public void setPurchasedFrom(String purchasedFrom) {
        PurchasedFrom = purchasedFrom;
    }
}
