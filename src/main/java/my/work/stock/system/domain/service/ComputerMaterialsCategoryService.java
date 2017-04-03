package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.repository.ComputerMaterialsCategoryRepository;
import my.work.stock.system.web.view.SearchComputerMaterialsCategory;
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
public class ComputerMaterialsCategoryService {
    @Autowired
    private ComputerMaterialsCategoryRepository computerMaterialsCategoryRepository;

    public Page<ComputerMaterialsCategory> searchComputerMaterialsCategory(SearchComputerMaterialsCategory searchComputerMaterialsCategory) {
        Specification<ComputerMaterialsCategory> specification = getWhereClause(searchComputerMaterialsCategory);
        PageRequest pageable = new PageRequest(searchComputerMaterialsCategory.getPageNumber(), searchComputerMaterialsCategory.getPageSize(), new Sort(Sort.Direction.DESC, "categoryId"));
        return computerMaterialsCategoryRepository.findAll(specification, pageable);
    }

    public List<ComputerMaterialsCategory> findAllInnerDepartmentInfo() {
        return (List<ComputerMaterialsCategory>) computerMaterialsCategoryRepository.findAll();
    }

    private Specification<ComputerMaterialsCategory> getWhereClause(SearchComputerMaterialsCategory searchComputerMaterialsCategory) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (searchComputerMaterialsCategory.getCategoryId() != null) {
                and.add(criteriaBuilder.equal(root.get("categoryId").as(Integer.class), searchComputerMaterialsCategory.getCategoryId()));
            }
            if (StringUtils.isNotEmpty(searchComputerMaterialsCategory.getCategoryName())) {
                and.add(criteriaBuilder.like(root.get("categoryName").as(String.class), "%" + searchComputerMaterialsCategory.getCategoryName() + "%"));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }
}
