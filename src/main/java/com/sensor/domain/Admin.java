package com.sensor.domain;

public class Admin {
    private int adminId;
    private String password;
    private String email;
    private String nickname;
    private String code;
    private String infotablename;
    private int state;

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

    @Override
    public String toString(){
        return "id: "+adminId+" password: "+password+" email: "+email +"nickname: "+nickname+"infotablename: "+infotablename+" state: "+state;
    }
}
