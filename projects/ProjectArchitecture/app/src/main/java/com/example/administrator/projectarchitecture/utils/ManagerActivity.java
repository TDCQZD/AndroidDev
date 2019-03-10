package com.example.administrator.projectarchitecture.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:Activity管理类
 * @author: Administrator
 * @Date: 2017/9/6 11:26
 * 可以支持完全退出，不过是否能够完全退出?
 * 肯定是不行，如果应用中有一些service，可能会重启
 */

public class ManagerActivity {
    public static ManagerActivity instance = new ManagerActivity();
    private List<Activity> mLists = new ArrayList<Activity>();
    private ManagerActivity() {
    }
    public synchronized static ManagerActivity getInstance() {

        return instance;
    }

    /**
     * 往集合中添加一个Activity
     * @param pActivity
     */
    public void addActivity(Activity pActivity) {
        if (pActivity != null) {
            mLists.add(pActivity);
        }
    }

    /**
     * 从集合中删除一个Activity
     * @param pActivity  需要删除的Activity
     */
    public void removeActivity(Activity pActivity) {
        if (pActivity != null) {
            if (mLists.contains(pActivity)) {
                mLists.remove(pActivity);
                pActivity.finish();
                pActivity = null;
            }
        }
    }

    //从栈中进行删除集合顶得Activity
    public void popActivity() {
        Activity activity = mLists.get(mLists.size() - 1);
        removeActivity(activity);
    }
    public int getNum() {
        return mLists.size();
    }
    /**
     * 完全删除集合中
     */
    public void finishActivity() {
        if (mLists != null && mLists.size() >= 0) {
            for (Activity pActivity : mLists) {
                pActivity.finish();
                pActivity = null;
            }
        }
    }
}
