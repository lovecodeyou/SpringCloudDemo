package top.linruchang.adv_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.domain.PmsProductAdv;

import java.util.List;

/**
 * @Classname AdvDao
 * @Description
 * @Date 2020/3/10 21:39
 * @Created by lrc
 */
@Mapper
public interface AdvDao {

    public List<PmsProductAdv> findAllAdvs();
}
