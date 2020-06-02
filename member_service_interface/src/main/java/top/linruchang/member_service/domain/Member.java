package top.linruchang.member_service.domain;

/**
 * @Classname Member
 * @Description
 * @Date 2020/2/13 14:40
 * @Created by lrc
 */
public class Member {


    Integer id;
    Integer role_id;
    String loginName;
    String password;
    String nickname;
    String headImg;
    Integer flag;
    boolean flag1;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFlag() {
        return flag;
    }
    public boolean getFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;

        if(flag1) {
            this.flag = 1;
        }else {
            this.flag = 0;
        }

    }

    public void setFlag(Integer flag) {

        if(flag == 1) {
            this.flag = flag;
            this.flag1 = true;
        }else {
            this.flag = flag;
            this.flag1 = false;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", role_id=" + role_id +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }




}
