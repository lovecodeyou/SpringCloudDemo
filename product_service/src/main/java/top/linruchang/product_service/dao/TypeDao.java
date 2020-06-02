package top.linruchang.product_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.product_service.domain.PmsProductDetailinfo;
import top.linruchang.product_service.domain.PmsProductType;
import top.linruchang.product_service.domain.Product;

import java.util.List;

/**
 * @Classname ProductDao
 * @Description
 * @Date 2020/2/5 20:58
 * @Created by lrc
 */

@Mapper
public interface TypeDao {
    List<PmsProductType> findAll();


    PmsProductType findById(Integer type_id);

    List<PmsProductType> findAllOnlyType();

}
