package com.example.zhangdai.androidjson.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhangdai.androidjson.R;
import com.example.zhangdai.androidjson.javaBean.DataInfo;
import com.example.zhangdai.androidjson.javaBean.FilmData;
import com.example.zhangdai.androidjson.javaBean.ShopInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdai on 2017/2/17.
 * GSon解析界面
 */

public class GsonActivity extends Activity implements View.OnClickListener {

    private Button btGsonTojavaobject;
    private Button btGsonTojavalist;
    private Button btGsonJavatojsonobject;
    private Button btGsonJavatojsonarray;
    private TextView tvGsonOrignal;
    private TextView tvGsonLast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        //初始化view
        findViews();
    }


    private void findViews() {
        btGsonTojavaobject = (Button) findViewById(R.id.bt_gson_tojavaobject);
        btGsonTojavalist = (Button) findViewById(R.id.bt_gson_tojavalist);
        btGsonJavatojsonobject = (Button) findViewById(R.id.bt_gson_javatojsonobject);
        btGsonJavatojsonarray = (Button) findViewById(R.id.bt_gson_javatojsonarray);
        // 获取展示数据的对象
        tvGsonOrignal = (TextView) findViewById(R.id.tv_gson_orignal);
        tvGsonLast = (TextView) findViewById(R.id.tv_gson_last);

        btGsonTojavaobject.setOnClickListener(this);
        btGsonTojavalist.setOnClickListener(this);
        btGsonJavatojsonobject.setOnClickListener(this);
        btGsonJavatojsonarray.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btGsonTojavaobject) {
            // （1）将json格式的字符串{}转换为Java对象
            jsonToJavaObjectByGson();
        } else if (v == btGsonTojavalist) {
            // （2）将json格式的字符串[]转换为Java对象的List
            jsonToJavaListByGson();
        } else if (v == btGsonJavatojsonobject) {
            // （3）将Java对象转换为json字符串{}
            javaToJsonObjectByGson();
        } else if (v == btGsonJavatojsonarray) {
            // （4）将Java对象的List转换为json字符串[]
            javaToJsonArrayByGson();
        }
    }

    // （4）将Java对象的List转换为json数组[]
    private void javaToJsonArrayByGson() {

        // 1 获取或创建Java对象
        List<ShopInfo> shops = new ArrayList<>();
        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 251.0, "longxia");
        shops.add(baoyu);
        shops.add(longxia);
        // 2 生成JSON数据
        Gson gson = new Gson();
        String json = gson.toJson(shops);
        // 3 展示数据
        tvGsonOrignal.setText(shops.toString());
        tvGsonLast.setText(json);
    }

    // （3）将Java对象转换为json字符串{}
    private void javaToJsonObjectByGson() {
        // 1 获取或创建Java对象
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");

        // 2 生成JSON数据
        Gson gson = new Gson();

        String json = gson.toJson(shopInfo);
        // 3 展示数据
        tvGsonOrignal.setText(shopInfo.toString());
        tvGsonLast.setText(json);
    }

    /*
    无数据头的纯数组数据
     */
    // （2）将json格式的数组[]转换为Java对象的List
    private void jsonToJavaListByGson() {
        // 1 获取或创建JSON数据
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

        // 2 解析JSON数据
        Gson gson = new Gson();

        /*
        第一种方法
        Gson可以直接解析成一个List
         */
        List<ShopInfo> shops = gson.fromJson(json, new TypeToken<List<ShopInfo>>() {
        }.getType());
        for (int i = 0; i < shops.size(); i++) {
            String imagePath = shops.get(i).getImagePath();
            int id = shops.get(i).getId();
            String name = shops.get(i).getName();
            double price = shops.get(i).getPrice();
            Log.i("TAG", "无数据头的纯数组数据：" + "\n"
                    + "id=" + id + "\n"
                    + "name=" + name + "\n"
                    + "price=" + price + "\n"
                    + "imagePath=" + imagePath + "\n"
            );

        }

        /*
        第二种方法
         */
        //Json的解析类对象
        JsonParser jsonParser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = jsonParser.parse(json).getAsJsonArray();
        ArrayList<ShopInfo> shopss = new ArrayList<>();
        //加强for循环遍历JsonArray
        for (JsonElement jsonElement : jsonArray) {
            //使用GSON，直接转成Bean对象
            ShopInfo shopInfo = gson.fromJson(jsonElement, ShopInfo.class);
            //解析每一个数据值
            int id = shopInfo.getId();
            String name = shopInfo.getName();
            double price = shopInfo.getPrice();
            String imagePath = shopInfo.getImagePath();
            Log.e("TAG", "无数据头的纯数组数据：" + "\n"
                    + "id=" + id + "\n"
                    + "name=" + name + "\n"
                    + "price=" + price + "\n"
                    + "imagePath=" + imagePath + "\n"
            );
            shopss.add(shopInfo);
        }
        Log.i("TAG", "" + shopss);


        // 3 展示数据
        tvGsonOrignal.setText(json);
        tvGsonLast.setText(shops.toString());

    }

    /*
    有数据头的纯数组数据
     */
    public void HeadrList(View v) {
        // 1 获取或创建JSON数据
        String json = "{\n" +
                "    \"shop\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"imagePath\": \"http://192.168.10.165:8080/f1.jpg\",\n" +
                "            \"name\": \"大虾1\",\n" +
                "            \"price\": 12.3\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"imagePath\": \"http://192.168.10.165:8080/f2.jpg\",\n" +
                "            \"name\": \"大虾2\",\n" +
                "            \"price\": 12.5\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // 2 解析JSON数据
        Gson gson = new Gson();

        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("shop");
        ArrayList<ShopInfo> shopss = new ArrayList<>();
        //循环遍历
        for (JsonElement jsonElement : jsonArray) {
            ShopInfo shops = gson.fromJson(jsonElement, new TypeToken<ShopInfo>() {
            }.getType());
            Log.i("TAG", "" + shops);
            int id = shops.getId();
            String name = shops.getName();
            double price = shops.getPrice();
            String imagePath = shops.getImagePath();
            Log.i("TAG", "有数据头的纯数组数据：" + "\n"
                    + "id=" + id + "\n"
                    + "name=" + name + "\n"
                    + "price=" + price + "\n"
                    + "imagePath=" + imagePath + "\n"
            );
            shopss.add(shops);
        }
        Log.i("TAG", "" + shopss);
        // 3 展示数据
        tvGsonOrignal.setText(json);
        tvGsonLast.setText(shopss.toString());
    }

    // (1）将json格式的对象转换为Java对象
    private void jsonToJavaObjectByGson() {
        // 1 获取或创建JSON数据
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";

        // 2 解析JSON数据
        Gson gson = new Gson();

        ShopInfo shopInfo = gson.fromJson(json, ShopInfo.class);
        //3、解析每一个数据值
        int id = shopInfo.getId();
        String name = shopInfo.getName();
        double price = shopInfo.getPrice();
        String imagePath = shopInfo.getImagePath();
        Log.i("TAG", "详细数据：" + "\n"
                + "id=" + id + "\n"
                + "name=" + name + "\n"
                + "price=" + price + "\n"
                + "imagePath=" + imagePath + "\n"
        );
        // 3 展示数据
        tvGsonOrignal.setText(json);
        tvGsonLast.setText(shopInfo.toString());
    }

    /*
    解析复杂数据
     */
    public void GSONOfComplex(View view) {
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
        // 2 解析JSON数据
        Gson gson = new Gson();
        DataInfo dataInfo = gson.fromJson(json, DataInfo.class);
        String code = dataInfo.getRs_code();
        Log.i("TAG", "code:" + code);
        String mag = dataInfo.getRs_msg();
        Log.i("TAG", "mag:" + mag);
        int count = dataInfo.getData().getCount();
        Log.i("TAG", "count:" + count);
        for (int i = 0; i < dataInfo.getData().getItems().size(); i++) {
            int id = dataInfo.getData().getItems().get(i).getId();
            String title = dataInfo.getData().getItems().get(i).getTitle();
            Log.i("TAG", "详细数据：" + "\n"
                    + "id=" + id + "\n"
                    + "title=" + title + "\n"
            );
        }
        tvGsonOrignal.setText(json);
        tvGsonLast.setText(dataInfo.toString());

    }

      /*
       解析特殊数据
        */
    public void GSONOfSpecial(View view) {
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
        // 2 解析JSON数据
        Gson gson = new Gson();
        FilmData filmInfo = gson.fromJson(json, FilmData.class);
        int code = filmInfo.getCode();
        String create1 = filmInfo.getList().get_$0().getCreate();
        String copyright1 = filmInfo.getList().get_$0().getCopyright();
        int coins1= filmInfo.getList().get_$0().getCoins();
        String author1 =filmInfo.getList().get_$0().getAuthor();
        String aid1 = filmInfo.getList().get_$0().getAid();
        Log.i("TAG", "0Bean：" + "\n"
                + "aid=" + aid1 + "\n"
                + "author=" + author1 + "\n"
                + "coins=" + coins1 + "\n"
                + "copyright=" + copyright1 + "\n"
                + "create=" + create1 + "\n"
        );
        String create2 = filmInfo.getList().get_$1().getCreate();
        String copyright2 = filmInfo.getList().get_$1().getCopyright();
        int coins2= filmInfo.getList().get_$1().getCoins();
        String author2 =filmInfo.getList().get_$1().getAuthor();
        String aid2 = filmInfo.getList().get_$1().getAid();
        Log.i("TAG", "1Bean：" + "\n"
                + "aid=" + aid2 + "\n"
                + "author=" + author2 + "\n"
                + "coins=" + coins2 + "\n"
                + "copyright=" + copyright2 + "\n"
                + "create=" + create2 + "\n"
        );

        tvGsonOrignal.setText(json);
        tvGsonLast.setText(filmInfo.toString());
    }

}
