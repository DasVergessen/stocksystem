package my.work.stock.system.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class StockPrewarningInfo implements Serializable {
    @Id
    private Integer categoryId;

    @Column(length = 11, nullable = false)
    private Integer prewarningStock;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrewarningStock() {
        return prewarningStock;
    }

    public void setPrewarningStock(Integer prewarningStock) {
        this.prewarningStock = prewarningStock;
    }
}


