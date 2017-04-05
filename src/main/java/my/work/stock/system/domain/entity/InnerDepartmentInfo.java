package my.work.stock.system.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class InnerDepartmentInfo implements Serializable {
    @Id
    @GeneratedValue
    private Integer departmentId;

    @Column(length = 64, nullable = false)
    private String departmentName;

    @Column(length = 64)
    private String departmentFunction;

    @Column(length = 64)
    private String mobileOfPropertyAdmin;

    @Column(length = 256)
    private String memo;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
