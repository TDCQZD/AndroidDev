package com.example.dictprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.dictprovider.db.MyDatabaseHelper;
import com.example.dictprovider.utils.Words;

/**
*ContentProvider
*@author ZD
*created at 2017/8/5 16:06
*description：
*/

public class DictProvider extends ContentProvider{
   //UriMatcher 处理URI的工具类
    private static UriMatcher matcher
            = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int WORDS = 1;
    private static final int WORD = 2;
    private MyDatabaseHelper dbOpenHelper;
    static
    {
        // 为UriMatcher注册两个Uri
        matcher.addURI(Words.AUTHORITY, "words", WORDS);
        matcher.addURI(Words.AUTHORITY, "word/#", WORD);
    }
    // 第一次调用该DictProvider时，系统先创建DictProvider对象，并回调该方法
    @Override
    public boolean onCreate() {
        dbOpenHelper = new MyDatabaseHelper(this.getContext(),
                "myDict.db",null ,1);//创建数据库和表
        return true;
    }
    /*
    查询数据的方法
     该方法应该返回查询得到的Cursor
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
       /*
        matcher 操作Uri字符串的工具类。
         */
        switch (matcher.match(uri))
        {
            // 如果Uri参数代表操作全部数据项
            case WORDS:
                // 执行查询
                return db.query("dict", projection, selection,
                        selectionArgs, null, null, sortOrder);
            // 如果Uri参数代表操作指定数据项
            case WORD:
                // 解析出想查询的记录ID
                long id = ContentUris.parseId(uri);
                String selectionClause = Words.Word._ID + "=" + id;
                // 如果原来的selection子句存在，拼接selection子句
                if (selection != null && !"".equals(selection))
                {
                    selectionClause = selectionClause + " and " + selection;
                }
                return db.query("dict", projection, selectionClause, selectionArgs,
                        null, null, sortOrder);
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }
    // 返回指定Uri参数对应的数据的MIME类型
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (matcher.match(uri))
        {
            // 如果操作的数据是多项记录
            case WORDS:
                return "vnd.android.cursor.dir/org.crazyit.dict";
            // 如果操作的数据是单项记录
            case WORD:
                return "vnd.android.cursor.item/org.crazyit.dict";
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
    }
    /*
     插入数据方法
     该方法应该返回新插入的记录的Uri
      */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // 获得数据库实例
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (matcher.match(uri))
        {
            // 如果Uri参数代表操作全部数据项
            case WORDS:
                // 插入数据，返回插入记录的ID
                long rowId = db.insert("dict", Words.Word._ID, values);
                // 如果插入成功返回uri
                if (rowId > 0)
                {
                    // 在已有的 Uri的后面追加ID
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);
                    // 通知数据已经改变
                    getContext().getContentResolver()
                            .notifyChange(wordUri, null);
                    return wordUri;
                }
                break;
            default :
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        return null;
    }
    /*
     删除数据的方法
         该方法应该返回被删除的记录条数
      */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        // 记录所删除的记录数
        int num = 0;
        // 对uri进行匹配
        switch (matcher.match(uri))
        {
            // 如果Uri参数代表操作全部数据项
            case WORDS:
                num = db.delete("dict", selection, selectionArgs);
                break;
            // 如果Uri参数代表操作指定数据项
            case WORD:
                // 解析出所需要删除的记录ID
                long id = ContentUris.parseId(uri);
                String selectionClause = Words.Word._ID + "=" + id;
                // 如果原来的selection子句存在，拼接selection子句
                if (selection != null && !selection.equals(""))
                {
                    selectionClause = selectionClause + " and " + selection;
                }
                num = db.delete("dict", selectionClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        // 通知数据已经改变
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
    /*
    修改数据的方法
     该方法应该返回被更新的记录条数
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        // 记录所修改的记录数
        int num = 0;
        switch (matcher.match(uri))
        {
            // 如果Uri参数代表操作全部数据项
            case WORDS:
                num = db.update("dict", values, selection, selectionArgs);
                break;
            // 如果Uri参数代表操作指定数据项
            case WORD:
                // 解析出想修改的记录ID
                long id = ContentUris.parseId(uri);
                String selectionClause = Words.Word._ID + "=" + id;
                // 如果原来的selection子句存在，拼接selection子句
                if (selection != null && !selection.equals(""))
                {
                    selectionClause = selectionClause + " and " + selection;
                }
                num = db.update("dict", values, selectionClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri:" + uri);
        }
        // 通知数据已经改变
        getContext().getContentResolver().notifyChange(uri, null);
        return num;
       
    
    }
}
