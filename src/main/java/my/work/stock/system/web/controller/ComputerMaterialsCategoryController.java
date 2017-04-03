package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.service.ComputerMaterialsCategoryService;
import my.work.stock.system.web.view.SearchComputerMaterialsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/computerMaterialsCategory")
public class ComputerMaterialsCategoryController {

    @Autowired
    private ComputerMaterialsCategoryService computerMaterialsCategoryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView get() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("basedatamanage/computermaterialscategory", model);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Page<ComputerMaterialsCategory> page(SearchComputerMaterialsCategory searchComputerMaterialsCategory) {
        return computerMaterialsCategoryService.searchComputerMaterialsCategory(searchComputerMaterialsCategory);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export() {
        return Boolean.TRUE;
    }
}
