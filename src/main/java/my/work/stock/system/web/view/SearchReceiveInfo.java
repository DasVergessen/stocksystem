package my.work.stock.system.web.view;

import java.io.Serializable;

public class SearchReceiveInfo extends PageBase implements Serializable {
    private String receiveDate;

    private Integer departmentId;

    private Integer computerMaterialsId;

    private Integer categoryId;

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
