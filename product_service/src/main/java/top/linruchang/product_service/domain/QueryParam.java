package top.linruchang.product_service.domain;

/**
 * @Classname QueryParam
 * @Description
 * @Date 2020/3/13 20:45
 * @Created by lrc
 */
public class QueryParam {

    String productName;

    String productIds;

    Integer productId;

    Integer pageNum;
    Integer pageSize;

    Integer type_id;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "QueryParam{" +
                "productName='" + productName + '\'' +
                ", productIds='" + productIds + '\'' +
                '}';
    }
}
