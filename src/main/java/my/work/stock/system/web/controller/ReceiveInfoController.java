package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.ReceiveInfo;
import my.work.stock.system.domain.service.ReceiveInfoService;
import my.work.stock.system.web.view.SearchReceiveInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/receiveInfo")
public class ReceiveInfoController {
    @Autowired
    private ReceiveInfoService receiveInfoService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("receiveinfo/list", model);
    }

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public ModelAndView report() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("receiveinfo/report", model);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Page<ReceiveInfo> page(SearchReceiveInfo searchReceiveInfo) {
        return receiveInfoService.searchReceiveInfo(searchReceiveInfo);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export() {
        return Boolean.TRUE;
    }
}
