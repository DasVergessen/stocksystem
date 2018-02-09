package my.work.stock.system.domain.repository;

import my.work.stock.system.domain.entity.ReceiveInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiveInfoRepository extends CrudRepository<ReceiveInfo, Integer>, JpaSpecificationExecutor {
    @Query(value = "select IFNULL(SUM(receive_quantity),0) "
            + "from receive_info "
            + "where computer_materials_info_computer_materials_id = :materialsId and inner_department_info_department_id = :departmentId and  receive_date like :month% ", nativeQuery = true)
    Object[] sumSameMaterialsAndDepartmentAtCurrentMonth(@Param("materialsId") Integer materialsId, @Param("departmentId") Integer departmentId, @Param("month") String month);
}
