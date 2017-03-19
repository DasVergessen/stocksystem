package my.work.stock.system.web.controller;

import my.work.stock.system.domain.service.MD5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private MD5Service md5Service;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("login", model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestParam String username, @RequestParam String password) throws IOException {
        if (StringUtils.startsWithIgnoreCase(username, "admin")) {
            String md5Password = md5Service.getMD5(password);
            //根据用户名从数据库得到加密存储的密码,对比
            HttpSession session = httpServletRequest.getSession(true);
            session.setAttribute("hasLogin", "true");
            response.sendRedirect("/sys");
        } else {
            response.sendRedirect("/login?error=userNotFound");
            return null;
        }
        return null;
    }
}
