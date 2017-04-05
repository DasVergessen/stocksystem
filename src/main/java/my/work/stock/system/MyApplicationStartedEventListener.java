package my.work.stock.system;

import my.work.stock.system.domain.entity.UserInfo;
import my.work.stock.system.domain.service.UserInfoService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class MyApplicationStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        UserInfoService userInfoService = contextRefreshedEvent.getApplicationContext().getBean(UserInfoService.class);
        UserInfo admin = userInfoService.findByUserName("admin");
        if (admin == null) {//初始化用户密码
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("admin");
            userInfo.setPassword("123456");
            userInfoService.save(userInfo);
        }
    }
}
