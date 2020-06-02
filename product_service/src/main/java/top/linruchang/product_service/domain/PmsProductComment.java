package top.linruchang.product_service.domain;

import java.lang.reflect.Member;
import java.util.Date;

/**
 * @Classname PmsProductComment
 * @Description
 * @Date 2020/4/30 17:34
 * @Created by lrc
 */
public class PmsProductComment {

    String nickname;
    String headImg;
    Integer id;
    Integer product_id;
    Integer user_id;
    Integer good_score;
    Integer service_score;
    String content;
    Date content_time;
    String shop_reply;
    Date reply_time;
    Integer show_flag;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGood_score() {
        return good_score;
    }

    public void setGood_score(Integer good_score) {
        this.good_score = good_score;
    }

    public Integer getService_score() {
        return service_score;
    }

    public void setService_score(Integer service_score) {
        this.service_score = service_score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getContent_time() {
        return content_time;
    }

    public void setContent_time(Date content_time) {
        this.content_time = content_time;
    }

    public String getShop_reply() {
        return shop_reply;
    }

    public void setShop_reply(String shop_reply) {
        this.shop_reply = shop_reply;
    }

    public Date getReply_time() {
        return reply_time;
    }

    public void setReply_time(Date reply_time) {
        this.reply_time = reply_time;
    }

    public Integer getShow_flag() {
        return show_flag;
    }

    public void setShow_flag(Integer show_flag) {
        this.show_flag = show_flag;
    }
}
