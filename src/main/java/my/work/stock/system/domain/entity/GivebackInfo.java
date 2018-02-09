package my.work.stock.system.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class GivebackInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer givebackId;

    @Column(nullable = false)
    private String givebackDate;

    @ManyToOne
    private InnerDepartmentInfo innerDepartmentInfo;

    @ManyToOne
    private ComputerMaterialsInfo computerMaterialsInfo;

    @ManyToOne
    ComputerMaterialsCategory computerMaterialsCategory;

    @Column(length = 8, nullable = false)
    private String givebackPerson;

    @Column(nullable = false)
    private Integer givebackQuantity;

    @Column(length = 128)
    private String memo;

    public Integer getGivebackId() {
        return givebackId;
    }

    public void setGivebackId(Integer givebackId) {
        this.givebackId = givebackId;
    }

    public String getGivebackDate() {
        return givebackDate;
    }

    public void setGivebackDate(String givebackDate) {
        this.givebackDate = givebackDate;
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

    public String getGivebackPerson() {
        return givebackPerson;
    }

    public void setGivebackPerson(String givebackPerson) {
        this.givebackPerson = givebackPerson;
    }

    public Integer getGivebackQuantity() {
        return givebackQuantity;
    }

    public void setGivebackQuantity(Integer givebackQuantity) {
        this.givebackQuantity = givebackQuantity;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
