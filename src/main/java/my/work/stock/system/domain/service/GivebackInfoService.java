package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.*;
import my.work.stock.system.domain.repository.GivebackInfoRepository;
import my.work.stock.system.web.view.SearchGivebackInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GivebackInfoService {
    private static final Logger logger = LoggerFactory.getLogger(GivebackInfoService.class);

    @Autowired
    private GivebackInfoRepository givebackInfoRepository;

    public Page<GivebackInfo> searchGivebackInfo(SearchGivebackInfo searchGivebackInfo) {
        Specification<GivebackInfo> specification = getWhereClause(searchGivebackInfo);
        PageRequest pageable = new PageRequest(searchGivebackInfo.getPageNumber(), searchGivebackInfo.getPageSize(), new Sort(Sort.Direction.DESC, "givebackId"));
        return givebackInfoRepository.findAll(specification, pageable);
    }

    public List<GivebackInfo> findAllGivebackInfo() {
        return (List<GivebackInfo>) givebackInfoRepository.findAll();
    }

    private Specification<GivebackInfo> getWhereClause(SearchGivebackInfo searchGivebackInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (StringUtils.isNotEmpty(searchGivebackInfo.getGivebackDate())) {
                and.add(criteriaBuilder.equal(root.get("givebackDate").as(String.class), searchGivebackInfo.getGivebackDate()));
            }
            if (StringUtils.isNotEmpty(searchGivebackInfo.getGivebackPerson())) {
                and.add(criteriaBuilder.equal(root.get("givebackPerson").as(String.class), searchGivebackInfo.getGivebackPerson()));
            }
            Integer departmentId = searchGivebackInfo.getDepartmentId();
            if (departmentId != null) {
                InnerDepartmentInfo innerDepartmentInfo = new InnerDepartmentInfo();
                innerDepartmentInfo.setDepartmentId(departmentId);
                and.add(criteriaBuilder.equal(root.get("innerDepartmentInfo").as(InnerDepartmentInfo.class), innerDepartmentInfo));
            }
            Integer computerMaterialsId = searchGivebackInfo.getComputerMaterialsId();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = new ComputerMaterialsInfo();
                computerMaterialsInfo.setComputerMaterialsId(computerMaterialsId);
                and.add(criteriaBuilder.equal(root.get("computerMaterialsInfo").as(ComputerMaterialsInfo.class), computerMaterialsInfo));
            }
            Integer categoryId = searchGivebackInfo.getCategoryId();
            if (categoryId != null) {
                ComputerMaterialsCategory computerMaterialsCategory = new ComputerMaterialsCategory();
                computerMaterialsCategory.setCategoryId(categoryId);
                and.add(criteriaBuilder.equal(root.get("computerMaterialsCategory").as(ComputerMaterialsCategory.class), computerMaterialsCategory));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }

    @Transactional
    public String save(GivebackInfo givebackInfo) {
        givebackInfoRepository.save(givebackInfo);
        return "保存成功!";
    }

    @Transactional
    public String delete(GivebackInfo givebackInfo) {
        givebackInfoRepository.delete(givebackInfo);
        return "删除成功!";
    }
}
