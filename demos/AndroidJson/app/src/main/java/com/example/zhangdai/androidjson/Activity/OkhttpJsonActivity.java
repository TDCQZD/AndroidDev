package com.example.zhangdai.androidjson.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangdai.androidjson.R;
import com.example.zhangdai.androidjson.adapter.OKHttpListAdapter;
import com.example.zhangdai.androidjson.javaBean.DataBean;
import com.example.zhangdai.androidjson.uilts.CacheUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import static com.zhy.http.okhttp.log.LoggerInterceptor.TAG;

/**
 * Created by zhangdai on 2017/2/19.
 * Android网络解析Json数据
 */
public class OkhttpJsonActivity extends Activity {
    private ListView listview;
    private ProgressBar progressBar;
    private TextView tvNodata;
    private OKHttpListAdapter adapter;
    String urljson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttpjson);
        findViews();
        getDataFromNet();//获取网络数据
    }

    //获取网络数据
    private void getDataFromNet() {
       urljson = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        //得到缓存的数据
        String cachedata = CacheUtils.getString(this, urljson);
        if(!TextUtils.isEmpty(urljson)) {
            processData(urljson);
        }
        //网络请求
        OkHttpUtils
                .post()
                .url(urljson)
                .id(100)
                .build()
                .execute(new MyStringCallback());

    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tvNodata.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tvNodata.setVisibility(View.GONE);

            switch (id) {
                case 100:
                    Toast.makeText(OkhttpJsonActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OkhttpJsonActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
            //解析数据和显示数据
            if(response != null){
                //缓存数据
                CacheUtils.putString(OkhttpJsonActivity.this,urljson,response);
                processData(response);

            }
        }



        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    /**
     * 解析和显示数据
     * @param urljson
     */
    private void processData(String urljson) {

//解析数据
        DataBean dataBean = parsedJson(urljson);
        List<DataBean.ItemData> datas =  dataBean.getTrailers();

        if(datas != null && datas.size() >0){
            //有数据
            tvNodata.setVisibility(View.GONE);
            //显示适配器
            adapter = new OKHttpListAdapter(OkhttpJsonActivity.this,datas);
            listview.setAdapter(adapter);
        }else{
            //没有数据
            tvNodata.setVisibility(View.VISIBLE);
        }

        progressBar.setVisibility(View.GONE);
    }
    /**
     * 解析json数据
     *
     * @param response
     * @return
     */
    private DataBean parsedJson(String response) {
        DataBean dataBean = new DataBean();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("trailers");
            if (jsonArray != null && jsonArray.length() > 0) {
                List<DataBean.ItemData> trailers = new ArrayList<>();
                dataBean.setTrailers(trailers);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObjectItem = (JSONObject) jsonArray.get(i);

                    if (jsonObjectItem != null) {

                        DataBean.ItemData mediaItem = new DataBean.ItemData();

                        String movieName = jsonObjectItem.optString("movieName");//name
                        mediaItem.setMovieName(movieName);

                        String videoTitle = jsonObjectItem.optString("videoTitle");//desc
                        mediaItem.setVideoTitle(videoTitle);

                        String imageUrl = jsonObjectItem.optString("coverImg");//imageUrl
                        mediaItem.setCoverImg(imageUrl);

                        String hightUrl = jsonObjectItem.optString("hightUrl");//data
                        mediaItem.setHightUrl(hightUrl);

                        //把数据添加到集合
                        trailers.add(mediaItem);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataBean;
    }

    private void findViews() {
        listview = (ListView) findViewById(R.id.listview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvNodata = (TextView) findViewById(R.id.tv_nodata);
    }

}
