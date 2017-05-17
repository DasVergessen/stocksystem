package my.work.stock.system.domain.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import my.work.stock.system.Application;
import my.work.stock.system.domain.entity.ReceiveInfo;
import my.work.stock.system.web.view.SearchReceiveInfo;
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
@DatabaseSetup({"/data/computer_materials_category.xml", "/data/computer_materials_info.xml", "/data/inner_department_info.xml", "/data/supplier_info.xml", "/data/receive_info.xml", "/data/purchase_info.xml"})
public class ReceiveInfoServiceTest {

    @Autowired
    private ReceiveInfoService receiveInfoService;

    @Test
    public void testSearchReceiveInfo() throws Exception {

        SearchReceiveInfo searchReceiveInfo = new SearchReceiveInfo();
        searchReceiveInfo.setDepartmentId(1);
        searchReceiveInfo.setPageNumber(0);
        searchReceiveInfo.setPageSize(10);
        Page<ReceiveInfo> receiveInfos = receiveInfoService.searchReceiveInfo(searchReceiveInfo);
        receiveInfos.forEach(receiveInfo -> {
            assertThat(receiveInfo.getInnerDepartmentInfo().getDepartmentName(), is("销售部"));
        });
    }
}