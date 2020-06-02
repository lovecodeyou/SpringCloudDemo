package top.linruchang.product_service.util;

/**
 * @Classname JsonModel
 * @Description
 * @Date 2020/2/6 22:48
 * @Created by lrc
 */
public class JsonModel {

    String msg;
    String data;
    Boolean isSuccess;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "JsonModel{" +
                "msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
