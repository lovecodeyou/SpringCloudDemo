package top.linruchang.product_service.dao;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.linruchang.product_service.domain.PmsProductComment;
import top.linruchang.product_service.domain.Product;
import top.linruchang.product_service.domain.QueryParam;

import java.util.List;

/**
 * @Classname ProductDao
 * @Description
 * @Date 2020/2/5 20:58
 * @Created by lrc
 */

@Mapper
public interface ProductDao {
    List<Product> findAllProducts(QueryParam queryParam);


    Product findByProductId(Integer product_id);


    List<Product> findByTypeId( @Param("type_id") Integer type_id);

    Integer deleteByProductId(Integer id);

    Integer updateProduct(Product product);

    List<PmsProductComment> findCommentByProductId(@Param("productId")Integer productId);
}
