package top.linruchang.product_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.product_service.domain.PmsProductDetailinfo;

/**
 * @Classname DetailDao
 * @Description
 * @Date 2020/3/12 21:40
 * @Created by lrc
 */

@Mapper
public interface DetailDao {

    public PmsProductDetailinfo findById(Integer id);

    public Integer updateProductDetail(PmsProductDetailinfo pmsProductDetailinfo) ;


}
