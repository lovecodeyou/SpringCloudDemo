package top.linruchang.domain;

import java.util.Date;

/**
 * @Classname UmsUserAuthcode
 * @Description
 * @Date 2020/5/5 2:14
 * @Created by lrc
 */
public class UmsUserAuthcode {

    Integer id;
    String verification_code;
    String from;
    String to;
    Integer type;


    Date create_time;
    Date end_time;
    Integer valid_time;


    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Integer getValid_time() {
        return valid_time;
    }

    public void setValid_time(Integer valid_time) {
        this.valid_time = valid_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
