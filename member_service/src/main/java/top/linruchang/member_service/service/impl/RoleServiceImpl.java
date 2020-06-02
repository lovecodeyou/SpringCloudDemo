package top.linruchang.member_service.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.domain.QueryParam;
import top.linruchang.member_service.dao.RoleDao;
import top.linruchang.member_service.domain.UmsUserRole;
import top.linruchang.member_service.service.RoleService;

import java.util.List;

/**
 * @Classname RoleServiceImpl
 * @Description
 * @Date 2020/3/26 14:32
 * @Created by lrc
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public PageInfo<UmsUserRole> findAll(QueryParam queryParam) {

        Integer pageNum = queryParam.getPageNum();
        Integer pageSize = queryParam.getPageSize();

        if(pageNum == null || pageNum == 0 ) {
            pageNum = 0;
            pageSize = 0;
        }

        PageHelper.startPage(pageNum, pageSize);


        List<UmsUserRole> roles = roleDao.findAll();

        PageInfo<UmsUserRole> pageInfo = new PageInfo<>(roles);

        return pageInfo;

    }

    @Override
    public boolean deleteRoleByRoleId(Integer role_id) {

        Integer flag = roleDao.deleteRoleByRoleId(role_id);

        if(flag > 0) {
            return true;
        }else {
            return false;
        }

    }


    @Override
    public boolean addRole(UmsUserRole role) {
        Integer flag = roleDao.addRole(role);

        if(flag > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRoleByRoleId(UmsUserRole umsUserRole) {

        Integer flag = roleDao.updateRoleByRoleId(umsUserRole);

        if(flag > 0) {
            return true;
        }else {
            return false;
        }

    }

}
