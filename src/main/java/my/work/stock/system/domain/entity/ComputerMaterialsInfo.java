package my.work.stock.system.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ComputerMaterialsInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer computerMaterialsId;

    @Column(length = 64, nullable = false)
    private String computerMaterialsName;
    @ManyToOne
    private ComputerMaterialsCategory computerMaterialsCategory;
    @Column(length = 64)
    private String computerMaterialsSpecifications;
    @Column(length = 64)
    private String computerMaterialsUnit;

    public Integer getComputerMaterialsId() {
        return computerMaterialsId;
    }

    public void setComputerMaterialsId(Integer computerMaterialsId) {
        this.computerMaterialsId = computerMaterialsId;
    }

    public String getComputerMaterialsName() {
        return computerMaterialsName;
    }

    public void setComputerMaterialsName(String computerMaterialsName) {
        this.computerMaterialsName = computerMaterialsName;
    }

    public ComputerMaterialsCategory getComputerMaterialsCategory() {
        return computerMaterialsCategory;
    }

    public void setComputerMaterialsCategory(ComputerMaterialsCategory computerMaterialsCategory) {
        this.computerMaterialsCategory = computerMaterialsCategory;
    }

    public String getComputerMaterialsSpecifications() {
        return computerMaterialsSpecifications;
    }

    public void setComputerMaterialsSpecifications(String computerMaterialsSpecifications) {
        this.computerMaterialsSpecifications = computerMaterialsSpecifications;
    }

    public String getComputerMaterialsUnit() {
        return computerMaterialsUnit;
    }

    public void setComputerMaterialsUnit(String computerMaterialsUnit) {
        this.computerMaterialsUnit = computerMaterialsUnit;
    }
}
