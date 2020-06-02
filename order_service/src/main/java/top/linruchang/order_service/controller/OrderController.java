package top.linruchang.order_service.controller;

import com.github.pagehelper.PageInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import top.linruchang.common.JsonModel;
import top.linruchang.domain.QueryParam;
import top.linruchang.order_service.domain.OmsOrderMaster;
import top.linruchang.order_service.domain.Param;
import top.linruchang.order_service.service.OrderMasterService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Classname OrderController
 * @Description
 * @Date 2020/3/21 20:42
 * @Created by lrc
 */

@Controller
@RequestMapping("books")
public class OrderController {


    @Autowired
    OrderMasterService orderMasterService;

    //自定义日期转换
    @InitBinder
    public void convertDate(ServletRequestDataBinder servletRequestDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        servletRequestDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    //用于熔断机制
    public JsonModel hystrixData(@RequestBody  OmsOrderMaster omsOrderMaster, @PathVariable("orderId") Integer orderId) {
        JsonModel jsonModel = new JsonModel();
        jsonModel.setEffective(false);
        jsonModel.setMessage("服务器内部出错，或者超时请求");
        return jsonModel;
    }


    //获取所有用户订单
    @GetMapping("all")
    @ResponseBody
    public JsonModel findAllOrder(QueryParam queryParam) {

        System.out.println(queryParam);

        JsonModel jsonModel = new JsonModel();
        PageInfo<OmsOrderMaster> pageInfo = orderMasterService.findAll(queryParam);

        List<OmsOrderMaster> orders = pageInfo.getList();

        if(orders == null && orders.size() > 0) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("获取订单失败：请重新查询订单");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取订单成功");
        }

        return jsonModel;

    }


    //添加订单
    @PostMapping("")
    @ResponseBody
    public JsonModel addOrder(@RequestBody Param param) {

        System.out.println(param);

        JsonModel jsonModel = new JsonModel();

        Integer orderId = orderMasterService.addOrder(param);

        if (orderId != null) {
            jsonModel.setData(orderId);
            jsonModel.setEffective(true);
            jsonModel.setMessage("下单成功");
        } else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("下单失败");
        }

        return jsonModel;

    }


    //查询某个订单 - 根据订单ID号
    @GetMapping("/{id}")
    @ResponseBody
    public JsonModel findById(@PathVariable("id") Integer id) {

        JsonModel jsonModel = new JsonModel();

        OmsOrderMaster omsOrderMaster = orderMasterService.findById(id);

        System.out.println("=====订单");
        System.out.println(omsOrderMaster);

        if (omsOrderMaster != null) {
            jsonModel.setData(omsOrderMaster);
            jsonModel.setEffective(true);
            jsonModel.setMessage("成功查询订单号" + id);
        } else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("查询失败，请重新进行查询");
        }

        return jsonModel;
    }


    // 查询订单 - 根据用户ID号
    @GetMapping("")
    @ResponseBody
//    public JsonModel findByUser_id(@RequestParam("user_id") Integer user_id, @RequestParam("all") Boolean all, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize")Integer pageSize) {
    public JsonModel findByUser_id(@RequestParam("user_id") Integer user_id, @RequestParam("all") Boolean all, Integer pageNum, Integer pageSize) {

        JsonModel jsonModel = new JsonModel();

        PageInfo<OmsOrderMaster> pageInfo = orderMasterService.findByUserId(user_id, all, pageNum, pageSize);

        List<OmsOrderMaster> omsOrderMasters = pageInfo.getList();

        if (omsOrderMasters == null || omsOrderMasters.size() == 0) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("获取订单失败：可能你并没有下单");
        } else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("查询订单成功");
        }
        return jsonModel;


    }


    // 修改用户订单状态 - 根据订单ID号
    @PutMapping("/{orderId}")
    @ResponseBody
//    @HystrixCommand(fallbackMethod="hystrixData")
    public JsonModel updataOrderMasterStatus(@RequestBody  OmsOrderMaster omsOrderMaster, @PathVariable("orderId") Integer orderId) {

        JsonModel jsonModel = new JsonModel();

        omsOrderMaster.setId(orderId);

        Boolean flag =  orderMasterService.updataOrderMasterStatus(omsOrderMaster);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("修改成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("修改失败");
        }


        return jsonModel;

    }


    //删除用户订单 - 根据订单ID号
    @DeleteMapping("/{orderId}")
    @ResponseBody
    public JsonModel deleteOrderMaster(@PathVariable("orderId") Integer orderId) {

        JsonModel jsonModel = new JsonModel();

        boolean flag = orderMasterService.deleteByOrderId(orderId);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("删除成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("删除失败");
        }

        return jsonModel;
    }

}
