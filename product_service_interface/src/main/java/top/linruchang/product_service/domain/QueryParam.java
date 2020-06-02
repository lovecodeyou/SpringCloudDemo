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
