package com.example.administrator.countdownviewtest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.iwgang.countdownview.CountdownView;

/**
*在ListView中使用CountdownView控件
*@author ZD
*created at 2017/6/29 16:15
*description：
*/ 
public class CountdownViewListViewActivity extends AppCompatActivity {
    private List<ItemInfo> mDataList;
    private MyListAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_view_list_view);
        initData();

        ListView lvList = (ListView) findViewById(R.id.lv_list);
        lvList.setAdapter(mMyAdapter = new MyListAdapter(this, mDataList));
    }

    /**
     * 准备数据
     */
    private void initData() {
        mDataList = new ArrayList<>();

        for (int i = 1; i < 20; i++) {
            mDataList.add(new ItemInfo(1000 + i, "ListView_测试标题_" + i, i * 20 * 1000));
        }

        // 校对倒计时
        long curTime = System.currentTimeMillis();//从1990
        for (ItemInfo itemInfo : mDataList) {
            //什么时候结束的时间
            itemInfo.setEndTime(curTime + itemInfo.getCountdown());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mMyAdapter) {
            mMyAdapter.startRefreshTime();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mMyAdapter) {
            mMyAdapter.cancelRefreshTime();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mMyAdapter) {
            mMyAdapter.cancelRefreshTime();
        }
    }

    /**
     * listview适配器
     */
    static class MyListAdapter extends BaseAdapter {
        private Context mContext;
        /**
         * 时间集合
         */
        private List<ItemInfo> mDatas;
        /**
         * 相当于HashMap
         * 防止下拉刷新时时间错乱
         */
        private final SparseArray<MyViewHolder> mCountdownVHList;
        private Handler mHandler = new Handler();
        private Timer mTimer;
        private boolean isCancel = true;

        public MyListAdapter(Context context, List<ItemInfo> datas) {
            this.mContext = context;
            this.mDatas = datas;
            mCountdownVHList = new SparseArray<>();
            startRefreshTime();
        }

        public void startRefreshTime() {
            if (!isCancel) return;

            if (null != mTimer) {
                mTimer.cancel();
            }

            isCancel = false;
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(mRefreshTimeRunnable);
                }
            }, 0, 10);
        }

        public void cancelRefreshTime() {
            isCancel = true;
            if (null != mTimer) {
                mTimer.cancel();
            }
            mHandler.removeCallbacks(mRefreshTimeRunnable);
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
                holder = new MyViewHolder();
                //初始化视图
                holder.initView(convertView);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

            //更加位置得到对应的时间对象
            ItemInfo curItemInfo = mDatas.get(position);
            holder.bindData(curItemInfo);

            // 处理倒计时
            if (curItemInfo.getCountdown() > 0) {
                synchronized (mCountdownVHList) {
                    mCountdownVHList.put(curItemInfo.getId(), holder);
                }
            }

            return convertView;
        }

        private Runnable mRefreshTimeRunnable = new Runnable() {
            @Override
            public void run() {
                if (mCountdownVHList.size() == 0) return;

                synchronized (mCountdownVHList) {
                    long currentTime = System.currentTimeMillis();
                    int key;
                    for (int i = 0; i < mCountdownVHList.size(); i++) {
                        key = mCountdownVHList.keyAt(i);
                        MyViewHolder curMyViewHolder = mCountdownVHList.get(key);
                        if (currentTime >= curMyViewHolder.getBean().getEndTime()) {
                            // 倒计时结束
                            curMyViewHolder.getBean().setCountdown(0);
                            mCountdownVHList.remove(key);
                            notifyDataSetChanged();
                        } else {
                            //更新时间
                            curMyViewHolder.refreshTime(currentTime);
                        }

                    }
                }
            }
        };

        static class MyViewHolder {
            private TextView mTvTitle;
            private CountdownView mCvCountdownView;
            /**
             * 时间对象
             */
            private ItemInfo mItemInfo;

            public void initView(View convertView) {
                mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                mCvCountdownView = (CountdownView) convertView.findViewById(R.id.cv_countdownView);
            }

            public void bindData(ItemInfo itemInfo) {
                mItemInfo = itemInfo;

                if (itemInfo.getCountdown() > 0) {
                    refreshTime(System.currentTimeMillis());
                } else {
                    mCvCountdownView.allShowZero();
                }

                mTvTitle.setText(itemInfo.getTitle());
            }

            /**
             * 更新时间
             * @param curTimeMillis
             */
            public void refreshTime(long curTimeMillis) {
                if (null == mItemInfo || mItemInfo.getCountdown() <= 0) return;

                mCvCountdownView.updateShow(mItemInfo.getEndTime() - curTimeMillis);
            }

            public ItemInfo getBean() {
                return mItemInfo;
            }
        }
    }

    static class ItemInfo {
        private int id;
        private String title;
        /**
         * 倒计时持续的时间
         */
        private long countdown;
        /*
           根据服务器返回的countdown换算成手机对应的开奖时间 (毫秒)
           [正常情况最好由服务器返回countdown字段，然后客户端再校对成该手机对应的时间，不然误差很大]
         */
        private long endTime;

        public ItemInfo(int id, String title, long countdown) {
            this.id = id;
            this.title = title;
            this.countdown = countdown;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getCountdown() {
            return countdown;
        }

        public void setCountdown(long countdown) {
            this.countdown = countdown;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }
    }
}
