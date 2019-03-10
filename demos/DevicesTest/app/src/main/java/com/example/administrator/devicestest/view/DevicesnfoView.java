package com.example.administrator.devicestest.view;

/**
 * @Description:设备信息显示界面
 * @author: Administrator
 * @Date: 2018/3/22 15:51
 */

public class DevicesnfoView {

    private String name;
    private String info;

    public DevicesnfoView(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
