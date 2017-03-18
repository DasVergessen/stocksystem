package my.work.stock.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("home", model);
    }
}
