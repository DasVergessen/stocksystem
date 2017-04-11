package my.work.stock.system.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ReceiveInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer receiveId;

    @Column(nullable = false)
    private String receiveDate;

    @ManyToOne
    private InnerDepartmentInfo innerDepartmentInfo;

    @ManyToOne
    private ComputerMaterialsInfo computerMaterialsInfo;

    @ManyToOne
    ComputerMaterialsCategory computerMaterialsCategory;

    @Column(nullable = false)
    private Integer receiveQuantity;

    @Column(nullable = false)
    private Integer receivePrice;

    @Transient
    private String receivePriceString;

    @Column(length = 128)
    private String memo;

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public InnerDepartmentInfo getInnerDepartmentInfo() {
        return innerDepartmentInfo;
    }

    public void setInnerDepartmentInfo(InnerDepartmentInfo innerDepartmentInfo) {
        this.innerDepartmentInfo = innerDepartmentInfo;
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

    public Integer getReceiveQuantity() {
        return receiveQuantity;
    }

    public void setReceiveQuantity(Integer receiveQuantity) {
        this.receiveQuantity = receiveQuantity;
    }

    public Integer getReceivePrice() {
        return receivePrice;
    }

    public void setReceivePrice(Integer receivePrice) {
        this.receivePrice = receivePrice;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getReceivePriceString() {
        return receivePriceString;
    }

    public void setReceivePriceString(String receivePriceString) {
        this.receivePriceString = receivePriceString;
    }
}
