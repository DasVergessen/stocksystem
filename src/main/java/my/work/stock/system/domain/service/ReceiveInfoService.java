package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.domain.entity.InnerDepartmentInfo;
import my.work.stock.system.domain.entity.ReceiveInfo;
import my.work.stock.system.domain.repository.ReceiveInfoRepository;
import my.work.stock.system.web.view.SearchReceiveInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiveInfoService {
    @Autowired
    private ReceiveInfoRepository receiveInfoRepository;

    public Page<ReceiveInfo> searchReceiveInfo(SearchReceiveInfo searchReceiveInfo) {
        Specification<ReceiveInfo> specification = getWhereClause(searchReceiveInfo);
        PageRequest pageable = new PageRequest(searchReceiveInfo.getPageNumber(), searchReceiveInfo.getPageSize(), new Sort(Sort.Direction.DESC, "receiveId"));
        return receiveInfoRepository.findAll(specification, pageable);
    }

    public List<ReceiveInfo> findAllReceiveInfo() {
        return (List<ReceiveInfo>) receiveInfoRepository.findAll();
    }

    private Specification<ReceiveInfo> getWhereClause(SearchReceiveInfo searchReceiveInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (StringUtils.isNotEmpty(searchReceiveInfo.getReceiveDate())) {
                and.add(criteriaBuilder.equal(root.get("receiveDate").as(String.class), searchReceiveInfo.getReceiveDate()));
            }
            Integer departmentId = searchReceiveInfo.getDepartmentId();
            if (departmentId != null) {
                InnerDepartmentInfo innerDepartmentInfo = new InnerDepartmentInfo();
                innerDepartmentInfo.setDepartmentId(departmentId);
                and.add(criteriaBuilder.equal(root.get("innerDepartmentInfo").as(InnerDepartmentInfo.class), innerDepartmentInfo));
            }
            Integer computerMaterialsId = searchReceiveInfo.getComputerMaterialsId();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = new ComputerMaterialsInfo();
                computerMaterialsInfo.setComputerMaterialsId(computerMaterialsId);
                and.add(criteriaBuilder.equal(root.get("computerMaterialsInfo").as(ComputerMaterialsInfo.class), computerMaterialsInfo));
            }
            Integer categoryId = searchReceiveInfo.getCategoryId();
            if (categoryId != null) {
                ComputerMaterialsCategory computerMaterialsCategory = new ComputerMaterialsCategory();
                computerMaterialsCategory.setCategoryId(categoryId);
                and.add(criteriaBuilder.equal(root.get("computerMaterialsCategory").as(ComputerMaterialsCategory.class), computerMaterialsCategory));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }

    public ReceiveInfo save(ReceiveInfo receiveInfo) {
        return receiveInfoRepository.save(receiveInfo);
    }

    public void delete(ReceiveInfo receiveInfo) {
        receiveInfoRepository.delete(receiveInfo);
    }
}
