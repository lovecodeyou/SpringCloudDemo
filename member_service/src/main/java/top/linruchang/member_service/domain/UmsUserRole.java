package top.linruchang.member_service.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname UmsUserRole
 * @Description
 * @Date 2020/3/26 11:02
 * @Created by lrc
 */


@JsonIgnoreProperties("handler")
public class UmsUserRole implements Serializable {


    Integer id;
    String name;
    String describe;
    String menuIds;
    List<UmsRoleMenu> umsRoleMenus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public List<UmsRoleMenu> getUmsRoleMenus() {
        return umsRoleMenus;
    }

    public void setUmsRoleMenus(List<UmsRoleMenu> umsRoleMenus) {
        this.umsRoleMenus = umsRoleMenus;
    }
}
