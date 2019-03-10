package com.example.zhangdai.androidjson.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.zhangdai.androidjson.R;
import com.example.zhangdai.androidjson.javaBean.DataInfo;
import com.example.zhangdai.androidjson.javaBean.FilmData;
import com.example.zhangdai.androidjson.javaBean.ShopInfo;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by zhangdai on 2017/2/17.
 * FastJson解析
 * Fastjson是一个Java语言编写的JSON处理器,由阿里巴巴公司开发
 */
public class FastJsonActivity extends Activity implements View.OnClickListener {
    private Button btFastjsonTojavaobject;
    private Button btFastjsonTojavalist;
    private Button btFastjsonJavatojsonobject;
    private Button btFastjsonJavatojsonarray;
    private TextView tvFastjsonOrignal;
    private TextView tvFastjsonLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastjson);
        //初始化view
        findViews();
    }


    private void findViews() {
        btFastjsonTojavaobject = (Button) findViewById(R.id.bt_fastjson_tojavaobject);
        btFastjsonTojavalist = (Button) findViewById(R.id.bt_fastjson_tojavalist);
        btFastjsonJavatojsonobject = (Button) findViewById(R.id.bt_fastjson_javatojsonobject);
        btFastjsonJavatojsonarray = (Button) findViewById(R.id.bt_fastjson_javatojsonarray);
        // 获取展示数据的对象
        tvFastjsonOrignal = (TextView) findViewById(R.id.tv_fastjson_orignal);
        tvFastjsonLast = (TextView) findViewById(R.id.tv_fastjson_last);

        btFastjsonTojavaobject.setOnClickListener(this);
        btFastjsonTojavalist.setOnClickListener(this);
        btFastjsonJavatojsonobject.setOnClickListener(this);
        btFastjsonJavatojsonarray.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btFastjsonTojavaobject) {
            // （1）将json格式的字符串{}转换为Java对象
            jsonToJavaObjectByFastJson();
        } else if (v == btFastjsonTojavalist) {
            // （2）将json格式的字符串[]转换为Java对象的List
            jsonToJavaListByFastJson();
        } else if (v == btFastjsonJavatojsonobject) {
            // （3）将Java对象转换为json字符串{}
            javaToJsonObjectByFastJson();
        } else if (v == btFastjsonJavatojsonarray) {
            // （4）将Java对象的List转换为json字符串[]
            javaToJsonArrayByFastJson();
        }
    }


    // （4）将Java对象的List转换为json字符串[]
    private void javaToJsonArrayByFastJson() {
        // 1 创建一个Java集合
        List<ShopInfo> shops = new ArrayList<>();

        ShopInfo baoyu = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");
        ShopInfo longxia = new ShopInfo(2, "龙虾", 251.0, "longxia");

        shops.add(baoyu);
        shops.add(longxia);

        // 2 生成JSON数据
        String json = JSON.toJSONString(shops);

        // 3 显示JSON数据
        tvFastjsonOrignal.setText(shops.toString());
        tvFastjsonLast.setText(json);
    }

    // （3）将Java对象转换为json字符串{}
    private void javaToJsonObjectByFastJson() {
        // 1 创建一个Java对象
        ShopInfo shopInfo = new ShopInfo(1, "鲍鱼", 250.0, "baoyu");

        // 2 生成JSON数据
        String json = JSON.toJSONString(shopInfo);

        // 3 显示数据
        tvFastjsonOrignal.setText(shopInfo.toString());
        tvFastjsonLast.setText(json);
    }

    // （2）将json格式的字符串[]转换为Java对象的List
    private void jsonToJavaListByFastJson() {

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
        List<ShopInfo> shops = JSON.parseArray(json, ShopInfo.class);

        for (int i = 0; i < shops.size(); i++) {

            String imagePath = shops.get(i).getImagePath();
            String name = shops.get(i).getName();
            double price = shops.get(i).getPrice();
            int id = shops.get(i).getId();
            Log.i("TAG", "详细数据：" + "\n"
                    + "id=" + id + "\n"
                    + "name=" + name + "\n"
                    + "price=" + price + "\n"
                    + "imagePath=" + imagePath + "\n"
            );
        }
        // 3 显示数据
        tvFastjsonOrignal.setText(json);
        tvFastjsonLast.setText(shops.toString());

    }


    // （1）将json格式的字符串{}转换为Java对象
    private void jsonToJavaObjectByFastJson() {
        // 1 获取或创建json数据
        String json = "{\n" +
                "\t\"id\":2, \"name\":\"大虾\", \n" +
                "\t\"price\":12.3, \n" +
                "\t\"imagePath\":\"http://192.168.10.165:8080/L05_Server/images/f1.jpg\"\n" +
                "}\n";

        // 2 解析JSON数据
        ShopInfo shopInfo = parseObject(json, ShopInfo.class);
        String imagePath = shopInfo.getImagePath();
        String name = shopInfo.getName();
        double price = shopInfo.getPrice();
        int id = shopInfo.getId();
        Log.i("TAG", "详细数据：" + "\n"
                + "id=" + id + "\n"
                + "name=" + name + "\n"
                + "price=" + price + "\n"
                + "imagePath=" + imagePath + "\n"
        );
        // 3 显示数据
        tvFastjsonOrignal.setText(json);
        tvFastjsonLast.setText(shopInfo.toString());
    }

    /*
       解析复杂数据
        */
    public void fastjsonOfComplex(View view) {
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

        DataInfo dataInfo = parseObject(json, DataInfo.class);
        String mag = dataInfo.getRs_msg();
        Log.i("TAG", "mag:" + mag);
        String code = dataInfo.getRs_code();
        Log.i("TAG", "code:" + code);
        int count = dataInfo.getData().getCount();
        Log.i("TAG", "count:" + count);

        for (int i = 0; i < dataInfo.getData().getItems().size(); i++) {
            String title = dataInfo.getData().getItems().get(i).getTitle();
            int id = dataInfo.getData().getItems().get(i).getId();
            Log.i("TAG", "详细数据：" + "\n"
                    + "id=" + id + "\n"
                    + "title=" + title + "\n"
            );
        }
        tvFastjsonOrignal.setText(json);
        tvFastjsonLast.setText(dataInfo.toString());
    }

    /*
       解析特殊数据
        */
    public void fastjsonOfSpecial(View view) {
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



        FilmData filmData = parseObject(json, FilmData.class);
        FilmData.ListBean list = filmData.getList();

        int code = filmData.getCode();
        list.getClass() ;
        Log.i("TAG", "" + code);
        Log.i("TAG", "" + list.get_$0());
//        Log.i("TAG", "" + list.get_$1().getCreate());
//        filmData.getList().get_$0();
//        String create1 = filmData.getList().get_$0().getCreate();
//        String copyright1 = filmData.getList().get_$0().getCopyright();
//        int coins1 = filmData.getList().get_$0().getCoins();
//        String author1 = filmData.getList().get_$0().getAuthor();
//        String aid1 = filmData.getList().get_$0().getAid();
//        Log.i("TAG", "0Bean：" + "\n"
//                + "aid=" + aid1 + "\n"
//                + "author=" + author1 + "\n"
//                + "coins=" + coins1 + "\n"
//                + "copyright=" + copyright1 + "\n"
//                + "create=" + create1 + "\n"
//        );
//        String create2 = filmData.getList().get_$1().getCreate();
//        String copyright2 = filmData.getList().get_$1().getCopyright();
//        int coins2 = filmData.getList().get_$1().getCoins();
//        String author2 = filmData.getList().get_$1().getAuthor();
//        String aid2 = filmData.getList().get_$1().getAid();
//        Log.i("TAG", "1Bean：" + "\n"
//                + "aid=" + aid2 + "\n"
//                + "author=" + author2 + "\n"
//                + "coins=" + coins2 + "\n"
//                + "copyright=" + copyright2 + "\n"
//                + "create=" + create2 + "\n"
//        );

    }


}
