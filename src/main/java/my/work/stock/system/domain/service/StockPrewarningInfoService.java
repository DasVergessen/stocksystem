package my.work.stock.system.domain.service;

import my.work.stock.system.domain.entity.StockPrewarningInfo;
import my.work.stock.system.domain.repository.StockPrewarningInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockPrewarningInfoService {
    private static final Logger logger = LoggerFactory.getLogger(StockPrewarningInfoService.class);

    @Autowired
    private StockPrewarningInfoRepository stockPrewarningInfoRepository;

    @Transactional
    public String save(StockPrewarningInfo stockPrewarningInfo) {
        stockPrewarningInfoRepository.save(stockPrewarningInfo);
        return "保存成功!";
    }

    @Transactional
    public String delete(Integer categoryId) {
        stockPrewarningInfoRepository.delete(categoryId);
        return "删除成功!";
    }

    public List<StockPrewarningInfo> all() {
        return (List<StockPrewarningInfo>) stockPrewarningInfoRepository.findAll();
    }
}
