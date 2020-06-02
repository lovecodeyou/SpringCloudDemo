package top.linruchang.member_service.domain;

/**
 * @Classname ReceiverParam
 * @Description
 * @Date 2020/3/17 22:20
 * @Created by lrc
 */
public class ReceiverParam {

    Integer user_id;
    Integer historyDefaultAddressId;
    Integer newDefaultAddressId;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getHistoryDefaultAddressId() {
        return historyDefaultAddressId;
    }

    public void setHistoryDefaultAddressId(Integer historyDefaultAddressId) {
        this.historyDefaultAddressId = historyDefaultAddressId;
    }

    public Integer getNewDefaultAddressId() {
        return newDefaultAddressId;
    }

    public void setNewDefaultAddressId(Integer newDefaultAddressId) {
        this.newDefaultAddressId = newDefaultAddressId;
    }
}
