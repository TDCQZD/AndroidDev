package com.example.administrator.listviewtest.bean;

import java.io.Serializable;

/**
 * 实体类
 */
public class TestBean implements Serializable {

    private String name;
    private int imgRes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
