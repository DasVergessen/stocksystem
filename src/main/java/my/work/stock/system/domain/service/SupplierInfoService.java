package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.SupplierInfo;
import my.work.stock.system.domain.repository.SupplierInfoRepository;
import my.work.stock.system.web.view.SearchSupplierInfo;
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
public class SupplierInfoService {
    @Autowired
    private SupplierInfoRepository supplierInfoRepository;

    public Page<SupplierInfo> searchSupplierInfo(SearchSupplierInfo searchSupplierInfo) {
        Specification<SupplierInfo> specification = getWhereClause(searchSupplierInfo);
        PageRequest pageable = new PageRequest(searchSupplierInfo.getPageNumber(), searchSupplierInfo.getPageSize(), new Sort(Sort.Direction.DESC, "supplierId"));
        return supplierInfoRepository.findAll(specification, pageable);
    }

    public List<SupplierInfo> findAllSupplierInfo() {
        return (List<SupplierInfo>) supplierInfoRepository.findAll();
    }

    private Specification<SupplierInfo> getWhereClause(SearchSupplierInfo searchSupplierInfo) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> and = new ArrayList<>();
            if (searchSupplierInfo.getSupplierId() != null) {
                and.add(criteriaBuilder.equal(root.get("supplierId").as(Integer.class), searchSupplierInfo.getSupplierId()));
            }
            if (StringUtils.isNotEmpty(searchSupplierInfo.getSupplierAddress())) {
                and.add(criteriaBuilder.like(root.get("supplierAddress").as(String.class), "%" + searchSupplierInfo.getSupplierAddress() + "%"));
            }
            if (StringUtils.isNotEmpty(searchSupplierInfo.getSupplierContacts())) {
                and.add(criteriaBuilder.like(root.get("supplierContacts").as(String.class), "%" + searchSupplierInfo.getSupplierContacts() + "%"));
            }
            if (StringUtils.isNotEmpty(searchSupplierInfo.getSupplierName())) {
                and.add(criteriaBuilder.like(root.get("supplierName").as(String.class), "%" + searchSupplierInfo.getSupplierName() + "%"));
            }
            if (StringUtils.isNotEmpty(searchSupplierInfo.getSupplierPhoneNumber())) {
                and.add(criteriaBuilder.like(root.get("supplierPhoneNumber").as(String.class), "%" + searchSupplierInfo.getSupplierPhoneNumber() + "%"));
            }
            return criteriaQuery.where(criteriaBuilder.and(and.toArray(new Predicate[0]))).getRestriction();
        };
    }

    public void save(SupplierInfo supplierInfo) {
        supplierInfoRepository.save(supplierInfo);
    }

    public void delete(SupplierInfo supplierInfo) {
        supplierInfoRepository.delete(supplierInfo);
    }
}
