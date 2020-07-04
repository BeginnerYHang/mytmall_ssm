package com.yuanhang.pojo;

public class User {
    private Integer id;

    private String username;

    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 用于进行匿名显示评价
     */
    public String getAnonymousName(){
        if (username == null){
            return null;
        }
        if (username.length() <= 1){
            return "*";
        }
        if (username.length() == 2){
            return username.substring(0,1) + "*";
        }
        StringBuilder annoymousName = new StringBuilder(username.substring(0,1));
        for (int i = 0 ; i < username.substring(1,username.length()-1).length();i++){
            annoymousName.append("*");
        }
        return  annoymousName.append(username.substring(username.length() - 1)).toString();
    }
}