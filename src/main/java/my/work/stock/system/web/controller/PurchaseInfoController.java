package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.PurchaseInfo;
import my.work.stock.system.domain.service.PurchaseInfoService;
import my.work.stock.system.web.view.SearchPurchaseInfo;
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

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@RequestBody PurchaseInfo purchaseInfo) {
        return purchaseInfoService.save(purchaseInfo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestBody PurchaseInfo purchaseInfo) {
        return purchaseInfoService.delete(purchaseInfo);
    }

    @RequestMapping(value = "all", method = RequestMethod.POST)
    public List<PurchaseInfo> all() {
        return purchaseInfoService.findAllPurchaseInfo();
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export(HttpServletResponse response) throws IOException {

        List<PurchaseInfo> allPurchaseInfo = purchaseInfoService.findAllPurchaseInfo();
        response.setContentType("text/csv;charset=GBK");
        String headerKey = "Content-Disposition";
        String csvFileName = "采购入库.xlsx";
        String headerValue = String.format("attachment; filename=\"%s\"", new String(csvFileName.getBytes(Charset.forName("GBK")), "ISO-8859-1"));
        response.setHeader(headerKey, headerValue);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("采购入库");

        Row header = sheet.createRow((short) 0);//头
        header.createCell(0).setCellValue("入库单号");
        header.createCell(1).setCellValue("入库日期");
        header.createCell(2).setCellValue("供应商");
        header.createCell(3).setCellValue("耗材编码");
        header.createCell(4).setCellValue("耗材名称");
        header.createCell(5).setCellValue("耗材规格");
        header.createCell(6).setCellValue("所属类别");
        header.createCell(7).setCellValue("单位");
        header.createCell(8).setCellValue("入库数量");
        header.createCell(9).setCellValue("单价");
        header.createCell(10).setCellValue("合计金额");
        header.createCell(11).setCellValue("备注信息");

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        for (int i = 0; i < allPurchaseInfo.size(); i++) {
            Row row = sheet.createRow(i + 1);
            PurchaseInfo purchaseInfo = allPurchaseInfo.get(i);
            row.createCell(0).setCellValue("RK" + purchaseInfo.getPurchaseId());
            row.createCell(1).setCellValue(purchaseInfo.getPurchaseDate());
            row.createCell(2).setCellValue(purchaseInfo.getSupplierInfo().getSupplierName());
            row.createCell(3).setCellValue(purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsId());
            row.createCell(4).setCellValue(purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsName());
            row.createCell(5).setCellValue(purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsSpecifications());
            row.createCell(6).setCellValue(purchaseInfo.getComputerMaterialsCategory().getCategoryName());
            row.createCell(7).setCellValue(purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsUnit());
            row.createCell(8).setCellValue(purchaseInfo.getPurchaseQuantity());
            row.createCell(9).setCellValue(decimalFormat.format(purchaseInfo.getPurchasePrice() / 100.00));
            row.createCell(10).setCellValue(decimalFormat.format(purchaseInfo.getPurchasePrice() * purchaseInfo.getPurchaseQuantity() / 100.00));
            row.createCell(11).setCellValue(purchaseInfo.getMemo());
        }

        wb.write(response.getOutputStream());
        wb.close();

        return Boolean.TRUE;
    }
}
