package top.linruchang.order_service.fallback;

import com.github.pagehelper.PageInfo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import top.linruchang.order_service.client.ProductServiceClient;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.domain.QueryParam;

import java.util.List;

/**
 * @Classname ProductServiceClientFallback
 * @Description
 * @Date 2020/2/7 15:26
 * @Created by lrc
 */

@Component
public class ProductServiceClientFallback implements ProductServiceClient {


    @Override
    public PageInfo<Product> listUI(String productIds) {
        return null;
    }

    @Override
    public Product queryById(Integer id) {


        Product product = new Product();
        product.setName("服务降级成功");
        return product;
    }

    @Override
    public Product findByProductId(Integer product_id) {
        return null;
    }


}
