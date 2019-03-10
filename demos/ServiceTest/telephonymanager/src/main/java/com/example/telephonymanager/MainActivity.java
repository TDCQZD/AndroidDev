package com.example.telephonymanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 电话管理器——获取网络和SIM卡信息
 *
 * @author ZD
 *         created at 2017/8/7 15:30
 *         description：
 */
public class MainActivity extends AppCompatActivity {
    ListView showView;
    // 声明代表状态名的数组
    String[] statusNames;
    // 声明代表手机状态的集合
    ArrayList<String> statusValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void block(View view) {
        startActivity(new Intent(MainActivity.this, TelePhoneBlockActivity.class));
    }

    public void monitorPhone(View view) {
        startActivity(new Intent(MainActivity.this, MonitorPhoneActivity.class));
    }

    public void getNetAndSIM(View view) {
        // 获取系统的TelephonyManager对象
        TelephonyManager tManager = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        // 获取各种状态名称的数组
        statusNames = getResources().getStringArray(R.array.statusNames);
        // 获取代表SIM卡状态的数组
        String[] simState = getResources()
                .getStringArray(R.array.simState);
        // 获取代表电话网络类型的数组
        String[] phoneType = getResources().getStringArray(
                R.array.phoneType);
        // 获取设备编号
        statusValues.add(tManager.getDeviceId());
        // 获取系统平台的版本
        statusValues.add(tManager.getDeviceSoftwareVersion()
                != null ? tManager.getDeviceSoftwareVersion() : "未知");
        // 获取网络运营商代号
        statusValues.add(tManager.getNetworkOperator());
        // 获取网络运营商名称
        statusValues.add(tManager.getNetworkOperatorName());
        // 获取手机网络类型
        statusValues.add(phoneType[tManager.getPhoneType()]);
        // 获取设备所在位置
        statusValues.add(tManager.getCellLocation() != null ? tManager
                .getCellLocation().toString() : "未知位置");
        // 获取SIM卡的国别
        statusValues.add(tManager.getSimCountryIso());
        // 获取SIM卡序列号
        statusValues.add(tManager.getSimSerialNumber());
        // 获取SIM卡状态
        statusValues.add(simState[tManager.getSimState()]);
        // 获得ListView对象
        showView = (ListView) findViewById(R.id.show);
        ArrayList<Map<String, String>> status = new ArrayList<>();
        // 遍历statusValues集合，将statusNames、statusValues
        // 的数据封装到List<Map<String , String>>集合中
        for (int i = 0; i < statusValues.size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", statusNames[i]);
            map.put("value", statusValues.get(i));
            status.add(map);
        }
        // 使用SimpleAdapter封装List数据
        SimpleAdapter adapter = new SimpleAdapter(this, status,
                R.layout.line, new String[]{"name", "value"}
                , new int[]{R.id.name, R.id.value});
        // 为ListView设置Adapter
        showView.setAdapter(adapter);
    }
}
