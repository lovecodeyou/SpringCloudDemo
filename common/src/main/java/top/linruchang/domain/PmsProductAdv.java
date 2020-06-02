package top.linruchang.domain;

import java.util.Date;

/**
 * @Classname PmsProductAdv
 * @Description
 * @Date 2020/3/10 20:50
 * @Created by lrc
 */
public class PmsProductAdv {

    Integer id;
    Integer type_id;
    String location;
    String img;
    Date start_date;
    Date end_date;
    Integer sort;
    String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PmsProductAdv{" +
                "id=" + id +
                ", type_id=" + type_id +
                ", location='" + location + '\'' +
                ", img='" + img + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", sort=" + sort +
                ", url='" + url + '\'' +
                '}';
    }
}
