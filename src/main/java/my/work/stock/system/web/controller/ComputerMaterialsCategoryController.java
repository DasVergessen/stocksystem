package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.service.ComputerMaterialsCategoryService;
import my.work.stock.system.web.view.SearchComputerMaterialsCategory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@RequestBody ComputerMaterialsCategory computerMaterialsCategory) {
        computerMaterialsCategoryService.save(computerMaterialsCategory);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody ComputerMaterialsCategory computerMaterialsCategory) {
        computerMaterialsCategoryService.delete(computerMaterialsCategory);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Page<ComputerMaterialsCategory> page(@RequestBody SearchComputerMaterialsCategory searchComputerMaterialsCategory) {
        return computerMaterialsCategoryService.searchComputerMaterialsCategory(searchComputerMaterialsCategory);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<ComputerMaterialsCategory> all() {
        return computerMaterialsCategoryService.findAllComputerMaterialsCategory();
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export(HttpServletResponse response) throws IOException {
        List<ComputerMaterialsCategory> allComputerMaterialsCategory = computerMaterialsCategoryService.findAllComputerMaterialsCategory();
        response.setContentType("text/csv;charset=GBK");
        String headerKey = "Content-Disposition";
        String csvFileName = "计算机耗材类别信息.xlsx";
        String headerValue = String.format("attachment; filename=\"%s\"", new String(csvFileName.getBytes(Charset.forName("GBK")), "ISO-8859-1"));
        response.setHeader(headerKey, headerValue);
        Workbook wb = new XSSFWorkbook();
        Sheet computerMaterialsCategorySheet = wb.createSheet("计算机耗材类别信息");

        Row header = computerMaterialsCategorySheet.createRow((short) 0);//头
        header.createCell(0).setCellValue("类别编码");
        header.createCell(1).setCellValue("类别名称");

        for (int i = 0; i < allComputerMaterialsCategory.size(); i++) {
            Row computerMaterialsCategorySheetRow = computerMaterialsCategorySheet.createRow(i + 1);
            ComputerMaterialsCategory computerMaterialsCategory = allComputerMaterialsCategory.get(i);
            computerMaterialsCategorySheetRow.createCell(0).setCellValue(computerMaterialsCategory.getCategoryId());
            computerMaterialsCategorySheetRow.createCell(1).setCellValue(computerMaterialsCategory.getCategoryName());
        }

        wb.write(response.getOutputStream());
        wb.close();
        return Boolean.TRUE;
    }
}
