package com.example.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
*ContentProvider使用
*@author ZD
*created at 2017/8/5 11:09
*description：
*/

public class Provider extends ContentProvider {

    /*
    第一次创建该ContentProvider时调用该方法
     */
    @Override
    public boolean onCreate() {
        System.out.println("===onCreate方法被调用===");
        return true;
    }

    /*
   根据 Uri 查询出 selection 条件所匹配的全部记录，
   其中 projection就是一个列名列表，表明只选择出指定的数据列
   该方法应该返回查询得到的Cursor
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        System.out.println(uri + "===query方法被调用===");
        System.out.println("selection参数为：" + selection);
        return null;

    }

    /*
    返回当前Uri所代表的数据的MIME类型
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /*
    根据Uri插入values对应的数据
    该方法应该返回新插入的记录的Uri
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        System.out.println(uri + "===insert方法被调用===");
        System.out.println("values参数为：" + values);
        return null;
    }

    /*
    根据 Uri删除selection条件所匹配的全部记录
        该方法应该返回被删除的记录条数
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        System.out.println(uri + "===delete方法被调用===");
        System.out.println("selection参数为：" + selection);
        return 0;
    }

    /*
    根据Uri修改 selection 条件所匹配的全部记录
    该方法应该返回被更新的记录条数
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        System.out.println(uri + "===update方法被调用===");
        System.out.println("selection参数为："
                + selection + ",values参数为：" + values);
        return 0;
    }
}
