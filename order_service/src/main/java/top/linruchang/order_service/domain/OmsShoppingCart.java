package top.linruchang.order_service.domain;

import top.linruchang.product_service.domain.Product;

import java.util.Date;

/**
 * @Classname OmsShoppingCart
 * @Description
 * @Date 2020/3/14 17:46
 * @Created by lrc
 */
public class OmsShoppingCart {


    Integer id;
    Integer user_id;
    Integer product_id;
    Integer num;
    Date create_time;
    Date update_time;
    Integer flag;

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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "OmsShoppingCart{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", num=" + num +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", flag=" + flag +
                '}';
    }
}
