package my.work.stock.system.web.view;

import java.io.Serializable;

public class SearchGivebackInfo extends PageBase implements Serializable {
    private String givebackDate;

    private Integer departmentId;
    private String givebackPerson;
    private Integer computerMaterialsId;

    private Integer categoryId;

    public String getGivebackDate() {
        return givebackDate;
    }

    public void setGivebackDate(String givebackDate) {
        this.givebackDate = givebackDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getGivebackPerson() {
        return givebackPerson;
    }

    public void setGivebackPerson(String givebackPerson) {
        this.givebackPerson = givebackPerson;
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
