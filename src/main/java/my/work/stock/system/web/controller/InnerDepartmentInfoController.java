package my.work.stock.system.web.controller;

import my.work.stock.system.domain.entity.InnerDepartmentInfo;
import my.work.stock.system.domain.service.InnerDepartmentInfoService;
import my.work.stock.system.web.view.SearchInnerDepartmentInfo;
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
@RequestMapping("/innerDepartmentInfo")
public class InnerDepartmentInfoController {
    @Autowired
    private InnerDepartmentInfoService innerDepartmentInfoService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView get() {
        Map<String, String> model = new HashMap<>();
        return new ModelAndView("basedatamanage/innerdepartmentinfo", model);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public void save(@RequestBody InnerDepartmentInfo innerDepartmentInfo) {
        innerDepartmentInfoService.save(innerDepartmentInfo);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody InnerDepartmentInfo innerDepartmentInfo) {
        innerDepartmentInfoService.delete(innerDepartmentInfo);
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Page<InnerDepartmentInfo> page(@RequestBody SearchInnerDepartmentInfo searchInnerDepartmentInfo) {
        return innerDepartmentInfoService.searchInnerDepartmentInfo(searchInnerDepartmentInfo);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<InnerDepartmentInfo> all() {
        return innerDepartmentInfoService.findAllInnerDepartmentInfo();
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public Boolean export(HttpServletResponse response) throws IOException {
        List<InnerDepartmentInfo> allInnerDepartmentInfo = innerDepartmentInfoService.findAllInnerDepartmentInfo();
        response.setContentType("text/csv;charset=GBK");
        String headerKey = "Content-Disposition";
        String csvFileName = "内部部门信息.xlsx";
        String headerValue = String.format("attachment; filename=\"%s\"", new String(csvFileName.getBytes(Charset.forName("GBK")), "ISO-8859-1"));
        response.setHeader(headerKey, headerValue);
        Workbook wb = new XSSFWorkbook();
        Sheet innerDepartmentInfoSheet = wb.createSheet("内部部门信息");

        Row header = innerDepartmentInfoSheet.createRow((short) 0);//头
        header.createCell(0).setCellValue("部门编码");
        header.createCell(1).setCellValue("部门名称");
        header.createCell(2).setCellValue("部门职能");
        header.createCell(3).setCellValue("资产管理员电话");
        header.createCell(4).setCellValue("备注");

        for (int i = 0; i < allInnerDepartmentInfo.size(); i++) {
            Row innerDepartmentInfoSheetRow = innerDepartmentInfoSheet.createRow(i + 1);
            InnerDepartmentInfo innerDepartmentInfo = allInnerDepartmentInfo.get(i);
            innerDepartmentInfoSheetRow.createCell(0).setCellValue(innerDepartmentInfo.getDepartmentId());
            innerDepartmentInfoSheetRow.createCell(1).setCellValue(innerDepartmentInfo.getDepartmentName());
            innerDepartmentInfoSheetRow.createCell(2).setCellValue(innerDepartmentInfo.getDepartmentFunction());
            innerDepartmentInfoSheetRow.createCell(3).setCellValue(innerDepartmentInfo.getMobileOfPropertyAdmin());
            innerDepartmentInfoSheetRow.createCell(4).setCellValue(innerDepartmentInfo.getMemo());
        }

        wb.write(response.getOutputStream());
        wb.close();
        return Boolean.TRUE;
    }
}
