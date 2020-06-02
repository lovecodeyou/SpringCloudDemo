package top.linruchang.order_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.domain.QueryParam;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;
import top.linruchang.order_service.client.MemberServiceClient;
import top.linruchang.order_service.client.ProductServiceClient;
import top.linruchang.order_service.dao.OrderDetailDao;
import top.linruchang.order_service.dao.OrderMasterDao;
import top.linruchang.order_service.domain.OmsOrderDetail;
import top.linruchang.order_service.domain.OmsOrderMaster;
import top.linruchang.order_service.domain.Param;
import top.linruchang.order_service.service.OrderMasterService;

import java.util.List;

/**
 * @Classname OrderMasterServiceImpl
 * @Description
 * @Date 2020/3/21 20:51
 * @Created by lrc
 */

@Service
public class OrderMasterServiceImpl implements OrderMasterService {


    @Autowired
    OrderMasterDao orderMasterDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    MemberServiceClient memberServiceClient;


    @Autowired
    ProductServiceClient productServiceClient;

    @Override
    public Integer addOrder(Param param) {

        OmsOrderMaster omsOrderMaster = new OmsOrderMaster();
        OmsOrderDetail omsOrderDetail = new OmsOrderDetail();


        Integer[] productIds = param.getProduct_ids();
        Integer[] productNums = param.getProductNums();


        String  productIds_str = "";
        String  productNums_str = "";
        for(Integer productId: productIds) {
            productIds_str = productIds_str + productId + ",";
        }
        productIds_str = productIds_str.substring(0,productIds_str.length() - 1);

        for(Integer productNum: productNums) {
            productNums_str = productNums_str + productNum + ",";
        }
        productNums_str = productNums_str.substring(0,productNums_str.length() - 1);



        //数据准备omsOrderMaster
        omsOrderMaster.setAddress_id(param.getAddress_id());
        omsOrderMaster.setUser_id(param.getUser_id());
        omsOrderMaster.setProductIds(productIds_str);
        omsOrderMaster.setProductNums(productNums_str);
        if(param.isPay_result()) {
            omsOrderMaster.setPay_status(1);
            omsOrderMaster.setOrder_status(1);
        }else {
            omsOrderMaster.setPay_status(0);
            omsOrderMaster.setOrder_status(0);
        }
        Integer flag1 = orderMasterDao.addOrder(omsOrderMaster);

        // 数据准备
        Integer flag2 = 0;
        for(int i = 0; i<param.getProduct_ids().length; i++) {

            omsOrderDetail.setOrder_id(omsOrderMaster.getId());
            omsOrderDetail.setProduct_id(param.getProduct_ids()[i]);
            omsOrderDetail.setNum(param.getProductNums()[i]);
            flag2 = orderDetailDao.addOrderDetail(omsOrderDetail);
        }



        if(flag1>0 && flag2>0) {
            return omsOrderMaster.getId();
        }
        return null;


    }

    @Override
    public OmsOrderMaster findById(Integer id) {

        OmsOrderMaster omsOrderMaster = orderMasterDao.findById(id);

        Integer address_id = omsOrderMaster.getAddress_id();

        UmsUserReceiveAddress address = memberServiceClient.findByAddressId(address_id);

        omsOrderMaster.setUmsUserReceiveAddress(address);

        List<OmsOrderDetail> omsOrderDetails = omsOrderMaster.getOmsOrderDetails();
        for(OmsOrderDetail detail: omsOrderDetails) {
            Integer product_id = detail.getProduct_id();
            detail.setProduct(productServiceClient.findByProductId(product_id));
        }




        return omsOrderMaster;
    }

    @Override
    public PageInfo<OmsOrderMaster> findByUserId(Integer user_id, Boolean all, Integer pageNum, Integer pageSize) {


        if(pageNum == null || pageNum == 0) {
            pageNum = 0;
            pageSize = 0;
        }

        //1. 分页检索数据  第PageNum页，每页显示pageSize条数据
        PageHelper.startPage(pageNum, pageSize);

        //2. 从数据库查询该用户（user_id）的订单信息
        List<OmsOrderMaster> omsOrderMasters = orderMasterDao.findByUserId(user_id, all);

        PageInfo<OmsOrderMaster> pageInfo = new PageInfo<>(omsOrderMasters);

        // 因为订单只包含产品的ID号、收货地址的ID号，并无产品信息、收货地址的信息
        for(OmsOrderMaster order: omsOrderMasters) {
            Integer address_id = order.getAddress_id();

            // 从用户服务中通过地址ID获取 用户收货地址信息 - RPC远程过程调用
            UmsUserReceiveAddress address= memberServiceClient.findByAddressId(address_id);
            order.setUmsUserReceiveAddress(address);

            List<OmsOrderDetail> omsOrderDetails = order.getOmsOrderDetails();

            // 从产品服务中通过产品ID号，获取产品的信息 - RPC远程过程调用
            for(OmsOrderDetail detail: omsOrderDetails) {
                Integer product_id = detail.getProduct_id();
                detail.setProduct(productServiceClient.findByProductId(product_id));
            }

        }
        // 将订单信息、用户收货地址信息、产品信息组装起来返回给用户
        return pageInfo;

    }

    @Override
    public PageInfo<OmsOrderMaster> findAll(QueryParam queryParam) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();

        if(pageNum == null || pageSize == 0) {
            pageSize = 0;
            pageNum = 0;
        }


        PageHelper.startPage(pageNum, pageSize);

        List<OmsOrderMaster> orders = orderMasterDao.findAll(queryParam);

        for(OmsOrderMaster order: orders) {
            Integer address_id = order.getAddress_id();
            UmsUserReceiveAddress address= memberServiceClient.findByAddressId(address_id);
            order.setUmsUserReceiveAddress(address);

            List<OmsOrderDetail> omsOrderDetails = order.getOmsOrderDetails();
            for(OmsOrderDetail detail: omsOrderDetails) {
                Integer product_id = detail.getProduct_id();
                detail.setProduct(productServiceClient.findByProductId(product_id));
            }

        }

        PageInfo<OmsOrderMaster> pageInfo = new PageInfo<>(orders);

        return  pageInfo;

    }


    @Override
    public Boolean updataOrderMasterStatus(OmsOrderMaster omsOrderMaster) {

        Integer order_status = omsOrderMaster.getOrder_status();

        if(order_status != null) {
            if(order_status == 1) {
                omsOrderMaster.setPay_status(1);
            }

            if(order_status == 0) {
                omsOrderMaster.setPay_status(0);
            }
        }

        Integer flag = orderMasterDao.updataOrderMasterStatus(omsOrderMaster);

        if(flag > 0) {
            return true;
        }

        return false;
    }


    @Override
    public Boolean deleteByOrderId(Integer orderId) {

        Integer flag = orderMasterDao.deleteByOrderId(orderId);

        if(flag > 0) {
            return true;
        }

        return false;
    }



}
