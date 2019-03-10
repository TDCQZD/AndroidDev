package com.example.zhangdai.androidjson.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangdai.androidjson.R;
import com.example.zhangdai.androidjson.javaBean.DataInfo;
import com.example.zhangdai.androidjson.javaBean.FilmInfo;
import com.example.zhangdai.androidjson.javaBean.ShopInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhangdai.androidjson.R.id.tv_native_last;
import static com.example.zhangdai.androidjson.R.id.tv_native_orignal;

/**
 * Created by zhangdai on 2017/2/16.
 * 手动JSON解析页面
 * (1）将json格式的字符串{}转换为Java对象
 * （2）将json格式的字符串[]转换为Java对象的List
 * （3）复杂json数据解析
 * （4）特殊json数据解析
 */

public class NativeJsonPraseActivity extends Activity implements View.OnClickListener {
    private Button btNativeTojavaobject;
    private Button btNativeTojavalist;
    private Button btNativeComplex;
    private Button btNativeSpecial;
    private Button btJsonJavatojsonobject;
    private Button btJsonJavatojsonarray;
    private TextView tvNativeOrignal;
    private TextView tvNativeLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        findViews();
    }

    private void findViews() {

        btNativeTojavaobject = (Button) findViewById(R.id.bt_native_tojavaobject);
        btNativeTojavalist = (Button) findViewById(R.id.bt_native_tojavalist);
        btNativeComplex = (Button) findViewById(R.id.bt_native_complex);
        btNativeSpecial = (Button) findViewById(R.id.bt_native_special);
        // 获取显示数据的textView对象
        tvNativeOrignal = (TextView) findViewById(tv_native_orignal);
        tvNativeLast = (TextView) findViewById(tv_native_last);

        btNativeTojavaobject.setOnClickListener(this);
        btNativeTojavalist.setOnClickListener(this);
        btNativeComplex.setOnClickListener(this);
        btNativeSpecial.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btNativeTojavaobject) {
            // (1)将json对象转换为Java对象
            jsonToJavaObject();
        } else if (v == btNativeTojavalist) {
            // (2)将json格式的数组转换为Java对象的List（集合）
            jsonToJavaList();
        } else if (v == btNativeComplex) {
            //(3)复杂json数据解析
            jsonToJavaOfComplex();
        } else if (v == btNativeSpecial) {
            // (4)特殊json数据解析
            jsonToJavaOfSpecial();
        }

    }

    // (4)特殊json数据解析
    private void jsonToJavaOfSpecial() {

        // 1 获取或创建JSON数据
        String json = "{\n" +
                "    \"code\": 0,\n" +
                "    \"list\": {\n" +
                "        \"0\": {\n" +
                "            \"aid\": \"6008965\",\n" +
                "            \"author\": \"哔哩哔哩番剧\",\n" +
                "            \"coins\": 170,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-25 21:34\"\n" +
                "        },\n" +
                "        \"1\": {\n" +
                "            \"aid\": \"6008938\",\n" +
                "            \"author\": \"哔哩哔哩番剧\",\n" +
                "            \"coins\": 404,\n" +
                "            \"copyright\": \"Copy\",\n" +
                "            \"create\": \"2016-08-25 21:33\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // 创建封装的Java对象
        FilmInfo filmInfo = new FilmInfo();

        // 2 解析json
        try {
            JSONObject jsonObject = new JSONObject(json);

            // 第一层解析
            int code = jsonObject.optInt("code");
            JSONObject list = jsonObject.optJSONObject("list");

            // 第一层封装
            filmInfo.setCode(code);
            List<FilmInfo.FilmBean> lists = new ArrayList<>();
            filmInfo.setList(lists);

            // 第二层解析
            for (int i = 0; i < list.length(); i++) {
                JSONObject jsonObject1 = list.optJSONObject(i + "");

                if (jsonObject1 != null) {
                    String aid = jsonObject1.optString("aid");

                    String author = jsonObject1.optString("author");

                    int coins = jsonObject1.optInt("coins");

                    String copyright = jsonObject1.optString("copyright");

                    String create = jsonObject1.optString("create");

                    // 第二层数据封装
                    FilmInfo.FilmBean filmBean = new FilmInfo.FilmBean();
                    filmBean.setAid(aid);
                    filmBean.setAuthor(author);
                    filmBean.setCoins(coins);
                    filmBean.setCopyright(copyright);
                    filmBean.setCreate(create);

                    lists.add(filmBean);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 3 显示JSON数据
        tvNativeOrignal.setText(json);
        tvNativeOrignal.setText(filmInfo.toString());
    }


    // (3)复杂json数据解析
    private void jsonToJavaOfComplex() {

        // 1 获取或创建JSON数据
        String json = "{\n" +
                "    \"data\": {\n" +
                "        \"count\": 5,\n" +
                "        \"items\": [\n" +
                "            {\n" +
                "                \"id\": 45,\n" +
                "                \"title\": \"坚果\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 132,\n" +
                "                \"title\": \"炒货\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 166,\n" +
                "                \"title\": \"蜜饯\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 195,\n" +
                "                \"title\": \"果脯\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 196,\n" +
                "                \"title\": \"礼盒\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"rs_code\": \"1000\",\n" +
                "    \"rs_msg\": \"success\"\n" +
                "}";

        // 封装Java对象
        DataInfo dataInfo = new DataInfo();

        // 2 解析json
        try {
            JSONObject jsonObject = new JSONObject(json);

            // 第一层解析，解析JSON对象
            JSONObject data = jsonObject.optJSONObject("data");
            String rs_code = jsonObject.optString("rs_code");
            String rs_msg = jsonObject.optString("rs_msg");

            // 第一层封装
            dataInfo.setRs_code(rs_code);
            dataInfo.setRs_msg(rs_msg);
            DataInfo.DataBean dataBean = new DataInfo.DataBean();
            dataInfo.setData(dataBean);

            // 第二层解析
            int count = data.optInt("count");
            JSONArray items = data.optJSONArray("items");

            // 第二层数据的封装
            dataBean.setCount(count);

            List<DataInfo.DataBean.ItemsBean> itemsBean = new ArrayList<>();
            dataBean.setItems(itemsBean);

            // 第三层解析，解析对象中数组
            for (int i = 0; i < items.length(); i++) {
                JSONObject jsonObject1 = items.optJSONObject(i);

                if (jsonObject1 != null) {
                    int id = jsonObject1.optInt("id");

                    String title = jsonObject1.optString("title");

                    // 第三层数据的封装
                    DataInfo.DataBean.ItemsBean bean = new DataInfo.DataBean.ItemsBean();
                    bean.setId(id);
                    bean.setTitle(title);

                    itemsBean.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 3 显示JSON数据
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(dataInfo.toString());

    }

    // 2、将json格式的字符串[]转换为Java对象的List
    private void jsonToJavaList() {
        // a.获取或创建JSON数据
        String json = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f1.jpg\",\n" +
                "        \"name\": \"大虾1\",\n" +
                "        \"price\": 12.3\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"imagePath\": \"http://192.168.10.165:8080/f2.jpg\",\n" +
                "        \"name\": \"大虾2\",\n" +
                "        \"price\": 12.5\n" +
                "    }\n" +
                "]";
        // b. 解析json

        List<ShopInfo> shops = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //判断 JSONObject非空，否则无法显示数据
                if (jsonObject != null) {
                    int id = jsonObject.optInt("id");

                    String name = jsonObject.optString("name");

                    double price = jsonObject.optDouble("price");

                    String imagePath = jsonObject.optString("imagePath");

                    // 封装Java对象
                    ShopInfo shopInfo = new ShopInfo(id, name, price, imagePath);
                    //将数组元素添加到Java集合中
                    shops.add(shopInfo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // c. 显示JSON数据
        tvNativeOrignal.setText(json);
        tvNativeLast.setText(shops.toString());
    }


    // 1、将json格式的对象转换为Java对象
    private void jsonToJavaObject() {
        //获取json字符串
        String jsonData = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}";
        ShopInfo shopInfo = new ShopInfo();
        //解析json
        try {
            //获取JsonObject对象
            JSONObject jsonObject = new JSONObject(jsonData);
            /**获取数据
             * getInt("key")  取值 不存在 或者类型不对 报错
             optInt("key")  取值 不存在 返回默认值
             */
            int id = jsonObject.optInt("id");
            String name = jsonObject.optString("name");
            Double price = jsonObject.optDouble("price");
            String imagePath = jsonObject.optString("imagePath");
            //封装成Java对象
            shopInfo = new ShopInfo(id, name, price, imagePath);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 显示JSON数据
        tvNativeOrignal.setText(jsonData);
        tvNativeLast.setText(shopInfo.toString());
    }


}
