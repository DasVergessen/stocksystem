package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.UserInfo;
import my.work.stock.system.domain.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfo findByUserName(String userName) {
        return userInfoRepository.findByUserName(userName);
    }

    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
