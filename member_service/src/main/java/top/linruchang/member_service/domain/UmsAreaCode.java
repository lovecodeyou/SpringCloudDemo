package top.linruchang.member_service.domain;

import java.util.List;

/**
 * @Classname UmsAreaCode
 * @Description
 * @Date 2020/3/18 21:02
 * @Created by lrc
 */
public class UmsAreaCode {



    Integer id;
    String name;
    Integer arealevel;
    Integer parent_id;

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

    public Integer getArealevel() {
        return arealevel;
    }

    public void setArealevel(Integer arealevel) {
        this.arealevel = arealevel;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "UmsAreaCode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", arealevel=" + arealevel +
                ", parent_id=" + parent_id +
                '}';
    }
}
