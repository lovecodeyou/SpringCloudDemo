package top.linruchang.member_service.domain;

import java.util.List;

/**
 * @Classname UmsRoleMenu
 * @Description
 * @Date 2020/3/24 16:16
 * @Created by lrc
 */
public class UmsRoleMenu {



    Integer id;
    String auth_name;
    String path;
    Integer father_id;
    Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    List<UmsRoleMenu> childrens;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuth_name() {
        return auth_name;
    }

    public void setAuth_name(String auth_name) {
        this.auth_name = auth_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getFather_id() {
        return father_id;
    }

    public void setFather_id(Integer father_id) {
        this.father_id = father_id;
    }

    public List<UmsRoleMenu> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<UmsRoleMenu> childrens) {
        this.childrens = childrens;
    }
}
