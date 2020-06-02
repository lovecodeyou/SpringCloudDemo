package top.linruchang.order_service.client;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.linruchang.order_service.fallback.ProductServiceClientFallback;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.domain.QueryParam;

import java.util.List;

/**
 * @Classname ProductServiceClient
 * @Description
 * @Date 2020/2/7 10:27
 * @Created by lrc
 */

@FeignClient(name="productService", fallback = ProductServiceClientFallback.class)
public interface ProductServiceClient {


    @RequestMapping(value="products", method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Product> listUI(@RequestParam("productIds") String productIds);



    @GetMapping(value = "products/{id}")
    @ResponseBody
    public Product queryById(@PathVariable("id") Integer id);


    @GetMapping("products/product/{product_id}")
    @ResponseBody
    public Product findByProductId(@PathVariable("product_id")Integer product_id);

}
