package my.work.stock.system.web.view;


import java.io.Serializable;

public class SearchPurchaseInfo extends PageBase implements Serializable {

    private String purchaseDate;

    private Integer supplierId;

    private Integer computerMaterialsId;

    private Integer categoryId;

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getComputerMaterialsId() {
        return computerMaterialsId;
    }

    public void setComputerMaterialsId(Integer computerMaterialsId) {
        this.computerMaterialsId = computerMaterialsId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
