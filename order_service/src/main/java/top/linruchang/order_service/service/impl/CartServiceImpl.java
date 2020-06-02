package top.linruchang.order_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.order_service.client.ProductServiceClient;
import top.linruchang.order_service.dao.CartDao;
import top.linruchang.order_service.domain.OmsShoppingCart;
import top.linruchang.order_service.service.CartService;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.domain.QueryParam;

import javax.management.Query;
import java.util.List;

/**
 * @Classname CartServiceImpl
 * @Description
 * @Date 2020/3/14 17:53
 * @Created by lrc
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartDao cartDao;

    @Autowired
    ProductServiceClient productServiceClient;

    @Override
    public PageInfo<OmsShoppingCart> findByUserId(top.linruchang.domain.QueryParam pageQueryParam, Integer user_id) {


        if(pageQueryParam.getPageNum() == null) {
            PageHelper.startPage(0, 0);
        }else {
            PageHelper.startPage(pageQueryParam.getPageNum(), pageQueryParam.getPageSize());
        }

        List<OmsShoppingCart> carts = cartDao.findByUserId(user_id);

        PageInfo<OmsShoppingCart> pageInfo = new PageInfo<>(carts);


        String productIds = "";

        if (carts != null && carts.size() != 0) {
            for (OmsShoppingCart cart : carts) {
                productIds = productIds + cart.getProduct_id() + ",";
            }
        }
        if (productIds.length() > 0) {
            productIds = productIds.substring(0, productIds.length() - 1);
        }

        QueryParam queryParam = new QueryParam();
        queryParam.setProductIds(productIds);

        PageInfo<Product> productPageInfo = productServiceClient.listUI(productIds);

        List<Product> products = productPageInfo.getList();


        for (Product product : products) {
            for (OmsShoppingCart cart : carts) {
                if (cart.getProduct_id() == product.getId()) {
                    cart.setProduct(product);
                }
            }
        }

        return pageInfo;
    }

    @Override
    public boolean updateCart(OmsShoppingCart omsShoppingCart) {

        Integer flag = cartDao.updateCart(omsShoppingCart);

        if (flag > 0) {
            return true;
        }

        return false;
    }


    @Override
    public boolean addCart(OmsShoppingCart omsShoppingCart) {

        Integer flag = cartDao.addCart(omsShoppingCart);

        if (flag > 0) {
            return true;
        }
        return false;

    }

    @Override
    public boolean deleteCarts(Integer user_id, Integer[] product_ids) {
        Integer flag = cartDao.deleteCarts(user_id, product_ids);

        if (flag > 0) {
            return true;
        }
        return false;

    }


}
