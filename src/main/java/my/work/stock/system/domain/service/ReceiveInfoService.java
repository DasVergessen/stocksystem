package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.ComputerMaterialsCategory;
import my.work.stock.system.domain.entity.ComputerMaterialsInfo;
import my.work.stock.system.domain.entity.InnerDepartmentInfo;
import my.work.stock.system.domain.entity.ReceiveInfo;
import my.work.stock.system.domain.repository.ReceiveInfoRepository;
import my.work.stock.system.web.view.SearchReceiveInfo;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceiveInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveInfoService.class);

    @Autowired
    private ReceiveInfoRepository receiveInfoRepository;
    @Autowired
    private ComputerMaterialsInfoService computerMaterialsInfoService;

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

    @Transactional
    public String save(ReceiveInfo receiveInfo) {
        Integer receiveId = receiveInfo.getReceiveId();
        ComputerMaterialsInfo computerMaterialsInfo1 = receiveInfo.getComputerMaterialsInfo();
        InnerDepartmentInfo innerDepartmentInfo = receiveInfo.getInnerDepartmentInfo();
        if (computerMaterialsInfo1 == null || innerDepartmentInfo == null) {
            return "未选择耗材或领用部门!";
        }
        Integer receiveQuantity = receiveInfo.getReceiveQuantity();
        Integer used = sumSameMaterialsAndDepartmentAtCurrentMonth(computerMaterialsInfo1.getComputerMaterialsId(), innerDepartmentInfo.getDepartmentId());
        if (receiveQuantity + used > 4) {
            return "超过领用上限!";
        }
        if (receiveId != null) {
            ReceiveInfo old = receiveInfoRepository.findOne(receiveId);
            Integer oldQuantity = old.getReceiveQuantity();
            Integer computerMaterialsId = receiveInfo.getComputerMaterialsInfo().getComputerMaterialsId();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoService.findOne(computerMaterialsId);
                if (computerMaterialsInfo != null && receiveQuantity != null && oldQuantity != null) {
                    Integer quantity = computerMaterialsInfo.getQuantity();
                    if (quantity != null && quantity - receiveQuantity + oldQuantity >= 0) {
                        computerMaterialsInfo.setQuantity(quantity - receiveQuantity + oldQuantity);
                        computerMaterialsInfoService.save(computerMaterialsInfo);
                    } else {
                        return "领用库存不够,领用失败!";
                    }
                } else {
                    return "领用库存为空或者找不到对应的耗材!";
                }
            }
        } else {
            Integer computerMaterialsId = receiveInfo.getComputerMaterialsInfo().getComputerMaterialsId();
            if (computerMaterialsId != null) {
                ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoService.findOne(computerMaterialsId);
                if (computerMaterialsInfo != null && receiveQuantity != null) {
                    Integer quantity = computerMaterialsInfo.getQuantity();
                    if (quantity != null && quantity - receiveQuantity >= 0) {
                        computerMaterialsInfo.setQuantity(quantity - receiveQuantity);
                        computerMaterialsInfoService.save(computerMaterialsInfo);
                    } else {
                        return "领用库存不够,领用失败!";
                    }
                } else {
                    return "领用库存为空或者找不到对应的耗材!";
                }
            }
        }
        receiveInfoRepository.save(receiveInfo);
        return "保存成功!";
    }

    private Integer sumSameMaterialsAndDepartmentAtCurrentMonth(Integer materialsId, Integer departmentId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String month = simpleDateFormat.format(new Date());
        try {
            Object[] objects = receiveInfoRepository.sumSameMaterialsAndDepartmentAtCurrentMonth(materialsId, departmentId, month);
            Object object = objects[0];
            BigDecimal bigDecimal = (BigDecimal) object;
            return bigDecimal.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Transactional
    public String delete(ReceiveInfo receiveInfo) {
        Integer computerMaterialsId = receiveInfo.getComputerMaterialsInfo().getComputerMaterialsId();
        Integer receiveQuantity = receiveInfo.getReceiveQuantity();
        if (computerMaterialsId != null) {
            ComputerMaterialsInfo computerMaterialsInfo = computerMaterialsInfoService.findOne(computerMaterialsId);
            if (computerMaterialsInfo != null && receiveQuantity != null) {
                Integer quantity = computerMaterialsInfo.getQuantity();
                if (quantity != null) {//删除领用记录,恢复库存
                    computerMaterialsInfo.setQuantity(quantity + receiveQuantity);
                    computerMaterialsInfoService.save(computerMaterialsInfo);
                } else {
                    return "删除失败!";
                }
            } else {
                return "领用库存为空或者找不到对应的耗材!";
            }
        }
        receiveInfoRepository.delete(receiveInfo);
        return "删除成功";
    }
}
