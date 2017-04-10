package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.domain.service.ComputerMaterialsInfoService;
import my.work.stock.system.web.view.SearchComputerMaterialsInfo;
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
@RequestMapping("/computerMaterialsInfo")
public class ComputerMaterialsInfoController {
    @Autowired
    private ComputerMaterialsInfoService computerMaterialsInfoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView get() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("basedatamanage/computermaterialsinfo", model);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@RequestBody ComputerMaterialsInfo computerMaterialsInfo) {
        computerMaterialsInfoService.save(computerMaterialsInfo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody ComputerMaterialsInfo computerMaterialsInfo) {
        computerMaterialsInfoService.delete(computerMaterialsInfo);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Page<ComputerMaterialsInfo> page(@RequestBody SearchComputerMaterialsInfo searchComputerMaterialsInfo) {
        return computerMaterialsInfoService.searchComputerMaterialsInfo(searchComputerMaterialsInfo);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export(HttpServletResponse response) throws IOException {
        List<ComputerMaterialsInfo> allComputerMaterialsInfo = computerMaterialsInfoService.findAllComputerMaterialsInfo();
        response.setContentType("text/csv;charset=GBK");
        String headerKey = "Content-Disposition";
        String csvFileName = "计算机耗材信息.xlsx";
        String headerValue = String.format("attachment; filename=\"%s\"", new String(csvFileName.getBytes(Charset.forName("GBK")), "ISO-8859-1"));
        response.setHeader(headerKey, headerValue);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("计算机耗材信息");

        Row header = sheet.createRow((short) 0);//头
        header.createCell(0).setCellValue("计算机耗材编码");
        header.createCell(1).setCellValue("计算机耗材名称");
        header.createCell(2).setCellValue("所属类别");
        header.createCell(3).setCellValue("规格型号");
        header.createCell(4).setCellValue("计量单位");

        for (int i = 0; i < allComputerMaterialsInfo.size(); i++) {
            Row row = sheet.createRow(i + 1);
            ComputerMaterialsInfo computerMaterialsInfo = allComputerMaterialsInfo.get(i);
            row.createCell(0).setCellValue(computerMaterialsInfo.getComputerMaterialsId());
            row.createCell(1).setCellValue(computerMaterialsInfo.getComputerMaterialsName());
            row.createCell(2).setCellValue(computerMaterialsInfo.getComputerMaterialsCategory().getCategoryName());
            row.createCell(3).setCellValue(computerMaterialsInfo.getComputerMaterialsSpecifications());
            row.createCell(4).setCellValue(computerMaterialsInfo.getComputerMaterialsUnit());
        }

        wb.write(response.getOutputStream());
        wb.close();
        return Boolean.TRUE;
    }
}
