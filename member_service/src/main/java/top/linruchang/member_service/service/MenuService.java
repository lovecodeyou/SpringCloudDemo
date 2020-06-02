package top.linruchang.member_service.service;

import top.linruchang.domain.QueryParam;
import top.linruchang.member_service.domain.UmsRoleMenu;

import java.util.List;

/**
 * @Classname MenuService
 * @Description
 * @Date 2020/3/24 16:18
 * @Created by lrc
 */
public interface MenuService {

    List<UmsRoleMenu>  findAllMenu(QueryParam queryParam);

}
