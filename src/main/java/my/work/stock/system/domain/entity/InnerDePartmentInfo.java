package my.work.stock.system.domain.entity;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class InnerDepartmentInfo implements Serializable {
    private Integer departmentId;
    private String departmentName;
    private String departmentFunction;
    private String mobileOfPropertyAdmin;
    private String memo;

}
