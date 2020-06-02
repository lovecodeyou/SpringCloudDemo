package top.linruchang.member_service.controller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.linruchang.common.JsonModel;
import top.linruchang.domain.QueryParam;
import top.linruchang.member_service.domain.UmsRoleMenu;
import top.linruchang.member_service.domain.UmsUserRole;
import top.linruchang.member_service.service.MenuService;
import top.linruchang.member_service.service.RoleService;

import java.util.List;

/**
 * @Classname RoleController
 * @Description
 * @Date 2020/3/24 16:29
 * @Created by lrc
 */

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

    // 查询所有角色
    @GetMapping("")
    @ResponseBody
    public JsonModel findAllRoles(QueryParam queryParam) {

        JsonModel jsonModel = new JsonModel();

        PageInfo<UmsUserRole> pageInfo = roleService.findAll(queryParam);

        List<UmsUserRole> roles = pageInfo.getList();

        if(roles == null || roles.size() == 0) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("获取角色列表失败，请重新尝试获取");
        }else {
            jsonModel.setData(pageInfo);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取角色列表成功");
        }
        return jsonModel;
    }


    // 添加角色
    @PostMapping("")
    @ResponseBody
    public JsonModel addRole(@RequestBody UmsUserRole umsUserRole) {

        JsonModel jsonModel = new JsonModel();

        boolean flag = roleService.addRole(umsUserRole);

        if(!flag) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("添加"+ umsUserRole.getName()  +"角色失败");
        }else {
            jsonModel.setData(null);
            jsonModel.setEffective(true);
            jsonModel.setMessage("添加角色"+ umsUserRole.getName()  +"列表成功");
        }
        return jsonModel;
    }


    //删除角色
    @DeleteMapping("/{role_id}")
    @ResponseBody
    public JsonModel deleteRoleByRoleId(@PathVariable("role_id") Integer role_id) {

        JsonModel jsonModel = new JsonModel();

        boolean flag = roleService.deleteRoleByRoleId(role_id);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("删除ID是" + role_id + "角色成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("删除ID是" + role_id + "角色失败");
        }
        return jsonModel;

    }

    //更新角色
    @PutMapping("/{role_id}")
    @ResponseBody
    public JsonModel updateRoleByRoleId(@PathVariable("role_id") Integer role_id, @RequestBody UmsUserRole umsUserRole) {

        JsonModel jsonModel = new JsonModel();

        umsUserRole.setId(role_id);

        Boolean flag = roleService.updateRoleByRoleId(umsUserRole);

        if(flag) {
            jsonModel.setEffective(true);
            jsonModel.setMessage("更新ID是" + role_id + "的角色成功");
        }else {
            jsonModel.setEffective(false);
            jsonModel.setMessage("更新ID是" + role_id + "的角色失败");
        }

        return jsonModel;

    }

    //查询用户角色列表
    @GetMapping("menus/{user_id}")
    @ResponseBody
    public JsonModel findAllMenu(QueryParam queryParam, @PathVariable Integer user_id) {


        queryParam.setId(user_id);

        JsonModel jsonModel = new JsonModel();

        List<UmsRoleMenu> menus = menuService.findAllMenu(queryParam);

        if(menus == null || menus.size() == 0) {
            jsonModel.setEffective(false);
            jsonModel.setMessage("获取失败：可能无数据，请重新尝试！");
        }else {
            jsonModel.setData(menus);
            jsonModel.setEffective(true);
            jsonModel.setMessage("获取数据成功");
        }
        return jsonModel;
    }


}
