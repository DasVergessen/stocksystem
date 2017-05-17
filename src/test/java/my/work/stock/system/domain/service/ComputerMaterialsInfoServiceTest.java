package my.work.stock.system.domain.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import my.work.stock.system.Application;
import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.web.view.SearchComputerMaterialsInfo;
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
@DatabaseSetup({"/data/computer_materials_category.xml", "/data/computer_materials_info.xml"})
public class ComputerMaterialsInfoServiceTest {

    @Autowired
    private ComputerMaterialsInfoService computerMaterialsInfoService;

    @Test
    public void testSearchComputerMaterialsInfo() throws Exception {
        SearchComputerMaterialsInfo search = new SearchComputerMaterialsInfo();
        search.setPageNumber(0);
        search.setPageSize(10);
        search.setCategoryId(1);
        Page<ComputerMaterialsInfo> computerMaterialsInfos = computerMaterialsInfoService.searchComputerMaterialsInfo(search);
        computerMaterialsInfos.forEach(computerMaterialsInfo -> {
            assertThat(computerMaterialsInfo.getComputerMaterialsCategory().getCategoryName(), is("类别一"));
        });
    }
}