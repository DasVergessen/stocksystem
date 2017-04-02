package my.work.stock.system.domain.repository;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerMaterialsCategoryRepository extends CrudRepository<ComputerMaterialsCategory, Integer>, JpaSpecificationExecutor {

}
