package com.example.administrator.retrofitdemo.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Description:
 * @author: Administrator
 * @Date: 2017/10/13 10:31
 */

public class Blog {

    @SerializedName("id")
    public long id;

    @SerializedName("date")
    public String date;

    @SerializedName("author")
    public String author;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
