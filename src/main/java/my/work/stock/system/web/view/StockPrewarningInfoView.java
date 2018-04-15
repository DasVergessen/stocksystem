package my.work.stock.system.web.view;

public class StockPrewarningInfoView {
    private Integer categoryId;
    private String categoryName;
    private Integer prewarningStock;
    private Boolean warning = false;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getPrewarningStock() {
        return prewarningStock;
    }

    public void setPrewarningStock(Integer prewarningStock) {
        this.prewarningStock = prewarningStock;
    }

    public Boolean getWarning() {
        return warning;
    }

    public void setWarning(Boolean warning) {
        this.warning = warning;
    }
}
