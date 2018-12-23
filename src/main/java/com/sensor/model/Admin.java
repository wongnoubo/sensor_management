package com.sensor.model;

/**
 * 管理员model
 * @author wongnoubo
 */
public class Admin {
    private int adminId;
    private String password;
    private String email;
    private String nickname;
    private String code;
    private String infotablename;
    private int state;

    /*public Admin(int adminId, String password,String email, String nickname, String code, String infotablename,int state){
        this.adminId = adminId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.code = code;
        this.infotablename = infotablename;
        this.state = state;
    }*/



    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public String getNickname() {
        return nickname;
    }

    public String getInfotablename() {
        return infotablename;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setInfotablename(String infotablename) {
        this.infotablename = infotablename;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
