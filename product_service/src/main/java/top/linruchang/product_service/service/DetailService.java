package top.linruchang.product_service.service;


import top.linruchang.product_service.domain.PmsProductDetailinfo;
import top.linruchang.product_service.domain.Product;

import java.util.List;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */
public interface DetailService {
    PmsProductDetailinfo findById(Integer id);

    Boolean updateProductDetail(PmsProductDetailinfo pmsProductDetailinfo);
}
