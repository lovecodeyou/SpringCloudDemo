package top.linruchang.product_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.product_service.domain.PmsProductClassify;

import java.util.List;

/**
 * @Classname ProductDao
 * @Description
 * @Date 2020/2/5 20:58
 * @Created by lrc
 */

@Mapper
public interface ClassifyDao {
    public List<PmsProductClassify> findAll();
}
