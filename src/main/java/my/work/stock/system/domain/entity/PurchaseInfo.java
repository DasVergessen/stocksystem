package my.work.stock.system.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class PurchaseInfo implements Serializable {
    
    @Id
    @GeneratedValue
    private Integer purchaseId;

    @Column(length = 64)
    private String purchaseCode;

    @Column(nullable = false)
    private String purchaseDate;

    @ManyToOne
    private SupplierInfo supplierInfo;

    @ManyToOne
    private ComputerMaterialsInfo computerMaterialsInfo;

    @ManyToOne
    ComputerMaterialsCategory computerMaterialsCategory;

    @Column(nullable = false)
    private Integer purchaseQuantity;

    @Column(nullable = false)
    private Integer purchasePrice;

    @Column(length = 128)
    private String memo;

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public SupplierInfo getSupplierInfo() {
        return supplierInfo;
    }

    public void setSupplierInfo(SupplierInfo supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public ComputerMaterialsInfo getComputerMaterialsInfo() {
        return computerMaterialsInfo;
    }

    public void setComputerMaterialsInfo(ComputerMaterialsInfo computerMaterialsInfo) {
        this.computerMaterialsInfo = computerMaterialsInfo;
    }

    public ComputerMaterialsCategory getComputerMaterialsCategory() {
        return computerMaterialsCategory;
    }

    public void setComputerMaterialsCategory(ComputerMaterialsCategory computerMaterialsCategory) {
        this.computerMaterialsCategory = computerMaterialsCategory;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
