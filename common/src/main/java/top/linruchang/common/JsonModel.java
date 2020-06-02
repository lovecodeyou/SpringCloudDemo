package top.linruchang.common;

/**
 * @Classname JsonModel
 * @Description
 * @Date 2020/3/6 20:40
 * @Created by lrc
 */
public class JsonModel {

    Object data = null;
    String message = "获取数据成功";
    boolean isEffective = false;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEffective() {
        return isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }

    @Override
    public String toString() {
        return "JsonModel{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", isEffective=" + isEffective +
                '}';
    }
}
