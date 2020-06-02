package top.linruchang.member_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.member_service.domain.UmsRoleMenu;

import java.util.List;

/**
 * @Classname MenuDao
 * @Description
 * @Date 2020/3/24 16:20
 * @Created by lrc
 */
@Mapper
public interface MenuDao {

    public List<UmsRoleMenu> findAll();


    public List<UmsRoleMenu> findAll2(Integer user_id);
}
