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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseInfoService {
    @Autowired
    private PurchaseInfoRepository purchaseInfoRepository;
    @Autowired
    private ComputerMaterialsInfoService computerMaterialsInfoService;

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

    @Transactional
    public String save(PurchaseInfo purchaseInfo) {
        Integer purchaseId = purchaseInfo.getPurchaseId();
        if (purchaseId != null) {//修改
            PurchaseInfo old = purchaseInfoRepository.findOne(purchaseId);
            Integer oldQuantity = old.getPurchaseQuantity();
            Integer computerMaterialsId = purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsId();
            Integer purchaseQuantity = purchaseInfo.getPurchaseQuantity();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoService.findOne(computerMaterialsId);
                if (computerMaterialsInfo != null && purchaseQuantity != null && oldQuantity != null) {
                    Integer quantity = computerMaterialsInfo.getQuantity();
                    if (quantity + purchaseQuantity - oldQuantity < 0) {
                        return "库存不足以抵扣修改后的数量!";
                    }
                    computerMaterialsInfo.setQuantity(quantity + purchaseQuantity - oldQuantity);
                    computerMaterialsInfoService.save(computerMaterialsInfo);
                } else {
                    return "入库库存为空或者找不到对应的耗材!";
                }
            }
        } else {//新增
            Integer computerMaterialsId = purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsId();
            Integer purchaseQuantity = purchaseInfo.getPurchaseQuantity();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoService.findOne(computerMaterialsId);
                if (computerMaterialsInfo != null && purchaseQuantity != null) {
                    Integer quantity = computerMaterialsInfo.getQuantity();
                    if (quantity == null) {
                        quantity = 0;
                    }
                    computerMaterialsInfo.setQuantity(quantity + purchaseQuantity);
                    computerMaterialsInfoService.save(computerMaterialsInfo);
                } else {
                    return "入库库存为空或者找不到对应的耗材!";
                }
            }
        }
        purchaseInfoRepository.save(purchaseInfo);
        return "入库成功!";
    }


    @Transactional
    public String delete(PurchaseInfo purchaseInfo) {
        Integer computerMaterialsId = purchaseInfo.getComputerMaterialsInfo().getComputerMaterialsId();
        Integer purchaseQuantity = purchaseInfo.getPurchaseQuantity();
        if (computerMaterialsId != null) {
            ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoService.findOne(computerMaterialsId);
            if (computerMaterialsInfo != null && purchaseQuantity != null) {
                Integer quantity = computerMaterialsInfo.getQuantity();
                if (quantity != null && quantity - purchaseQuantity >= 0) {//删除入库记录,恢复库存
                    computerMaterialsInfo.setQuantity(quantity - purchaseQuantity);
                    computerMaterialsInfoService.save(computerMaterialsInfo);
                } else {
                    return "删除入库记录失败,库存不足!";
                }
            } else {
                return "入库库存为空或者找不到对应的耗材!";
            }
        }
        purchaseInfoRepository.delete(purchaseInfo);
        return "删除成功!";
    }
}
