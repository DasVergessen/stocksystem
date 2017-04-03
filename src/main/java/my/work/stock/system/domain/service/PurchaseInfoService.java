package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.domain.entity.PurchaseInfo;
import my.work.stock.system.domain.entity.SupplierInfo;
import my.work.stock.system.domain.repository.PurchaseInfoRepository;
import my.work.stock.system.web.view.SearchPurchaseInfo;
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
public class PurchaseInfoService {
    @Autowired
    private PurchaseInfoRepository purchaseInfoRepository;

    public Page<PurchaseInfo> searchPurchaseInfo(SearchPurchaseInfo searchPurchaseInfo) {
        Specification<PurchaseInfo> specification = getWhereClause(searchPurchaseInfo);
        PageRequest pageable = new PageRequest(searchPurchaseInfo.getPageNumber(), searchPurchaseInfo.getPageSize(), new Sort(Sort.Direction.DESC, "purchaseId"));
        return purchaseInfoRepository.findAll(specification, pageable);
    }

    public List<PurchaseInfo> findAllPurchaseInfo() {
        return (List<PurchaseInfo>) purchaseInfoRepository.findAll();
    }

    private Specification<PurchaseInfo> getWhereClause(SearchPurchaseInfo searchPurchaseInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (StringUtils.isNotEmpty(searchPurchaseInfo.getPurchaseDate())) {
                and.add(criteriaBuilder.equal(root.get("purchaseDate").as(String.class), searchPurchaseInfo.getPurchaseDate()));
            }
            Integer supplierId = searchPurchaseInfo.getSupplierId();
            if (supplierId != null) {
                SupplierInfo supplierInfo = new SupplierInfo();
                supplierInfo.setSupplierId(supplierId);
                and.add(criteriaBuilder.equal(root.get("supplierInfo").as(SupplierInfo.class), supplierInfo));
            }
            Integer computerMaterialsId = searchPurchaseInfo.getComputerMaterialsId();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = new ComputerMaterialsInfo();
                computerMaterialsInfo.setComputerMaterialsId(computerMaterialsId);
                and.add(criteriaBuilder.equal(root.get("computerMaterialsInfo").as(ComputerMaterialsInfo.class), computerMaterialsInfo));
            }
            Integer categoryId = searchPurchaseInfo.getCategoryId();
            if (categoryId != null) {
                ComputerMaterialsCategory computerMaterialsCategory = new ComputerMaterialsCategory();
                computerMaterialsCategory.setCategoryId(categoryId);
                and.add(criteriaBuilder.equal(root.get("computerMaterialsCategory").as(ComputerMaterialsCategory.class), computerMaterialsCategory));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }
}
