package my.work.stock.system.web.view;

import java.io.Serializable;

public class SearchInnerDepartmentInfo extends PageBase implements Serializable {

    private Integer departmentId;

    private String departmentName;

    private String departmentFunction;

    private String mobileOfPropertyAdmin;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentFunction() {
        return departmentFunction;
    }

    public void setDepartmentFunction(String departmentFunction) {
        this.departmentFunction = departmentFunction;
    }

    public String getMobileOfPropertyAdmin() {
        return mobileOfPropertyAdmin;
    }

    public void setMobileOfPropertyAdmin(String mobileOfPropertyAdmin) {
        this.mobileOfPropertyAdmin = mobileOfPropertyAdmin;
    }
}
