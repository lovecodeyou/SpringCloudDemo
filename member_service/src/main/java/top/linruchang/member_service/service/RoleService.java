package top.linruchang.member_service.service;

import com.github.pagehelper.PageInfo;
import top.linruchang.domain.QueryParam;
import top.linruchang.member_service.domain.UmsUserRole;

/**
 * @Classname RoleService
 * @Description
 * @Date 2020/3/26 11:25
 * @Created by lrc
 */
public interface RoleService {

    public PageInfo<UmsUserRole> findAll(QueryParam queryParam);

    boolean deleteRoleByRoleId(Integer role_id);

    boolean addRole(UmsUserRole role);

    boolean updateRoleByRoleId(UmsUserRole umsUserRole);
}
