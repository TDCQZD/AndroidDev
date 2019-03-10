package com.example.administrator.devicestest.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.TELEPHONY_SERVICE;
/**
 * @author ZD
 * created at 2017/6/8 10:37
 * description：手机设备信息工具类
 */

/**
 * 一、Android 获取手机中已安装apk文件信息(PackageInfo、ResolveInfo)(应用图片、应用名、包名等)
 * 1、通过PackageManager可获取手机端已安装的apk文件的信息，具体代码如下: PackageManager
 * packageManager = this.getPackageManager(); List<PackageInfo>
 * packageInfoList = packageManager.getInstalledPackages(0);
 * 通过上述方法，可得到手机中安装的所有应用程序，包括手动安装的apk包的信息、、系统预装的应用软件的信息，要区分这两类软件可使用以下方法:
 * （a）从packageInfoList获取的packageInfo
 * ，再通过packageInfo.applicationInfo获取applicationInfo。
 * （b）判断(applicationInfo.flags &
 * ApplicationInfo.FLAG_SYSTEM)的值，该值大于0时，表示获取的应用为系统预装的应用，反之则为手动安装的应用。
 * (1)获取应用的代码: public static List<PackageInfo> getAllApps(Context context) {
 * List<PackageInfo> apps = new ArrayList<PackageInfo>(); PackageManager
 * pManager = context.getPackageManager(); //获取手机内所有应用 List<PackageInfo>
 * paklist = pManager.getInstalledPackages(0); for (int i = 0; i <
 * paklist.size(); i++) { PackageInfo pak = (PackageInfo) paklist.get(i);
 * //判断是否为非系统预装的应用程序 if ((pak.applicationInfo.flags &
 * pak.applicationInfo.FLAG_SYSTEM) <= 0) { apps.add(pak); } } return apps;
 * } (2)、获取图片、应用名、包名: PackageManager pManager =
 * MessageSendActivity.this.getPackageManager(); List<PackageInfo> appList =
 * Utils.getAllApps(MessageSendActivity.this); for(int
 * i=0;i<appList.size();i++) { PackageInfo pinfo = appList.get(i); shareItem
 * = new ShareItemInfo();
 * shareItem.setIcon(pManager.getApplicationIcon(pinfo.applicationInfo));
 * shareItem
 * .setLabel(pManager.getApplicationLabel(pinfo.applicationInfo).toString
 * ()); shareItem.setPackageName(pinfo.applicationInfo.packageName); }
 * 其中ShareItemInfo 类自己写的，各位可以忽略 (3)获取支持分享的应用的代码： public static
 * List<ResolveInfo> getShareApps(Context context){ List<ResolveInfo> mApps
 * = new ArrayList<ResolveInfo>(); Intent intent=new
 * Intent(Intent.ACTION_SEND,null);
 * intent.addCategory(Intent.CATEGORY_DEFAULT);
 * intent.setType("text/plain"); PackageManager pManager =
 * context.getPackageManager(); mApps =
 * pManager.queryIntentActivities(intent
 * ,PackageManager.COMPONENT_ENABLED_STATE_DEFAULT); return mApps; }
 * 由于该方法，返回的并不是PackageInfo 对象。而是ResolveInfo。因此获取图片、应用名、包名的方法不一样，如下：
 * PackageManager pManager = MessageSendActivity.this.getPackageManager();
 * List<ResolveInfo> resolveList =
 * Utils.getShareApps(MessageSendActivity.this); for(int
 * i=0;i<resolveList.size();i++) { ResolveInfo resolve = resolveList.get(i);
 * ShareItemInfo shareItem = new ShareItemInfo(); //set Icon
 * shareItem.setIcon(resolve.loadIcon(pManager)); //set Application Name
 * shareItem.setLabel(resolve.loadLabel(pManager).toString()); //set Package
 * Name shareItem.setPackageName(resolve.activityInfo.packageName); } 总结： 通过
 * PackageInfo 获取具体信息方法： 包名获取方法：packageInfo.packageName
 * icon获取获取方法：packageManager.getApplicationIcon(applicationInfo)
 * 应用名称获取方法：packageManager.getApplicationLabel(applicationInfo)
 * 使用权限获取方法：packageManager
 * .getPackageInfo(packageName,PackageManager.GET_PERMISSIONS)
 * .requestedPermissions 通过 ResolveInfo 获取具体信息方法：
 * 包名获取方法：resolve.activityInfo.packageName
 * icon获取获取方法：resolve.loadIcon(packageManager)
 * 应用名称获取方法：resolve.loadLabel(packageManager).toString()
 */
public class DevicesInfoUtils {

    /**
     * 手机号： GSM手机的 MSISDN
     */

    public static String getLine1Number(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getLine1Number();
    }

    /**
     * 返回当前移动终端的唯一标识，设备ID
     * 如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID ；如果设备ID不可用，则返回null.
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getDeviceId();
    }

    /**
     * IMSI
     * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a GSM phone. 需要权限：READ_PHONE_STATE
     */
    public static String getSubscriberId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSubscriberId();
    }

    /**
     * 获取设备的IMEI
     * <p>
     * 解释：
     * IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
     * IMSI共有15位，其结构如下：
     * MCC+MNC+MIN
     * MCC：Mobile Country Code，移动国家码，共3位，中国为460;
     * MNC:Mobile NetworkCode，移动网络码，共2位
     * 在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
     * 合起来就是（也是Android手机中APN配置文件中的代码）：
     * 中国移动：46000 46002
     * 中国联通：46001
     * 中国电信：46003
     * 举例，一个典型的IMSI号码为460030912121001
     * IMEI是International Mobile Equipment Identity （国际移动设备标识）的简称
     * IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的
     * 其组成为：1. 前6位数(TAC)是”型号核准号码”，一般代表机型
     * 2. 接着的2位数(FAC)是”最后装配号”，一般代表产地
     * 3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号
     * 4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用
     */

    public static String getLocalIMEI(Context context) {
        TelephonyManager tm = null;
        try {
            tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            if (null != tm) {
                return tm.getDeviceId();
            }
        } catch (Exception ex) {
        } finally {
            tm = null;
        }
        return null;
    }

    /**
     * 序列号
     * 从Android 2.3 (“Gingerbread”)开始可用，可以通过android.os.Build.SERIAL获取，
     * 对于没有通话功能的设备，它会返回一个唯一的device ID
     *
     * @return
     */
    public static String getSerial(Context context) {
        try {
            String str = android.os.Build.class.getField("SERIAL").get(null).toString();
            return str;
        } catch (IllegalAccessException | IllegalArgumentException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Android Id
     * ANDROID_ID
     * 2.2（Froyo，8）版本系统会不可信，来自主要生产厂商的主流手机，至少有一个普遍发现的bug，这些有问题的手机相同的ANDROID_ID: 9774d56d682e549c
     * 但是如果返厂的手机，或者被root的手机，可能会变
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /**
     * 运营商名称
     * 按照字母次序的current registered operator(当前已注册的用户)的名字 注意：仅当用户已在网络注册时有效。
     * 在CDMA网络中结果也许不可靠。
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getNetworkOperatorName();
    }

    /**
     * 运营商编号
     * MCC+MNC(mobile country code + mobile network code) 注意：仅当用户已在网络注册时有效。
     * 在CDMA网络中结果也许不可靠。
     */
    public static String getNetworkOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return tm.getNetworkOperator();
    }

    /*
     * SIM卡的序列号： 需要权限：READ_PHONE_STATE
     */
    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSimSerialNumber();
    }


    /**
     * sim卡所在国家
     * 获取ISO标准的国家码，即国际长途区号。 注意：仅当用户已在网络注册后有效。 在CDMA网络中结果也许不可靠。
     */
    public static String getNetworkCountryIso(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getNetworkCountryIso();
    }

    /**
     * android.os.Build.BOARD：获取设备基板名称
     * android.os.Build.BOOTLOADER:获取设备引导程序版本号
     * android.os.Build.BRAND：获取设备品牌
     * android.os.Build.CPU_ABI：获取设备指令集名称（CPU的类型）
     * android.os.Build.CPU_ABI2：获取第二个指令集名称
     * android.os.Build.DEVICE：获取设备驱动名称
     * android.os.Build.DISPLAY：获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
     * android.os.Build.FINGERPRINT：设备的唯一标识。由设备的多个信息拼接合成。
     * android.os.Build.HARDWARE：设备硬件名称,一般和基板名称一样（BOARD）
     * android.os.Build.HOST：设备主机地址
     * android.os.Build.ID:设备版本号。
     * android.os.Build.MODEL ：获取手机的型号 设备名称。
     * android.os.Build.MANUFACTURER:获取设备制造商
     * android:os.Build.PRODUCT：整个产品的名称
     * android:os.Build.RADIO：无线电固件版本号，通常是不可用的 显示unknown
     * android.os.Build.TAGS：设备标签。如release-keys 或测试的 test-keys
     * android.os.Build.TIME：出厂时间
     * android.os.Build.TYPE:设备版本类型  主要为"user" 或"eng".
     * android.os.Build.USER:设备用户名 基本上都为android-build
     * android.os.Build.VERSION.RELEASE：获取系统版本字符串。如4.1.2 或2.2 或2.3等
     * android.os.Build.VERSION.CODENAME：设备当前的系统开发代号，一般使用REL代替
     * android.os.Build.VERSION.INCREMENTAL：系统源代码控制值，一个数字或者git hash值
     * android.os.Build.VERSION.SDK：系统的API级别 一般使用下面大的SDK_INT 来查看
     * android.os.Build.VERSION.SDK_INT：系统的API级别 数字表示
     * android.os.Build.VERSION_CODES类 中有所有的已公布的Android版本号。全部是Int常亮。可用于与SDK_INT进行比较来判断当前的系统版本
     */
    //android 获取当前手机型号
    public static String getPhoneModel(Context context) {
        Build bd = new Build();
        return bd.MODEL;
    }

    //android 获取当前手机品牌
    public static String getPhoneProduct(Context context) {
        Build bd = new Build();
//        return bd.PRODUCT;
        return bd.BRAND;
    }
    //android 获取当前获取设备引导程序版本号
    public static String getPhoneBOOTLOADER(Context context) {
        Build bd = new Build();
        return bd.BOOTLOADER;
    }
    //android 获取设备指令集名称
    public static String getPhoneCPU_ABI(Context context) {
        Build bd = new Build();
        return bd.CPU_ABI;
    }
    //android DEVICE：获取设备驱动名称
    public static String getPhoneDEVICE(Context context) {
        Build bd = new Build();
        return bd.DEVICE;
    }
    //android DISPLAY：获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
    public static String getPhoneDISPLAY(Context context) {
        Build bd = new Build();
        return bd.DISPLAY;
    }
    //android FINGERPRINT：设备的唯一标识。由设备的多个信息拼接合成。
    public static String getPhoneFINGERPRINT(Context context) {
        Build bd = new Build();
        return bd.FINGERPRINT;
    } //android HARDWARE：设备硬件名称
    public static String getPhoneHARDWARE(Context context) {
        Build bd = new Build();
        return bd.HARDWARE;
    } //android HOST：设备主机地址
    public static String getPhoneHOST(Context context) {
        Build bd = new Build();
        return bd.HOST;
    }
    //android ID:设备版本号
    public static String getPhoneID(Context context) {
        Build bd = new Build();
        return bd.ID;
    }
    //android MANUFACTURER:获取设备制造商
    public static String getPhoneMANUFACTURER(Context context) {
        Build bd = new Build();
        return bd.MANUFACTURER;
    }
    //android RADIO：无线电固件版本号，通常是不可用的
    public static String getPhoneRADIO(Context context) {
        Build bd = new Build();
        return bd.RADIO;
    }
   //TAGS：设备标签。如release-keys 或测试的 test-keys
   public static String getPhoneTAGS(Context context) {
       Build bd = new Build();
       return bd.TAGS;
   }
    //TIME：出厂时间
    public static long getPhoneTIME(Context context) {
        Build bd = new Build();
        return bd.TIME;
    }
    // TYPE:设备版本类型  主要为"user" 或"eng".
    public static String getPhoneTYPE(Context context) {
        Build bd = new Build();
        return bd.TYPE;
    }
    //USER:设备用户名 基本上都为android-build
    public static String getPhoneUSER(Context context) {
        Build bd = new Build();
        return bd.USER;
    }
    //VERSION.RELEASE：获取系统版本字符串。如4.1.2 或2.2 或2.3等
    public static String getPhoneRELEASE(Context context) {
//        Build bd = new Build();
        return Build.VERSION.RELEASE;
    }
     //VERSION.CODENAME：设备当前的系统开发代号，一般使用REL代替
     public static String getPhoneCODENAME(Context context) {
//         Build bd = new Build();
         return Build.VERSION.CODENAME;
     }
     //VERSION.INCREMENTAL：系统源代码控制值，一个数字或者git hash值
     public static String getPhoneINCREMENTAL(Context context) {
//         Build bd = new Build();
         return Build.VERSION.INCREMENTAL;
     }
     //VERSION.SDK：系统的API级别 一般使用下面大的SDK_INT 来查看
     public static String getPhoneSDK(Context context) {
//         Build bd = new Build();
         return Build.VERSION.SDK;
     }
     //VERSION.SDK_INT：系统的API级别 数字表示
     public static int  getPhoneSDK_INT(Context context) {
//         Build bd = new Build();
         return Build.VERSION.SDK_INT;
     }
     /*
     //VERSION_CODES类 中有所有的已公布的Android版本号。全部是Int常亮。可用于与SDK_INT进行比较来判断当前的系统版本
     public static int getPhoneVERSION_CODES(Context context) {
//         Build bd = new Build();
         return Build.VERSION_CODES;
     }
*/
    /**
     * android 获取屏幕分辩率
     *
     * @param context
     * @return * 下面的代码即可获取屏幕的尺寸。 在一个Activity的onCreate方法中，写入如下代码：
     * DisplayMetrics metric   = new DisplayMetrics();
     * getWindowManager().getDefaultDisplay().getMetrics(metric);
     * int width  = metric.widthPixels; // 屏幕宽度（像素）
     * int height = metric.heightPixels;   // 屏幕高度（像素）
     * float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
     * int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
     * <p>
     * 但是，需要注意的是，在一个低密度的小屏手机上，仅靠上面的代码是不能获取正确的尺寸的。
     * 比如说，一部240x320像素的低密度手机，如果运行上述代码，获取到的屏幕尺寸是320x427。
     * 因此，研究之后发现，若没有设定多分辨率支持的话
     * ，Android系统会将240x320的低密度（120）尺寸转换为中等密度（160）对应的尺寸，
     * 这样的话就大大影响了程序的编码。所以，需要在工程的AndroidManifest
     * .xml文件中，加入supports-screens节点，具体的内容如下：
     * <supports-screens
     * android:smallScreens="true" android:normalScreens="true"
     * android:largeScreens="true" android:resizeable="true"
     * android:anyDensity="true" />
     * 这样的话，当前的Android程序就支持了多种分辨率，那么就可以得到正确的物理尺寸了。
     */
    public static String getWeithAndHeight(Context context) {
        // 这种方式在service中无法使用，
        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels; // 宽
        int height = dm.heightPixels; // 高
        float density = dm.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        // 在service中也能得到高和宽
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = mWindowManager.getDefaultDisplay().getWidth();
        height = mWindowManager.getDefaultDisplay().getHeight();

        // 居中显示Toast
//        Toast msg = Toast.makeText(this, "宽=" + width + "   高=" + height,
//                Toast.LENGTH_LONG);
//        msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
//                msg.getYOffset() / 2);
//        msg.show();
        return "(像素)宽:" + width + "*" + "(像素)高:" + height + "\n"
                + "屏幕密度（0.75 / 1.0 / 1.5）:" + density + "\n"
                + "屏幕密度DPI（120 / 160 / 240）:" + densityDpi + "\n";

    }

    //android获取当前时区
    public static String getTimeZone(Context context) {
        TimeZone tz = TimeZone.getDefault();
        String s = tz.getID();
        return s;
    }

    //android获取当前日期时间
    public static String getDateAndTime(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //获取手机系统语言 0中文简体 1其它
    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return "0";
        else
            return "1";
    }

    /**
     * Mac地址
     * ACCESS_WIFI_STATE权限
     * 有些设备没有WiFi，或者蓝牙，就不可以，如果WiFi没有打开，硬件也不会返回Mac地址
     *
     * @return
     */
    public static String getMac(Context context) {
//        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
//        return info.getMacAddress();
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();

        return result;
    }

    /**
     * 获取网络类型
     */
//    public static int getNetWorkType(Context context) {
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()) {
//            String type = networkInfo.getTypeName();
//
//            if (type.equalsIgnoreCase("WIFI")) {
//                return AVConstants.NETTYPE_WIFI;
//            } else if (type.equalsIgnoreCase("MOBILE")) {
//                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                if (mobileInfo != null) {
//                    switch (mobileInfo.getType()) {
//                        case ConnectivityManager.TYPE_MOBILE:// 手机网络
//                            switch (mobileInfo.getSubtype()) {
//                                case TelephonyManager.NETWORK_TYPE_UMTS:
//                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                                case TelephonyManager.NETWORK_TYPE_HSPA:
//                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
//                                case TelephonyManager.NETWORK_TYPE_EHRPD:
//                                case TelephonyManager.NETWORK_TYPE_HSPAP:
//                                    return AVConstants.NETTYPE_3G;
//                                case TelephonyManager.NETWORK_TYPE_CDMA:
//                                case TelephonyManager.NETWORK_TYPE_GPRS:
//                                case TelephonyManager.NETWORK_TYPE_EDGE:
//                                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                                case TelephonyManager.NETWORK_TYPE_IDEN:
//                                    return AVConstants.NETTYPE_2G;
//                                case TelephonyManager.NETWORK_TYPE_LTE:
//                                    return AVConstants.NETTYPE_4G;
//                                default:
//                                    return AVConstants.NETTYPE_NONE;
//                            }
//                    }
//                }
//            }
//        }
//
//        return AVConstants.NETTYPE_NONE;
//    }

    /*
     * 网络连接是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查看本机外网IP
     * 该方法需要设备支持上网 查看
     * System.out.println((GetNetIp("http://fw.qq.com/ipaddress"))); 加权限
     * <uses-permission
     * android:name="android.permission.INTERNET"></uses-permission>
     * 通过获取http://fw.qq.com/ipaddress网页取得外网IP 这里有几个查看IP的网址然后提取IP试试。
     * http://ip168.com/ http://www.cmyip.com/ http://city.ip138.com/ip2city.asp
     */
    public String GetNetIp(String ipaddr) {
        URL infoUrl = null;
        InputStream inStream = null;
        try {
            infoUrl = new URL(ipaddr);
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                return strber.toString();
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    // 获取手机经纬度
    public void getLocation(Context context) {
        // 1. 创建一个 LocationManager对象。
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 2. 创建一个 LocationListener对象。
        LocationListener myGPSListener = new LocationListener() {
            // 一旦Location发生改变就会调用这个方法
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        // 3.向LocationManager 注册一个LocationListener。
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true); // 根据Criteria
        locationManager.requestLocationUpdates(provider, 5000, 0, myGPSListener);
        // 4.移除LocationManager 注册的 LocationListener。
        locationManager.removeUpdates(myGPSListener);

    }


    /**
     * 获取信号强度
     * 可能需要的权限
     * <uses-permission android:name="android.permission.WAKE_LOCK"></uses-permission>
     * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     */
    public void getPhoneState(Context context) {

        // 1. 创建telephonyManager 对象。
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        // 2. 创建PhoneStateListener 对象
        PhoneStateListener MyPhoneListener = new PhoneStateListener() {
            @Override
            public void onCellLocationChanged(CellLocation location) {
                int CID, ID;
                if (location instanceof GsmCellLocation) {// gsm网络
                    CID = ((GsmCellLocation) location).getCid();

                } else if (location instanceof CdmaCellLocation) {// 其他CDMA等网络
                    ID = ((CdmaCellLocation) location).getBaseStationId();
                }

            }

            @Override
            public void onServiceStateChanged(ServiceState serviceState) {
                super.onServiceStateChanged(serviceState);
            }

            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                int asu = signalStrength.getGsmSignalStrength();
                int dbm = -113 + 2 * asu; // 信号强度
                super.onSignalStrengthsChanged(signalStrength);
            }

        };
        // 3. 监听信号改变
        telephonyManager.listen(MyPhoneListener,
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);


    }


    /**
     * 获取手机可用内存和总内存
     * 在android开发中，有时候我们想获取手机的一些硬件信息，比如android手机的总内存和可用内存大小。这个该如何实现呢？
     * 通过读取文件"/proc/meminfo"  的信息能够获取手机Memory的总量，而通过
     * ActivityManager.getMemoryInfo(ActivityManager.MemoryInfo)方法可以获取当前的可用Memory量。
     * "/proc/meminfo"文件记录了android手机的一些内存信息，在命令行窗口里输入"adb shell"，进入shell环境，输入
     * "cat /proc/meminfo"即可在命令行里显示meminfo文件的内容，具体如下所示。
     * C:\Users\Figo>adb shell # cat /proc/meminfo cat /proc/meminfo
     * MemTotal: 94096 kB
     * MemFree: 1684 kB     Buffers: 16 kB     Cached: 27160 kB
     * SwapCached: 0 kB     Active: 35392 kB    Inactive: 44180 kB
     * Active(anon): 26540 kB Inactive(anon): 28244 kB   Active(file): 8852 kB
     * Inactive(file): 15936 kB  Unevictable: 280 kB  Mlocked: 0 kB       * SwapTotal:   0 kB SwapFree: 0 kB Dirty: 0 kB Writeback: 0 kB AnonPages: 52688 kB
     * Mapped: 17960 kB Slab: 3816 kB SReclaimable: 936 kB SUnreclaim: 2880
     * kB PageTables: 5260 kB NFS_Unstable: 0 kB Bounce: 0 kB
     * WritebackTmp:   0 kB
     * CommitLimit: 47048 kB
     * Committed_AS: 1483784 kB
     * VmallocTotal:  876544 kB
     * VmallocUsed: 15456 kB
     * VmallocChunk: 829444 kB #
     * <p>
     * 下面先对"/proc/meminfo"文件里列出的字段进行粗略解释：
     * MemTotal: 所有可用RAM大小。
     * MemFree:LowFree与HighFree的总和，被系统留着未使用的内存。
     * Buffers: 用来给文件做缓冲大小。
     * Cached:被高速缓冲存储器（cache memory）用的内存的大小（等于diskcache minus SwapCache）。
     * SwapCached:被高速缓冲存储器（cachememory）用的交换空间的大小。已经被交换出来的内存，仍然被存放在swapfile中，
     * 用来在需要的时候很快的被替换而不需要再次打开I/O端口。
     * Active:在活跃使用中的缓冲或高速缓冲存储器页面文件的大小，除非非常必要，否则不会被移作他用。 I
     * nactive:在不经常使用中的缓冲或高速缓冲存储器页面文件的大小，可能被用于其他途径。
     * SwapTotal: 交换空间的总大小。
     * SwapFree:未被使用交换空间的大小。
     * Dirty: 等待被写回到磁盘的内存大小。
     * Writeback: 正在被写回到磁盘的内存大小。
     * AnonPages：未映射页的内存大小。
     * Mapped: 设备和文件等映射的大小。 Slab:
     * 内核数据结构缓存的大小，可以减少申请和释放内存带来的消耗。
     * SReclaimable:可收回Slab的大小。
     * SUnreclaim：不可收回Slab的大小（SUnreclaim+SReclaimable＝Slab）。
     * PageTables：管理内存分页页面的索引表的大小。
     * NFS_Unstable:不稳定页表的大小。要获取android手机总内存大小，只需读取"/proc/meminfo"文件的第1行，并进行简单的字符串处理即可。
     */
    public static String getSystemMemory(Context context) {

        String availMemory = getAvailMemory(context);
        String totalMemory = getTotalMemory(context);
        return "可用内存=" + availMemory + "\n" + "总内存=" + totalMemory;

    }

    /*
    手机的内存信息主要在/proc/meminfo文件中，其中第一行是总内存，而剩余内存可通过ActivityManager.MemoryInfo得到。
     */
    public static String getAvailMemory(Context context) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
//        int memory = am.getMemoryClass();//最大分配内存
//        Log.d("TAG", "最大分配内存"+Formatter.formatFileSize(context, memory));
//        float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0/ (1024 * 1024));//最大分配内存获取方法2
//        Log.d("TAG", "最大分配内存2"+Formatter.formatFileSize(context, (long) maxMemory));
//        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0/ (1024 * 1024)); //当前分配的总内存
//        Log.d("TAG", "当前分配的总内存"+Formatter.formatFileSize(context, (long) totalMemory));
//        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0/ (1024 * 1024)); //剩余内存
//        Log.d("TAG", "剩余内存"+Formatter.formatFileSize(context, (long) freeMemory));
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
//        return getMemInfoIype(context, "MemFree");
    }

    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
//        return getMemInfoIype(context, "MemTotal");
    }

    public static String getMemInfoIype(Context context, String type) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        try {
            FileReader fileReader = new FileReader(str1);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 4 * 1024);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.contains(type)) {
                    break;
                }
            }
            bufferedReader.close();
            /* \\s表示   空格,回车,换行等空白符,
            +号表示一个或多个的意思     */
            String[] array = str.split("\\s+");
            // 获得系统总内存，单位是KB，乘以1024转换为Byte
            int length = Integer.valueOf(array[1]).intValue() * 1024;
            return android.text.format.Formatter.formatFileSize(context, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getProcessCpuRate() {//cpu使用率

        StringBuilder tv = new StringBuilder();
        int rate = 0;

        try {
            String Result;
            Process p;
            p = Runtime.getRuntime().exec("top -n 1");

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((Result = br.readLine()) != null) {
                if (Result.trim().length() < 1) {
                    continue;
                } else {
                    String[] CPUusr = Result.split("%");
                    tv.append("USER:" + CPUusr[0] + "\n");
                    String[] CPUusage = CPUusr[0].split("User");
                    String[] SYSusage = CPUusr[1].split("System");
                    tv.append("CPU:" + CPUusage[1].trim() + " length:" + CPUusage[1].trim().length() + "\n");
                    tv.append("SYS:" + SYSusage[1].trim() + " length:" + SYSusage[1].trim().length() + "\n");

                    rate = Integer.parseInt(CPUusage[1].trim()) + Integer.parseInt(SYSusage[1].trim());
                    break;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println(rate + "");
        return rate;
    }
    // 获取手机CPU信息
    public static String getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";
        String[] cpuInfo = {"", ""}; // 1-cpu型号 //2-cpu频率
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
        }

        return "1-cpu型号:" + cpuInfo[0] + "2-cpu频率:" + cpuInfo[1];
    }// 和内存信息同理，cpu信息可通过读取/proc/cpuinfo文件来得到，其中第一行为cpu型号，第二行为cpu频率。

    //    CPU最大频率
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim() + "Hz";
    }
// 获取CPU最小频率（单位KHZ）

    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim() + "Hz";
    }

    // 实时获取CPU当前频率（单位KHZ）

    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim() + "Hz";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    // 实时获取CPU名称
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动，无任何状态时 2.tm.CALL_STATE_RINGING=1
     * 响铃，电话进来时 3.tm.CALL_STATE_OFFHOOK=2 摘机
     */
    public static String getTellState(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getCallState();
    }


    /*
     * 电话方位：
     */
    public static String getTellLocal(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        // 返回当前移动终端的位置
        CellLocation location = tm.getCellLocation();
        // 请求位置更新，如果更新将产生广播，接收对象为注册LISTEN_CELL_LOCATION的对象，需要的permission名称为
        // ACCESS_COARSE_LOCATION。
        location.requestLocationUpdate();
        return "" + location;
    }


    /**
     * 获取数据活动状态
     * <p>
     * DATA_ACTIVITY_IN 数据连接状态：活动，正在接受数据 DATA_ACTIVITY_OUT 数据连接状态：活动，正在发送数据
     * DATA_ACTIVITY_INOUT 数据连接状态：活动，正在接受和发送数据 DATA_ACTIVITY_NONE
     * 数据连接状态：活动，但无数据发送和接受
     */
    public static String getDataActivityState(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getDataActivity();
    }


    /**
     * 获取数据连接状态
     * <p>
     * DATA_CONNECTED 数据连接状态：已连接 DATA_CONNECTING 数据连接状态：正在连接
     * DATA_DISCONNECTED 数据连接状态：断开 DATA_SUSPENDED 数据连接状态：暂停
     */
    public static String getDataState(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getDataState();
    }

    /*
     * 返回移动终端的软件版本，例如：GSM手机的IMEI/SV码。 设备的软件版本号： 例如：the IMEI/SV(software
     * version) for GSM phones. Return null if the software version is not
     * available.
     */
    public static String getDeviceSoftwareVersion(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getDeviceSoftwareVersion();
    }








    /*
     * 当前使用的网络类型： 例如：
     * NETWORK_TYPE_UNKNOWN 网络类型未知 0
     * NETWORK_TYPE_GPRS GPRS网络1
     * NETWORK_TYPE_EDGE EDGE网络 2
     * NETWORK_TYPE_UMTS UMTS网络 3
     * NETWORK_TYPE_HSDPA HSDPA网络 8
     * NETWORK_TYPE_HSUPA HSUPA网络 9
     * NETWORK_TYPE_HSPA HSPA网络 10
     * NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4
     * NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5
     * NETWORK_TYPE_EVDO_A EVDO网络,
     * revision A. 6
     * NETWORK_TYPE_1xRTT 1xRTT网络 7
     */

    public static String getNetworkType(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getNetworkType();// int
    }

    /*
     * 手机类型： 例如： PHONE_TYPE_NONE 无信号 PHONE_TYPE_GSM GSM信号 PHONE_TYPE_CDMA
     * CDMA信号
     */

    public static String getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getPhoneType();// int
    }

    /*
     * Returns the ISO country code equivalent for the SIM provider's
     * country code. 获取ISO国家码，相当于提供SIM卡的国家码。
     */

    public static String getSimCountryIso(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSimCountryIso();
    }
    /*
     * Returns the MCC+MNC (mobile country code + mobile network code) of
     * the provider of the SIM. 5 or 6 decimal digits.
     * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字. SIM卡的状态必须是
     * SIM_STATE_READY(使用getSimState()判断).
     */


    public static String getSimOperator(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSimOperator();
    }

    /*
     * 服务商名称： 例如：中国移动、联通 SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
     */


    public static String getSimOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSimOperatorName();
    }



    /*
     * SIM的状态信息： SIM_STATE_UNKNOWN 未知状态 0 SIM_STATE_ABSENT 没插卡 1
     * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2 SIM_STATE_PUK_REQUIRED
     * 锁定状态，需要用户的PUK码解锁 3 SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
     * SIM_STATE_READY 就绪状态 5
     */

    public static String getSimState(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getSimState();// int
    }


    /*
     * 取得和语音邮件相关的标签，即为识别符 需要权限：READ_PHONE_STATE
     */
    public static String getVoiceMailAlphaTag(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getVoiceMailAlphaTag();
    }


    /*
     * 获取语音邮件号码： 需要权限：READ_PHONE_STATE
     */

    public static String getVoiceMailNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.getVoiceMailNumber();
    }

    /*
     * ICC卡是否存在
     */

    public static String hasIccCard(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.hasIccCard();//boolean
    }


    /*
     * 是否漫游: (在GSM用途下)
     */

    public static String getNetworkRoaming(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        return "" + tm.isNetworkRoaming();//boolean
    }


    /**
     * 返回当前移动终端附近移动终端的信息
     * 附近的电话的信息: 类型：List<NeighboringCellInfo>
     * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
     */
    public static String getNeighboringCellInfos(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (tm == null) {
            return "";
        }
        List<NeighboringCellInfo> infos = tm.getNeighboringCellInfo();
        for (NeighboringCellInfo info : infos) {
            // 获取邻居小区号
            int cid = info.getCid();
            // 获取邻居小区LAC，LAC:
            // 位置区域码。为了确定移动台的位置，每个GSM/PLMN的覆盖区都被划分成许多位置区，LAC则用于标识不同的位置区。
            info.getLac();
            info.getNetworkType();
            info.getPsc();
            // 获取邻居小区信号强度
            info.getRssi();
            return "" + infos;
        }


        return null;
    }

}