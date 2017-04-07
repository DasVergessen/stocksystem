package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.domain.repository.ComputerMaterialsInfoRepository;
import my.work.stock.system.web.view.SearchComputerMaterialsInfo;
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
public class ComputerMaterialsInfoService {
    @Autowired
    private ComputerMaterialsInfoRepository computerMaterialsInfoRepository;

    public Page<ComputerMaterialsInfo> searchComputerMaterialsInfo(SearchComputerMaterialsInfo searchComputerMaterialsInfo) {
        Specification<ComputerMaterialsInfo> specification = getWhereClause(searchComputerMaterialsInfo);
        PageRequest pageable = new PageRequest(searchComputerMaterialsInfo.getPageNumber(), searchComputerMaterialsInfo.getPageSize(), new Sort(Sort.Direction.DESC, "computerMaterialsId"));
        return computerMaterialsInfoRepository.findAll(specification, pageable);
    }

    public List<ComputerMaterialsInfo> findAllComputerMaterialsInfo() {
        return (List<ComputerMaterialsInfo>) computerMaterialsInfoRepository.findAll();
    }

    private Specification<ComputerMaterialsInfo> getWhereClause(SearchComputerMaterialsInfo searchComputerMaterialsInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (searchComputerMaterialsInfo.getComputerMaterialsId() != null) {
                and.add(criteriaBuilder.equal(root.get("computerMaterialsId").as(Integer.class), searchComputerMaterialsInfo.getComputerMaterialsId()));
            }
            if (searchComputerMaterialsInfo.getCategoryId() != null) {
                ComputerMaterialsCategory computerMaterialsCategory = new ComputerMaterialsCategory();
                computerMaterialsCategory.setCategoryId(searchComputerMaterialsInfo.getCategoryId());
                and.add(criteriaBuilder.equal(root.get("computerMaterialsCategory").as(ComputerMaterialsCategory.class), computerMaterialsCategory));
            }
            if (StringUtils.isNotEmpty(searchComputerMaterialsInfo.getComputerMaterialsName())) {
                and.add(criteriaBuilder.like(root.get("computerMaterialsName").as(String.class), "%" + searchComputerMaterialsInfo.getComputerMaterialsName() + "%"));
            }
            if (StringUtils.isNotEmpty(searchComputerMaterialsInfo.getComputerMaterialsSpecifications())) {
                and.add(criteriaBuilder.like(root.get("computerMaterialsSpecifications").as(String.class), "%" + searchComputerMaterialsInfo.getComputerMaterialsSpecifications() + "%"));
            }
            if (StringUtils.isNotEmpty(searchComputerMaterialsInfo.getComputerMaterialsUnit())) {
                and.add(criteriaBuilder.like(root.get("computerMaterialsUnit").as(String.class), "%" + searchComputerMaterialsInfo.getComputerMaterialsUnit() + "%"));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }

    public void save(ComputerMaterialsInfo computerMaterialsInfo) {
        computerMaterialsInfoRepository.save(computerMaterialsInfo);
    }

    public void delete(ComputerMaterialsInfo computerMaterialsInfo) {
        computerMaterialsInfoRepository.delete(computerMaterialsInfo);
    }
}
