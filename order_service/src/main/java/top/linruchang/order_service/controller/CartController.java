package top.linruchang.order_service.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.linruchang.common.JsonModel;
import top.linruchang.order_service.client.ProductServiceClient;
import top.linruchang.order_service.domain.DeleteParam;
import top.linruchang.order_service.domain.OmsShoppingCart;
import top.linruchang.order_service.domain.UpdataParam;
import top.linruchang.order_service.service.CartService;
import top.linruchang.product_service.domain.QueryParam;

import java.util.List;

/**
 * @Classname CartController
 * @Description
 * @Date 2020/3/14 17:40
 * @Created by lrc
 */


@Controller
@RequestMapping("carts")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductServiceClient productServiceClient;


    //无用，只是用来测试
    @GetMapping("testFeign")
    @ResponseBody
    public Object testFeign() {

        QueryParam queryParam  = new QueryParam();
        queryParam.setProductIds("1,2");

        Object object = productServiceClient.listUI("1,2");
        System.out.println(object);

        return object;
    }


    // 获取用户的购物车信息
    @GetMapping("{user_id}")
    @ResponseBody
    public JsonModel findByUserId(top.linruchang.domain.QueryParam queryParam, @PathVariable("user_id") Integer user_id) {

        JsonModel jsonModel = new JsonModel();

        PageInfo<OmsShoppingCart> pageInfo = cartService.findByUserId(queryParam,user_id);

        List<OmsShoppingCart> omsShoppingCarts =  pageInfo.getList();

        if(omsShoppingCarts == null || omsShoppingCarts.size() == 0) {
            jsonModel.setMessage("获取数据失败：可能用户并无在购物车添加内容");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
        }
        return jsonModel;
    }


    //修改购物车某个产品的数量
    @PutMapping("")
    @ResponseBody
    public JsonModel UpdateCarts(@RequestBody UpdataParam updataParam) {

        JsonModel jsonModel = new JsonModel();
        if(updataParam != null) {
            List<OmsShoppingCart> carts = updataParam.getCarts();
            if(carts != null && carts.size() != 0) {

                for(OmsShoppingCart cart : carts) {
                    cart.setUser_id(updataParam.getUser_id());
                    cartService.updateCart(cart);
                }

            }
            jsonModel.setMessage("更新购物车信息成功");
            jsonModel.setEffective(true);
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("更新购物车信息失败，请重新发送");
        }

        return jsonModel;

    }


    //增添购物车的内容
    @PostMapping
    @ResponseBody
    public JsonModel addCart( @RequestBody List<OmsShoppingCart> carts) {

        JsonModel jsonModel = new JsonModel();

        if(carts == null || carts.size() == 0) {
            jsonModel.setMessage("无购物车内容可增添");
            jsonModel.setEffective(false);
        }else {
            for(OmsShoppingCart cart : carts) {
                cartService.addCart(cart);
            }
            jsonModel.setMessage("增添购物车内容成功");
            jsonModel.setEffective(true);
        }

        return jsonModel;
    }


    //删除购物车的内容
    @DeleteMapping
    @ResponseBody
    public JsonModel deleteCart(@RequestBody DeleteParam deleteParam) {
        JsonModel jsonModel = new JsonModel();
        cartService.deleteCarts(deleteParam.getUser_id(), deleteParam.getProduct_ids());
        jsonModel.setEffective(true);
        jsonModel.setMessage("删除购物车内容成功");
        return jsonModel;
    }



}
