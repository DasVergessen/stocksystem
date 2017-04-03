package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.InnerDepartmentInfo;
import my.work.stock.system.domain.service.InnerDepartmentInfoService;
import my.work.stock.system.web.view.SearchInnerDepartmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/innerDepartmentInfo")
public class InnerDepartmentInfoController {
    @Autowired
    private InnerDepartmentInfoService innerDepartmentInfoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView get() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("basedatamanage/innerdepartmentinfo", model);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Page<InnerDepartmentInfo> page(SearchInnerDepartmentInfo searchInnerDepartmentInfo) {
        return innerDepartmentInfoService.searchInnerDepartmentInfo(searchInnerDepartmentInfo);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export() {
        return Boolean.TRUE;
    }
}
