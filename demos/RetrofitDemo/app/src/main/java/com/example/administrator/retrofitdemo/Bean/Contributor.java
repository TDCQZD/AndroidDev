package com.example.administrator.retrofitdemo.Bean;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/17 15:11
 */

public class Contributor {
    private String login;
    private Integer contributions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void setContributions(Integer contributions) {
        this.contributions = contributions;
    }
}
