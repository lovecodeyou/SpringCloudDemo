package top.linruchang.order_service.service;

import com.github.pagehelper.PageInfo;
import top.linruchang.domain.QueryParam;
import top.linruchang.order_service.domain.OmsShoppingCart;

import java.util.List;

/**
 * @Classname CartService
 * @Description
 * @Date 2020/3/14 17:53
 * @Created by lrc
 */
public interface CartService {

    public PageInfo<OmsShoppingCart> findByUserId(QueryParam queryParam, Integer user_id);

    public boolean updateCart(OmsShoppingCart omsShoppingCart);

    boolean addCart(OmsShoppingCart omsShoppingCart);

    boolean deleteCarts(Integer user_id, Integer[] product_ids);
}
