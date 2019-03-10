package com.example.administrator.devicestest.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.devicestest.R;
import com.example.administrator.devicestest.adapter.DevicesInfoAdapter;
import com.example.administrator.devicestest.utils.DevicesInfoUtils;
import com.example.administrator.devicestest.view.DevicesnfoView;

import java.util.ArrayList;
import java.util.List;


/**
 * @auther ZD
 * created at 2017/6/7 11:16
 * 作用：Android获取设备信息
 */

public class DevicesInfoActivity extends Activity implements View.OnClickListener {
    private Button btDevicesInfo;
    private List<DevicesnfoView> infolists = new ArrayList<>();
    private String[] nameData = {"手机号：", "设备ID：", "序列号：", "IMEI：", "AndroidId：",
            "运营商名称：", "运营商编号：","服务商名称:", "获取SIM卡提供的移动国家码和移动网络码：", "获取ISO国家码：",
            "MAC：", "获取当前日期时间：", "获取手机系统语言：", "获取屏幕分辩率：", "sim卡所在国家：",
            "获取当前手机型号：", "获取当前手机品牌：",

            "获取当前获取设备引导程序版本号：","获取设备指令集名称：","获取设备驱动名称：",
            "获取设备显示的版本包：","设备的唯一标识：","设备硬件名称：","设备主机地址：","设备版本号：",
            "获取设备制造商：","无线电固件版本号：","设备标签：","出厂时间：","设备版本类型：",
            "设备用户名：","获取系统版本字符串：","设备当前的系统开发代号：","系统源代码控制值：","系统的API级别：",
            "系统的API级别（数字表示）：",

            "获取当前时区：","sim卡序列号：", "IMSI：", "网络连接是否可用：",
            "手机屏幕高度：", "取手机可用内存和总内存：", "手机CPU信息：","实时获取CPU名称","实时获取CPU当前频率","获取CPU最小频率",
            "获取CPU最大频率","CPU使用率:", "前移动终端附近移动终端的信息：", "是否漫游：", "ICC卡是否存在：",
            "获取语音邮件号码：", "取得和语音邮件相关的标签：", "SIM的状态信息：", "手机类型：", "当前使用的网络类型：",
            "移动终端的软件版本：", "获取数据连接状态：", "获取数据活动状态 ：",
            "电话方位：","电池电量(%)","电池电压(mv)","电池温度","电池温度(C)","电池状态","电池使用情况"};
    private List<String> infosData = new ArrayList<>();
    private int mvoltage;
    private int mtemperature;
    private int status;
    private int health;
    private  String BatteryTemp;
    private  String BatteryStatus;
    private int battery;
    private  double mtemperatureDouble;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_info);
        findViews();
        this.registerReceiver(this.myBatteryReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED));//注册广播
    }

    private BroadcastReceiver myBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            battery  =arg1.getIntExtra("level", 0); //目前电量（0~100）
            mvoltage = arg1.getIntExtra("voltage", 0) / 1000;//电池电压(mv)
            mtemperature = arg1.getIntExtra("temperature", 0) / 10;//电池温度(数值)
            mtemperatureDouble = mtemperature/10.0; //电池摄氏温度，默认获取的非摄氏温度值，需做一下运算转换
//            status = arg1.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);//电池状态
//            health = arg1.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);//电池使用情况

            switch (arg1.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN))
            {
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    BatteryStatus = "充电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    BatteryStatus = "放电状态";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    BatteryStatus = "未充电";
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    BatteryStatus = "充满电";

                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    BatteryStatus = "未知道状态";
                    break;
            }

            switch (arg1.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN))
            {
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    BatteryTemp = "未知错误";
                    break;
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    BatteryTemp = "状态良好";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    BatteryTemp = "电池没有电";

                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    BatteryTemp = "电池电压过高";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    BatteryTemp =  "电池过热";
                    break;
            }
        }
    };

    private void inData() {
        initList();
        DevicesInfoAdapter itemAdapter = new DevicesInfoAdapter(DevicesInfoActivity.this, R.layout.view_devicesnfo, infolists);
        ListView listView = (ListView) findViewById(R.id.infolists);
        listView.setAdapter(itemAdapter);
    }

    private void initList() {
        for (int i = 0; i < nameData.length; i++) {
//            Log.i("TAG", "信息：--->" + infosData.get(i));
            DevicesnfoView devicesnfoView = new DevicesnfoView(nameData[i], infosData.get(i));
            infolists.add(devicesnfoView);
        }
    }


    private void findViews() {
        btDevicesInfo = (Button) findViewById(R.id.bt_devices_info);
        btDevicesInfo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btDevicesInfo) {
            showDevicesnfo();
            inData();
        }
    }

    private void showDevicesnfo() {
        String phoneNum = DevicesInfoUtils.getLine1Number(this);
        infosData.add(phoneNum);
        Log.i("TAG", "手机号：--->" + phoneNum);
        String DeviceId = DevicesInfoUtils.getDeviceId(this);
        infosData.add(DeviceId);
        Log.i("TAG", "设备ID：--->" + DeviceId);
        String Serial = DevicesInfoUtils.getSerial(this);
        infosData.add(Serial);
        Log.i("TAG", "序列号：--->" + Serial);
        String IMEI = DevicesInfoUtils.getLocalIMEI(this);
        infosData.add(IMEI);
        Log.i("TAG", "IMEI：--->" + IMEI);
        String AndroidId = DevicesInfoUtils.getAndroidId(this);
        infosData.add(AndroidId);
        Log.i("TAG", "AndroidId：--->" + AndroidId);
        String non = DevicesInfoUtils.getNetworkOperatorName(this);
        infosData.add(non);
        Log.i("TAG", "运营商名称：--->" + non);
        String etworkOperator = DevicesInfoUtils.getNetworkOperator(this);
        infosData.add(etworkOperator);
        Log.i("TAG", "运营商编号：--->" + etworkOperator);
        String SimOperatorName = DevicesInfoUtils.getSimOperatorName(this);
        infosData.add(SimOperatorName);
        Log.i("TAG", "服务商名称：--->" + SimOperatorName);
        String SimOperator = DevicesInfoUtils.getSimOperator(this);
        infosData.add(SimOperator);
        Log.i("TAG", "获取SIM卡提供的移动国家码和移动网络码：--->" + SimOperator);
        String SimCountryIso = DevicesInfoUtils.getSimCountryIso(this);
        infosData.add(SimCountryIso);
        Log.i("TAG", "获取ISO国家码：--->" + SimCountryIso);
        String date = DevicesInfoUtils.getMac(this);
        infosData.add(date);
        Log.i("TAG", "MAC：--->" + date);
        String mac = DevicesInfoUtils.getDateAndTime(this);
        infosData.add(mac);
        Log.i("TAG", "获取当前日期时间：--->" + mac);
        String Language = DevicesInfoUtils.getLanguage(this);
        infosData.add(Language);
        Log.i("TAG", "获取手机系统语言：--->" + Language);
        String Metrics = DevicesInfoUtils.getWeithAndHeight(this);
        infosData.add(Metrics);
        Log.i("TAG", "获取屏幕分辩率：--->" + Metrics);
        String CountryIso = DevicesInfoUtils.getNetworkCountryIso(this);
        infosData.add(CountryIso);
        Log.i("TAG", "sim卡所在国家：--->" + CountryIso);
        String PhoneModel = DevicesInfoUtils.getPhoneModel(this);
        infosData.add(PhoneModel);
        Log.i("TAG", "获取当前手机型号：--->" + PhoneModel);
        String PhoneProduct = DevicesInfoUtils.getPhoneProduct(this);
        infosData.add(PhoneProduct);
        Log.i("TAG", "获取当前手机品牌：--->" + PhoneProduct);

        String  BOOTLOADER= DevicesInfoUtils.getPhoneBOOTLOADER(this);
        infosData.add(BOOTLOADER);
        Log.i("TAG", "获取当前设备引导程序版本号：--->" + PhoneProduct);
        String CPU_ABI = DevicesInfoUtils.getPhoneCPU_ABI(this);
        infosData.add(CPU_ABI);
        Log.i("TAG", "获取设备指令集名称：--->" + PhoneProduct);
        String  DEVICE= DevicesInfoUtils.getPhoneDEVICE(this);
        infosData.add(DEVICE);
        Log.i("TAG", "获取设备驱动名称：--->" + DEVICE);
        String  DISPLAY= DevicesInfoUtils.getPhoneDISPLAY(this);
        infosData.add(DISPLAY);
        Log.i("TAG", "获取设备显示的版本包：--->" + DISPLAY);
        String  FINGERPRINT= DevicesInfoUtils.getPhoneFINGERPRINT(this);
        infosData.add(FINGERPRINT);
        Log.i("TAG", "设备的唯一标识：--->" + FINGERPRINT);
        String  HARDWARE= DevicesInfoUtils.getPhoneHARDWARE(this);
        infosData.add(HARDWARE);
        Log.i("TAG", "设备硬件名称：--->" + HARDWARE);
        String  HOST= DevicesInfoUtils.getPhoneHOST(this);
        infosData.add(HOST);
        Log.i("TAG", "设备主机地址：--->" + HOST);
        String  PhoneID= DevicesInfoUtils.getPhoneID(this);
        infosData.add(PhoneID);
        Log.i("TAG", "设备版本号：--->" + PhoneID);
        String  MANUFACTURER= DevicesInfoUtils.getPhoneMANUFACTURER(this);
        infosData.add(MANUFACTURER);
        Log.i("TAG", "获取设备制造商：--->" + MANUFACTURER);
        String  RADIO= DevicesInfoUtils.getPhoneRADIO(this);
        infosData.add(RADIO);
        Log.i("TAG", "无线电固件版本号：--->" + RADIO);
        String  TAGS= DevicesInfoUtils.getPhoneTAGS(this);
        infosData.add(TAGS);
        Log.i("TAG", "设备标签：--->" + TAGS);
        long  TIME= DevicesInfoUtils.getPhoneTIME(this);
        infosData.add(String.valueOf(TIME));
        Log.i("TAG", "获取设备出厂时间：--->" + PhoneProduct);
        String  TYPE= DevicesInfoUtils.getPhoneTYPE(this);
        infosData.add(TYPE);
        Log.i("TAG", "设备版本类型：--->" + TYPE);
        String  USER= DevicesInfoUtils.getPhoneUSER(this);
        infosData.add(USER);
        Log.i("TAG", "设备用户名：--->" + USER);
        String  RELEASE= DevicesInfoUtils.getPhoneRELEASE(this);
        infosData.add(RELEASE);
        Log.i("TAG", "获取系统版本字符串：--->" + RELEASE);
        String  CODENAME= DevicesInfoUtils.getPhoneCODENAME(this);
        infosData.add(CODENAME);
        Log.i("TAG", "设备当前的系统开发代号：--->" + CODENAME);
        String  INCREMENTAL= DevicesInfoUtils.getPhoneINCREMENTAL(this);
        infosData.add(INCREMENTAL);
        Log.i("TAG", "系统源代码控制值：--->" + INCREMENTAL);
        String  SDK= DevicesInfoUtils.getPhoneSDK(this);
        infosData.add(SDK);
        Log.i("TAG", "系统的API级别：--->" + SDK);
        int  SDK_INT= DevicesInfoUtils.getPhoneSDK_INT(this);
        infosData.add(String.valueOf(SDK_INT));
        Log.i("TAG", "系统的API级别数字表示：--->" + SDK_INT);






        String TimeZone = DevicesInfoUtils.getTimeZone(this);
        infosData.add(TimeZone);
        Log.i("TAG", "获取当前时区：--->" + TimeZone);
        String ssm = DevicesInfoUtils.getSimSerialNumber(this);
        infosData.add(ssm);
        Log.i("TAG", "sim卡序列号：--->" + ssm);
        String SubscriberId = DevicesInfoUtils.getSubscriberId(this);
        infosData.add(SubscriberId);
        Log.i("TAG", "IMSI：--->" + SubscriberId);
        boolean NetAvai = DevicesInfoUtils.isNetworkAvailable(this);
        if (NetAvai) {
            infosData.add("是");
        } else {
            infosData.add("否");
        }
        Log.i("TAG", "网络连接是否可用：--->" + NetAvai);
        String eithAndHeight = DevicesInfoUtils.getWeithAndHeight(this);
        infosData.add(eithAndHeight);
        Log.i("TAG", "手机屏幕高度：--->" + eithAndHeight);
        String SystemMemory = DevicesInfoUtils.getSystemMemory(this);
        infosData.add(SystemMemory);
        Log.i("TAG", "取手机可用内存和总内存：--->" + SystemMemory);
        String CpuInfo = DevicesInfoUtils.getCpuInfo();
        infosData.add(CpuInfo);
        Log.i("TAG", "手机CPU信息：--->" + CpuInfo);
        String CpuName = DevicesInfoUtils.getCpuName();
        infosData.add(CpuName);
        Log.i("TAG", "手机CPU名称：--->" + CpuName);
        String CpuFreq = DevicesInfoUtils.getCurCpuFreq();
        infosData.add(CpuFreq);
        Log.i("TAG", "实时获取CPU当前频：--->" + CpuFreq);
        String MinCpuFreq = DevicesInfoUtils.getMinCpuFreq();
        infosData.add(MinCpuFreq);
        Log.i("TAG", "手机CPU最小频率：--->" + MinCpuFreq);
        String MaxCpuFreq = DevicesInfoUtils.getMaxCpuFreq();
        infosData.add(MaxCpuFreq);
        Log.i("TAG", "手机CPU最大频率：--->" + MaxCpuFreq);
        int CpuRate = DevicesInfoUtils.getProcessCpuRate();
        infosData.add(String.valueOf(CpuRate));
        Log.i("TAG", "CPU使用率：--->" + CpuRate);



        String CellInfos = DevicesInfoUtils.getNeighboringCellInfos(this);
        infosData.add(CellInfos);
        Log.i("TAG", "前移动终端附近移动终端的信息：--->" + CellInfos);
        String NetworkRoaming = DevicesInfoUtils.getNetworkRoaming(this);
        infosData.add(NetworkRoaming);
        Log.i("TAG", "是否漫游：--->" + NetworkRoaming);
        String hasIccCard = DevicesInfoUtils.hasIccCard(this);
        infosData.add(hasIccCard);
        Log.i("TAG", "ICC卡是否存在：--->" + hasIccCard);
        String VoiceMailNumber = DevicesInfoUtils.getVoiceMailNumber(this);
        infosData.add(VoiceMailNumber);
        Log.i("TAG", "获取语音邮件号码：--->" + VoiceMailNumber);
        String VoiceMailAlphaTag = DevicesInfoUtils.getVoiceMailAlphaTag(this);
        infosData.add(VoiceMailAlphaTag);
        Log.i("TAG", "取得和语音邮件相关的标签：--->" + VoiceMailAlphaTag);
        String SimState = DevicesInfoUtils.getSimState(this);
        infosData.add(SimState);
        Log.i("TAG", "SIM的状态信息：--->" + SimState);

        String PhoneType = DevicesInfoUtils.getPhoneType(this);
        infosData.add(PhoneType);
        Log.i("TAG", "手机类型：--->" + PhoneType);
        String NetworkType = DevicesInfoUtils.getNetworkType(this);
        infosData.add(NetworkType);
        Log.i("TAG", "当前使用的网络类型：--->" + NetworkType);
        String DeviceSoftwareVersion = DevicesInfoUtils.getDeviceSoftwareVersion(this);
        infosData.add(DeviceSoftwareVersion);
        Log.i("TAG", "移动终端的软件版本：--->" + DeviceSoftwareVersion);
        String DataState = DevicesInfoUtils.getDataState(this);
        infosData.add(DataState);
        Log.i("TAG", "获取数据连接状态：--->" + DataState);
        String DataActivityState = DevicesInfoUtils.getDataActivityState(this);
        infosData.add(DataActivityState);
        Log.i("TAG", "获取数据活动状态 ：--->" + DataActivityState);
        String TellLocal = DevicesInfoUtils.getTellLocal(this);
        infosData.add(TellLocal);

        infosData.add(String.valueOf(battery));
        Log.i("TAG", "电池电量(%)：--->" + battery);
        infosData.add(String.valueOf(mvoltage));
        Log.i("TAG", "电池电压(mv)：--->" + mvoltage);
        infosData.add(String.valueOf(mtemperature));
        Log.i("TAG", "电池温度：--->" + mtemperature);
        infosData.add(String.valueOf(mtemperatureDouble));
        Log.i("TAG", "电池温度(Double)：--->" + mtemperatureDouble);
        infosData.add(BatteryTemp);
        Log.i("TAG", "电池状态：--->" + BatteryTemp);
        infosData.add(BatteryStatus);
        Log.i("TAG", "电池使用情况：--->" + BatteryStatus);
    }


}
