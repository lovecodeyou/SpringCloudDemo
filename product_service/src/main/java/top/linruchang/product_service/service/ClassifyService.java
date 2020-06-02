package top.linruchang.product_service.service;


import com.github.pagehelper.PageInfo;
import top.linruchang.product_service.domain.PmsProductClassify;
import top.linruchang.product_service.domain.QueryParam;

/**
 * @Classname ProductServiceImpl
 * @Description
 * @Date 2020/2/5 20:54
 * @Created by lrc
 */
public interface ClassifyService {
    PageInfo<PmsProductClassify> findAll(QueryParam queryParam);
}
