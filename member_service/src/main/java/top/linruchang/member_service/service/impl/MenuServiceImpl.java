package top.linruchang.member_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.linruchang.domain.QueryParam;
import top.linruchang.member_service.dao.MenuDao;
import top.linruchang.member_service.domain.UmsRoleMenu;
import top.linruchang.member_service.service.MenuService;

import java.util.List;

/**
 * @Classname MenuServiceImpl
 * @Description
 * @Date 2020/3/24 16:19
 * @Created by lrc
 */

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuDao menuDao;

    @Override
    public List<UmsRoleMenu> findAllMenu(QueryParam queryParam) {


        String dataType = queryParam.getDataType();

        if("list".equals(dataType)) {
           return menuDao.findAll2(queryParam.getId());
        }else {
            return menuDao.findAll();
        }

    }
}
