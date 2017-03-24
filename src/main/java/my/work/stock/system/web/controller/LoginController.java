package my.work.stock.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest httpServletRequest) {
        String error = httpServletRequest.getParameter("error");
        Map<String, String> model = new HashMap<>();
        if (error != null) {
            model.put("message", "用户名或密码错误!");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView doLogin(HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {
        String exception = (String) httpServletRequest.getAttribute("shiroLoginFailure");
        if (exception != null) {
            response.sendRedirect("/login?error=userOrPasswordError");
        }
        return null;
    }
}
