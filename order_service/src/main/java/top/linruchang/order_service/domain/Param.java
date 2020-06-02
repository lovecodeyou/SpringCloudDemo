package top.linruchang.order_service.domain;

import java.util.Arrays;

/**
 * @Classname Param
 * @Description
 * @Date 2020/3/21 20:45
 * @Created by lrc
 */
public class Param {

    Integer user_id;
    Integer address_id;
    Integer[] product_ids;
    Integer[] productNums;
    boolean pay_result;
    Integer total_money;



    public Integer getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Integer total_money) {
        this.total_money = total_money;
    }

    public Integer[] getProductNums() {
        return productNums;
    }

    public void setProductNums(Integer[] productNums) {
        this.productNums = productNums;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Integer[] getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(Integer[] product_ids) {
        this.product_ids = product_ids;
    }

    public boolean isPay_result() {
        return pay_result;
    }

    public void setPay_result(boolean pay_result) {
        this.pay_result = pay_result;
    }

    @Override
    public String toString() {
        return "Param{" +
                "user_id=" + user_id +
                ", address_id=" + address_id +
                ", product_ids=" + Arrays.toString(product_ids) +
                ", productNums=" + Arrays.toString(productNums) +
                ", pay_result=" + pay_result +
                ", total_money=" + total_money +
                '}';
    }
}
