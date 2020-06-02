package top.linruchang.order_service.service;

import com.github.pagehelper.PageInfo;
import top.linruchang.domain.QueryParam;
import top.linruchang.order_service.domain.OmsOrderMaster;
import top.linruchang.order_service.domain.Param;

/**
 * @Classname OrderMasterService
 * @Description
 * @Date 2020/3/21 20:51
 * @Created by lrc
 */
public interface OrderMasterService {


    Integer addOrder(Param param);

    OmsOrderMaster findById(Integer id);

    PageInfo<OmsOrderMaster> findByUserId(Integer user_id, Boolean all, Integer pageNum, Integer pageSize);

    PageInfo<OmsOrderMaster> findAll(QueryParam queryParam);

    Boolean updataOrderMasterStatus(OmsOrderMaster omsOrderMaster);

    Boolean deleteByOrderId(Integer orderId);
}
