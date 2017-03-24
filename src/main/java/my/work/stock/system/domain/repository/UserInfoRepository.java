package my.work.stock.system.domain.repository;


import my.work.stock.system.domain.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    UserInfo findByUserName(String userName);
}
