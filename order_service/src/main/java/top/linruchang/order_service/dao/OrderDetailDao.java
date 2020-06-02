package top.linruchang.order_service.dao;

/**
 * @Classname OrderDetailDao
 * @Description
 * @Date 2020/3/21 21:13
 * @Created by lrc
 */

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.order_service.domain.OmsOrderDetail;

import java.util.List;

@Mapper
public interface OrderDetailDao {


    public Integer addOrderDetail(OmsOrderDetail omsOrderDetail);

    public List<OmsOrderDetail>  findByOrderId(Integer order_id);

}
