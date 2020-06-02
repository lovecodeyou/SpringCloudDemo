package top.linruchang.order_service.domain;

/**
 * @Classname DeleteParam
 * @Description
 * @Date 2020/3/16 22:26
 * @Created by lrc
 */
public class DeleteParam {

    Integer user_id;
    Integer[] product_ids;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer[] getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(Integer[] product_ids) {
        this.product_ids = product_ids;
    }
}
