package com.example.administrator.listviewtest.bean;

/**
 * Created by Administrator on 2017/4/1.
 * BaseAdapter bean
 */

public class ItemBean {
    public int ItemImageResid;
    public String ItemTitle;
    public String ItemContent;

    public ItemBean(int itemImageResid, String itemTitle, String itemContent) {
        ItemImageResid = itemImageResid;
        ItemTitle = itemTitle;
        ItemContent = itemContent;
    }

    public int getItemImageResid() {
        return ItemImageResid;
    }

    public void setItemImageResid(int itemImageResid) {
        ItemImageResid = itemImageResid;
    }

    public String getItemTitle() {
        return ItemTitle;
    }

    public void setItemTitle(String itemTitle) {
        ItemTitle = itemTitle;
    }

    public String getItemContent() {
        return ItemContent;
    }

    public void setItemContent(String itemContent) {
        ItemContent = itemContent;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "ItemImageResid=" + ItemImageResid +
                ", ItemTitle='" + ItemTitle + '\'' +
                ", ItemContent='" + ItemContent + '\'' +
                '}';
    }
}
