package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.SupplierInfo;
import my.work.stock.system.domain.service.SupplierInfoService;
import my.work.stock.system.web.view.SearchSupplierInfo;
import org.apache.poi.ss.usermodel.CreationHelper;
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
@RequestMapping("/supplierInfo")
public class SupplierInfoController {
    @Autowired
    private SupplierInfoService supplierInfoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView get() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("basedatamanage/supplierinfo", model);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@RequestBody SupplierInfo supplierInfo) {
        supplierInfoService.save(supplierInfo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody SupplierInfo supplierInfo) {
        supplierInfoService.delete(supplierInfo);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Page<SupplierInfo> page(@RequestBody SearchSupplierInfo searchSupplierInfo) {
        return supplierInfoService.searchSupplierInfo(searchSupplierInfo);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<SupplierInfo> all() {
        return supplierInfoService.findAllSupplierInfo();
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export(HttpServletResponse response) throws IOException {
        List<SupplierInfo> allSupplierInfo = supplierInfoService.findAllSupplierInfo();
        response.setContentType("text/csv;charset=GBK");
        String headerKey = "Content-Disposition";
        String csvFileName = "供应商信息.xlsx";
        String headerValue = String.format("attachment; filename=\"%s\"", new String(csvFileName.getBytes(Charset.forName("GBK")), "ISO-8859-1"));
        response.setHeader(headerKey, headerValue);
        Workbook wb = new XSSFWorkbook();
        Sheet supplierInfoSheet = wb.createSheet("供应商信息");

        Row header = supplierInfoSheet.createRow((short) 0);//头
        header.createCell(0).setCellValue("供应商编码");
        header.createCell(1).setCellValue("供应商名称");
        header.createCell(2).setCellValue("供应商地址");
        header.createCell(3).setCellValue("供应商电话");
        header.createCell(4).setCellValue("供应商联系人");
        header.createCell(5).setCellValue("备注");

        for (int i = 0; i < allSupplierInfo.size(); i++) {
            Row supplierInfoSheetRow = supplierInfoSheet.createRow(i + 1);
            SupplierInfo supplierInfo = allSupplierInfo.get(i);
            supplierInfoSheetRow.createCell(0).setCellValue(supplierInfo.getSupplierId());
            supplierInfoSheetRow.createCell(1).setCellValue(supplierInfo.getSupplierName());
            supplierInfoSheetRow.createCell(2).setCellValue(supplierInfo.getSupplierAddress());
            supplierInfoSheetRow.createCell(3).setCellValue(supplierInfo.getSupplierPhoneNumber());
            supplierInfoSheetRow.createCell(4).setCellValue(supplierInfo.getSupplierContacts());
            supplierInfoSheetRow.createCell(5).setCellValue(supplierInfo.getSupplierRemark());
        }

        wb.write(response.getOutputStream());
        wb.close();
        return Boolean.TRUE;
    }
}
