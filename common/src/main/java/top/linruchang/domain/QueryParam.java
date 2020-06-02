package top.linruchang.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Classname QueryParam
 * @Description
 * @Date 2020/3/25 0:43
 * @Created by lrc
 */
public class QueryParam {

    Integer pageNum;
    Integer pageSize;
    String nickname;
    Integer id;
    String dataType;

    List<String> dates;
    String order_id;

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "QueryParam{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", nickname='" + nickname + '\'' +
                ", id=" + id +
                ", dataType='" + dataType + '\'' +
                ", dates=" + dates +
                ", order_id='" + order_id + '\'' +
                '}';
    }
}
