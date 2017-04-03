package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.InnerDepartmentInfo;
import my.work.stock.system.domain.repository.InnerDepartmentInfoRepository;
import my.work.stock.system.web.view.SearchInnerDepartmentInfo;
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
public class InnerDepartmentInfoService {
    @Autowired
    private InnerDepartmentInfoRepository innerDepartmentInfoRepository;

    public Page<InnerDepartmentInfo> searchInnerDepartmentInfo(SearchInnerDepartmentInfo searchInnerDepartmentInfo) {
        Specification<InnerDepartmentInfo> specification = getWhereClause(searchInnerDepartmentInfo);
        PageRequest pageable = new PageRequest(searchInnerDepartmentInfo.getPageNumber(), searchInnerDepartmentInfo.getPageSize(), new Sort(Sort.Direction.DESC, "departmentId"));
        return innerDepartmentInfoRepository.findAll(specification, pageable);
    }

    public List<InnerDepartmentInfo> findAllInnerDepartmentInfo() {
        return (List<InnerDepartmentInfo>) innerDepartmentInfoRepository.findAll();
    }

    private Specification<InnerDepartmentInfo> getWhereClause(SearchInnerDepartmentInfo searchInnerDepartmentInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (searchInnerDepartmentInfo.getDepartmentId() != null) {
                and.add(criteriaBuilder.equal(root.get("departmentId").as(Integer.class), searchInnerDepartmentInfo.getDepartmentId()));
            }
            if (StringUtils.isNotEmpty(searchInnerDepartmentInfo.getDepartmentName())) {
                and.add(criteriaBuilder.like(root.get("departmentName").as(String.class), "%" + searchInnerDepartmentInfo.getDepartmentName() + "%"));
            }
            if (StringUtils.isNotEmpty(searchInnerDepartmentInfo.getDepartmentFunction())) {
                and.add(criteriaBuilder.like(root.get("departmentFunction").as(String.class), "%" + searchInnerDepartmentInfo.getDepartmentFunction() + "%"));
            }
            if (StringUtils.isNotEmpty(searchInnerDepartmentInfo.getMobileOfPropertyAdmin())) {
                and.add(criteriaBuilder.like(root.get("mobileOfPropertyAdmin").as(String.class), "%" + searchInnerDepartmentInfo.getMobileOfPropertyAdmin() + "%"));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }
}
