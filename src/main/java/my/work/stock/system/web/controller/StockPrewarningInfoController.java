package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.domain.entity.StockPrewarningInfo;
import my.work.stock.system.domain.service.ComputerMaterialsCategoryService;
import my.work.stock.system.domain.service.ComputerMaterialsInfoService;
import my.work.stock.system.domain.service.StockPrewarningInfoService;
import my.work.stock.system.web.view.SearchComputerMaterialsInfo;
import my.work.stock.system.web.view.StockPrewarningInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stockprewarning")

public class StockPrewarningInfoController {
    @Autowired
    private StockPrewarningInfoService stockPrewarningInfoService;

    @Autowired
    private ComputerMaterialsCategoryService computerMaterialsCategoryService;
    @Autowired
    private ComputerMaterialsInfoService computerMaterialsInfoService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("stockprewarning/list", model);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody StockPrewarningInfo stockPrewarningInfo) {
        return stockPrewarningInfoService.save(stockPrewarningInfo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam Integer categoryId) {
        return stockPrewarningInfoService.delete(categoryId);
    }

    @RequestMapping(value = "all", method = RequestMethod.POST)
    public List<StockPrewarningInfoView> all() {
        final List<StockPrewarningInfoView> stockPrewarningInfoViewList = new ArrayList<>();
        List<StockPrewarningInfo> stockPrewarningInfoList = stockPrewarningInfoService.all();
        for (int i = 0; i < stockPrewarningInfoList.size(); i++) {
            StockPrewarningInfo stockPrewarningInfo = stockPrewarningInfoList.get(i);
            StockPrewarningInfoView stockPrewarningInfoView = new StockPrewarningInfoView();
            Integer categoryId = stockPrewarningInfo.getCategoryId();
            stockPrewarningInfoView.setCategoryId(categoryId);
            Integer prewarningStock = stockPrewarningInfo.getPrewarningStock();
            stockPrewarningInfoView.setPrewarningStock(prewarningStock);
            ComputerMaterialsCategory computerMaterialsCategory = computerMaterialsCategoryService.findOne(categoryId);
            String categoryName = computerMaterialsCategory.getCategoryName();
            stockPrewarningInfoView.setCategoryName(categoryName);
            Integer realStock = 0;
            SearchComputerMaterialsInfo searchComputerMaterialsInfo = new SearchComputerMaterialsInfo();
            searchComputerMaterialsInfo.setCategoryId(categoryId);
            List<ComputerMaterialsInfo> computerMaterialsInfoList = computerMaterialsInfoService.searchComputerMaterialsInfoAll(searchComputerMaterialsInfo);
            for (int j = 0; j < computerMaterialsInfoList.size(); j++) {
                ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoList.get(j);
                realStock += computerMaterialsInfo.getQuantity();
            }
            if (realStock <= prewarningStock) {
                stockPrewarningInfoView.setWarning(true);
            }
            stockPrewarningInfoViewList.add(stockPrewarningInfoView);
        }
        return stockPrewarningInfoViewList;
    }
}
