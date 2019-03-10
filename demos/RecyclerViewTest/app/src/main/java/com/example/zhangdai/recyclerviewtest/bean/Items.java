package com.example.zhangdai.recyclerviewtest.bean;

/**
 * Created by Administrator on 2017/8/14.
 */

public class Items {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Items{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
