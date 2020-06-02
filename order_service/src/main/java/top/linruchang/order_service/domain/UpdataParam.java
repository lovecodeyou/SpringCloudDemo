package top.linruchang.order_service.domain;

import java.util.List;

/**
 * @Classname UpdataParam
 * @Description
 * @Date 2020/3/16 18:04
 * @Created by lrc
 */
public class UpdataParam {

    Integer user_id;
    List<OmsShoppingCart> carts;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<OmsShoppingCart> getCarts() {
        return carts;
    }

    public void setCarts(List<OmsShoppingCart> carts) {
        this.carts = carts;
    }

    @Override
    public String toString() {
        return "UpdataParam{" +
                "user_id=" + user_id +
                ", carts=" + carts +
                '}';
    }
}
