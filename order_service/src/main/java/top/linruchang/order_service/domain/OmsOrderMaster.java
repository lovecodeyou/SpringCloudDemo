package top.linruchang.order_service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import top.linruchang.member_service.domain.UmsUserReceiveAddress;

import java.util.Date;
import java.util.List;

/**
 * @Classname OmsOrderMaster
 * @Description
 * @Date 2020/3/21 20:27
 * @Created by lrc
 */
public class OmsOrderMaster {

//      `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单流水号',
////            `user_id` int(11) NOT NULL COMMENT '用户id外键',
////            `order_id` int(11) DEFAULT NULL COMMENT '订单号 - 无论是否支付都会生成',
////            `transaction_id` int(11) DEFAULT NULL COMMENT '支付交易的ID - 只有用户付钱才会生成该id',
////            `order_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '-1: 取消订单 0: 未确认 1: 已确认(给钱了) 2: 已发货  3: 已收货 4: 运输中 5: 已完成',
////            `pay_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0:待付款 1: 已付款 -1: 不再支付',
////            `total_money` int(255) NOT NULL COMMENT '订单总金额 - 分为单位',
////            `create_time` date NOT NULL COMMENT '订单创建时间',
////    update_time
////            `payment_time` date


    Integer id;
    Integer user_id;
    Integer address_id;
    String order_id;
    String transaction_id;
    Integer order_status;
    Integer pay_status;
    Integer total_money;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08")
    Date create_time;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08")
    Date update_time;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08")
    Date payment_time;

    String productIds;
    String productNums;


    List<OmsOrderDetail> omsOrderDetails;
    UmsUserReceiveAddress umsUserReceiveAddress;


    public String getProductNums() {
        return productNums;
    }

    public void setProductNums(String productNums) {
        this.productNums = productNums;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    public Integer getTotal_money() {
        return total_money;
    }

    public void setTotal_money(Integer total_money) {
        this.total_money = total_money;
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

    public Date getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(Date payment_time) {
        this.payment_time = payment_time;
    }

    public List<OmsOrderDetail> getOmsOrderDetails() {
        return omsOrderDetails;
    }

    public void setOmsOrderDetails(List<OmsOrderDetail> omsOrderDetails) {
        this.omsOrderDetails = omsOrderDetails;
    }

    public UmsUserReceiveAddress getUmsUserReceiveAddress() {
        return umsUserReceiveAddress;
    }

    public void setUmsUserReceiveAddress(UmsUserReceiveAddress umsUserReceiveAddress) {
        this.umsUserReceiveAddress = umsUserReceiveAddress;
    }

    @Override
    public String toString() {
        return "OmsOrderMaster{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", address_id=" + address_id +
                ", order_id='" + order_id + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                ", order_status=" + order_status +
                ", pay_status=" + pay_status +
                ", total_money=" + total_money +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", payment_time=" + payment_time +
                ", omsOrderDetails=" + omsOrderDetails +
                ", umsUserReceiveAddress=" + umsUserReceiveAddress +
                '}';
    }
}
