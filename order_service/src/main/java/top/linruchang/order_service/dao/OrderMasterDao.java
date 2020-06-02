package top.linruchang.order_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.linruchang.domain.QueryParam;
import top.linruchang.order_service.domain.OmsOrderMaster;

import java.util.List;

/**
 * @Classname OrderMasterDao
 * @Description
 * @Date 2020/3/21 21:12
 * @Created by lrc
 */
@Mapper
public interface OrderMasterDao {

    public Integer addOrder(OmsOrderMaster omsOrderMaster);

    public OmsOrderMaster findById(Integer id);

    public List<OmsOrderMaster> findByUserId(@Param("user_id") Integer user_id,@Param("all") Boolean all);

    public List<OmsOrderMaster> findAll(QueryParam queryParam);

    public  Integer updataOrderMasterStatus(OmsOrderMaster omsOrderMaster);

    public Integer deleteByOrderId(Integer orderId);

}
