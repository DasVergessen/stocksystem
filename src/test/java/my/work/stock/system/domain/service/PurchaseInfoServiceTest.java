package my.work.stock.system.domain.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import my.work.stock.system.Application;
import my.work.stock.system.domain.entity.PurchaseInfo;
import my.work.stock.system.web.view.SearchPurchaseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup({"/data/computer_materials_category.xml", "/data/computer_materials_info.xml", "/data/inner_department_info.xml", "/data/supplier_info.xml", "/data/purchase_info.xml", "/data/receive_info.xml"})
public class PurchaseInfoServiceTest {

    @Autowired
    private PurchaseInfoService purchaseInfoService;

    @Test
    public void testSearchSupplierInfo() throws Exception {
        SearchPurchaseInfo searchSupplierInfo = new SearchPurchaseInfo();
        searchSupplierInfo.setPageSize(10);
        searchSupplierInfo.setPageNumber(0);
        searchSupplierInfo.setSupplierId(1);
        Page<PurchaseInfo> purchaseInfos = purchaseInfoService.searchPurchaseInfo(searchSupplierInfo);
        purchaseInfos.forEach(purchaseInfo -> {
            assertThat(purchaseInfo.getSupplierInfo().getSupplierContacts(), is("王哈哈"));
        });
    }
}