package com.example.zhangdai.androidjson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zhangdai.androidjson.Activity.FastJsonActivity;
import com.example.zhangdai.androidjson.Activity.GsonActivity;
import com.example.zhangdai.androidjson.Activity.NativeJsonPraseActivity;
import com.example.zhangdai.androidjson.Activity.OkhttpJsonActivity;
import com.example.zhangdai.androidjson.uilts.ContentHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button json;
    private Button GSON;
    private Button FastJson;
    private Button NetWoekJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();//初始化控件
    }


    private void findViews() {
        json = (Button) findViewById(R.id.json);
        GSON = (Button) findViewById(R.id.GSON);
        FastJson = (Button) findViewById(R.id.FastJson);
        NetWoekJson = (Button) findViewById(R.id.NetWoekJson);

        json.setOnClickListener(this);
        GSON.setOnClickListener(this);
        FastJson.setOnClickListener(this);
        NetWoekJson.setOnClickListener(this);
    }

    /**
     * 按钮监听处理
     */
    @Override
    public void onClick(View v) {
        if (v == json) {
            // 测解析json
            Intent jsonintent = new Intent(MainActivity.this, NativeJsonPraseActivity.class);
            startActivity(jsonintent);
        } else if (v == GSON) {
            // 测试解析Gson
            Intent jsonintent = new Intent(MainActivity.this, GsonActivity.class);
            startActivity(jsonintent);
        } else if (v == FastJson) {
            // 测试解析FastJson
            Intent jsonintent = new Intent(MainActivity.this, FastJsonActivity.class);
            startActivity(jsonintent);
        } else if (v == NetWoekJson) {
            // 测试解析网络Json
            Intent okhttpjson = new Intent(MainActivity.this, OkhttpJsonActivity.class);
            startActivity(okhttpjson);
        }
    }

    public void pull(View view) throws IOException {
        InputStream is = getAssets().open("books.xml");
        parseXMLWithPull(is);

        /*
        读取流数据
         */
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String buf = null;
        String resultxml = "";

        StringBuffer sb = new StringBuffer();
        while ((buf = reader.readLine()) != null) {
            sb.append(buf);
            resultxml = sb.toString();
            Log.i("TAG", " resultxml--->" + sb.toString());
        }
        is.close();

    }

    /*
    DOM解析
     */
    public void dom(View view) throws IOException {
        InputStream is = getAssets().open("books.xml");
        parseXMLWithDOM(is);
    }


    /*
    sax解析
     */
    public void sax(View view) throws IOException {

        InputStream is = getAssets().open("books.xml");
        parseXMLWithSAX(is);
    }

    /*
    SAX解析的操作
     */
    private void parseXMLWithSAX(InputStream xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            // 将ContentHandler的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            // 开始执行解析
            xmlReader.parse(new InputSource(xmlData));//流数据
//            xmlReader.parse(new InputSource(new StringReader(xmlData)));//字符串数据
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
   Pull解析的操作
    */
    private void parseXMLWithPull(InputStream xmlData) {

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(xmlData, "UTF-8");//流数据
//            xmlPullParser.setInput(new StringReader(xmlData));//字符串数据
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String price = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    // 开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("price".equals(nodeName)) {
                            price = xmlPullParser.nextText();
                        }
                        break;
                    }
                    // 完成解析某个结点
                    case XmlPullParser.END_TAG: {
                        if ("book".equals(nodeName)) {
                            Log.e("TAG", "Pull解析的子节点数据：" + "\n" +
                                    "id ：" + id + "\n" +
                                    "name ： " + name + "\n" +
                                    "price ： " + price);

                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    DOM解析的操作
     */
    private void parseXMLWithDOM(InputStream xmlData) {
        String id = "";
        String name = "";
        String price = "";
        //创建解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            //创建解析器
            DocumentBuilder builder = factory.newDocumentBuilder();
            //获得Document对象
            Document document = builder.parse(xmlData);//流数据
//            Document document = builder.parse(new InputSource(new StringReader(xmlData)));//字符串数据
            NodeList bookList = document.getElementsByTagName("book");
            for (int i = 0; i < bookList.getLength(); i++) {
                //获得子节点的List
                Node node_bookList = bookList.item(i);
                //获得子节点List里面的节点
                NodeList childNodes = node_bookList.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    //判断是name还是nickName
                    if ("id".equals(childNode.getNodeName())) {
                        id = childNode.getTextContent();
                    } else if ("name".equals(childNode.getNodeName())) {
                        name = childNode.getTextContent();
                    } else if ("price".equals(childNode.getNodeName())) {
                        price = childNode.getTextContent();
                    }

                }
                Log.e("TAG", "DOM解析的子节点数据：" + "\n" +
                        "id ：" + id + "\n" +
                        "name ： " + name + "\n" +
                        "price ： " + price);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
