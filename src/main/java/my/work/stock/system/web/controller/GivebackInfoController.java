package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.GivebackInfo;
import my.work.stock.system.domain.entity.ReceiveInfo;
import my.work.stock.system.domain.service.GivebackInfoService;
import my.work.stock.system.web.view.SearchGivebackInfo;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/givebackInfo")
public class GivebackInfoController {
    @Autowired
    private GivebackInfoService givebackInfoService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("givebackinfo/list", model);
    }

    @RequestMapping(value = "report", method = RequestMethod.GET)
    public ModelAndView report() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("givebackinfo/report", model);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody GivebackInfo givebackInfo) {
        return givebackInfoService.save(givebackInfo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestBody GivebackInfo givebackInfo) {
        return givebackInfoService.delete(givebackInfo);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Page<GivebackInfo> page(@RequestBody SearchGivebackInfo searchGivebackInfo) {
        return givebackInfoService.searchGivebackInfo(searchGivebackInfo);
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export(HttpServletResponse response) throws IOException {

        List<GivebackInfo> allGivebackInfo = givebackInfoService.findAllGivebackInfo();
        response.setContentType("text/csv;charset=GBK");
        String headerKey = "Content-Disposition";
        String csvFileName = "领用出库.xlsx";
        String headerValue = String.format("attachment; filename=\"%s\"", new String(csvFileName.getBytes(Charset.forName("GBK")), "ISO-8859-1"));
        response.setHeader(headerKey, headerValue);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("领用出库");

        Row header = sheet.createRow((short) 0);//头
        header.createCell(0).setCellValue("出库单号");
        header.createCell(1).setCellValue("出库日期");
        header.createCell(2).setCellValue("领用部门");
        header.createCell(3).setCellValue("耗材编码");
        header.createCell(4).setCellValue("耗材名称");
        header.createCell(5).setCellValue("耗材规格");
        header.createCell(6).setCellValue("所属类别");
        header.createCell(7).setCellValue("单位");
        header.createCell(8).setCellValue("出库数量");
        header.createCell(9).setCellValue("单价");
        header.createCell(10).setCellValue("合计金额");
        header.createCell(11).setCellValue("备注");


        for (int i = 0; i < allGivebackInfo.size(); i++) {
            Row row = sheet.createRow(i + 1);
            GivebackInfo givebackInfo = allGivebackInfo.get(i);
            row.createCell(0).setCellValue("CK" + givebackInfo.getGivebackId());
            row.createCell(1).setCellValue(givebackInfo.getGivebackDate());
            row.createCell(2).setCellValue(givebackInfo.getInnerDepartmentInfo().getDepartmentName());
            row.createCell(3).setCellValue(givebackInfo.getComputerMaterialsInfo().getComputerMaterialsId());
            row.createCell(4).setCellValue(givebackInfo.getComputerMaterialsInfo().getComputerMaterialsName());
            row.createCell(5).setCellValue(givebackInfo.getComputerMaterialsInfo().getComputerMaterialsSpecifications());
            row.createCell(6).setCellValue(givebackInfo.getComputerMaterialsCategory().getCategoryName());
            row.createCell(7).setCellValue(givebackInfo.getComputerMaterialsInfo().getComputerMaterialsUnit());
            row.createCell(8).setCellValue(givebackInfo.getGivebackQuantity());
            row.createCell(11).setCellValue(givebackInfo.getMemo());
        }

        wb.write(response.getOutputStream());
        wb.close();

        return Boolean.TRUE;
    }
}
