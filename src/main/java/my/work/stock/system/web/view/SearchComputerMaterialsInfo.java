package my.work.stock.system.web.view;


import java.io.Serializable;

public class SearchComputerMaterialsInfo extends PageBase implements Serializable {

    private Integer computerMaterialsId;
    private String computerMaterialsName;
    private Integer categoryId;
    private String computerMaterialsSpecifications;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
