package top.linruchang.product_service.service;


import com.github.pagehelper.PageInfo;
import top.linruchang.domain.QueryParam;
import top.linruchang.product_service.domain.PmsProductType;
import top.linruchang.product_service.domain.Product;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */
public interface TypeService {
    PageInfo<PmsProductType> findAll(QueryParam queryParam);


    PageInfo<PmsProductType> findAllOnlyType(QueryParam queryParam);

    PmsProductType findById(QueryParam queryParam,Integer type_id);

    PageInfo<Product> findById2(QueryParam queryParam, Integer type_id);
}
