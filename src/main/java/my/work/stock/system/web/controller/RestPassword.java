package my.work.stock.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RestPassword {
    @RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
    public ModelAndView resetpassword() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("resetpassword", model);
    }
}
