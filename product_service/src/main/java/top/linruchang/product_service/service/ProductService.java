package top.linruchang.product_service.service;


import com.github.pagehelper.PageInfo;
import top.linruchang.product_service.domain.PmsProductComment;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.domain.QueryParam;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */
public interface ProductService {
    PageInfo<Product> findAllProducts(QueryParam queryParam);

    Product findProductById(Integer id);

    Product findByProductId(Integer product_id);


    PageInfo<PmsProductComment>  findCommentByProductId(QueryParam queryParam);

    boolean deleteByProductId(Integer id);

    boolean updateProduct(Product product);
}
