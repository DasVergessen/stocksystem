package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.PurchaseInfo;
import my.work.stock.system.domain.service.PurchaseInfoService;
import my.work.stock.system.web.view.SearchPurchaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/purchaseInfo")
public class PurchaseInfoController {
    @Autowired
    private PurchaseInfoService purchaseInfoService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("purchaseinfo/list", model);
    }

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public ModelAndView report() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("purchaseinfo/report", model);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Page<PurchaseInfo> page(@RequestBody SearchPurchaseInfo searchPurchaseInfo) {
        return purchaseInfoService.searchPurchaseInfo(searchPurchaseInfo);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export() {
        return Boolean.TRUE;
    }
}
