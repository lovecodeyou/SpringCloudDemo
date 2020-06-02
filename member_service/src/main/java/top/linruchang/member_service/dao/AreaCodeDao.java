package top.linruchang.member_service.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.linruchang.member_service.domain.UmsAreaCode;

import java.util.List;

/**
 * @Classname AreaCodeDao
 * @Description
 * @Date 2020/3/18 23:14
 * @Created by lrc
 */

@Mapper
public interface AreaCodeDao {

    public List<UmsAreaCode> findByParentId(@Param("parent_id") Integer parentId);

}
