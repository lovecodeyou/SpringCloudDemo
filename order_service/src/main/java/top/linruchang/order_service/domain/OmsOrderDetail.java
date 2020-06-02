package top.linruchang.order_service.domain;

import top.linruchang.product_service.domain.Product;

/**
 * @Classname OmsOrderDetail
 * @Description
 * @Date 2020/3/21 20:30
 * @Created by lrc
 */
public class OmsOrderDetail {


    Integer id;
    Integer order_id;
    Integer product_id;
    Integer num;
    Integer total_money;
    Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Integer total_money) {
        this.total_money = total_money;
    }

    @Override
    public String toString() {
        return "OmsOrderDetail{" +
                "id=" + id +
                ", order_id='" + order_id + '\'' +
                ", product_id=" + product_id +
                ", num=" + num +
                ", total_money=" + total_money +
                ", product=" + product +
                '}';
    }
}
