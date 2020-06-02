package top.linruchang.member_service.dao;

import org.apache.ibatis.annotations.Mapper;
import top.linruchang.member_service.domain.UmsUserRole;

import java.util.List;

/**
 * @Classname RoleDao
 * @Description
 * @Date 2020/3/26 11:16
 * @Created by lrc
 */

@Mapper
public interface RoleDao {

    public List<UmsUserRole> findAll();

    public Integer deleteRoleByRoleId(Integer role_id);

    public Integer addRole(UmsUserRole role);

    public Integer updateRoleByRoleId(UmsUserRole umsUserRole);

}
