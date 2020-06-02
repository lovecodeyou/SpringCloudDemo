package top.linruchang.order_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.linruchang.order_service.domain.OmsOrderMaster;
import top.linruchang.order_service.domain.OmsShoppingCart;

import java.util.List;

/**
 * @Classname CartDao
 * @Description
 * @Date 2020/3/14 17:54
 * @Created by lrc
 */

@Mapper
public interface CartDao {

    public List<OmsShoppingCart> findByUserId(Integer user_id);


    public Integer updateCart(OmsShoppingCart omsShoppingCart);

    public Integer addCart(OmsShoppingCart omsShoppingCart);

    public Integer deleteCarts(@Param("user_id")Integer user_id, @Param("product_ids") Integer[] product_ids);




}
