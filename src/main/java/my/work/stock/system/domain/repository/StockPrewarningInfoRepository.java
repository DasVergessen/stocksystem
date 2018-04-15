package my.work.stock.system.domain.repository;

import my.work.stock.system.domain.entity.StockPrewarningInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPrewarningInfoRepository extends CrudRepository<StockPrewarningInfo, Integer> {

}
