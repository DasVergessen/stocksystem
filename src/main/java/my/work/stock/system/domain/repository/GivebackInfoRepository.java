package my.work.stock.system.domain.repository;


import my.work.stock.system.domain.entity.GivebackInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface GivebackInfoRepository extends CrudRepository<GivebackInfo, Integer>, JpaSpecificationExecutor {

}
