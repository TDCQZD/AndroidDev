package com.example.zhangdai.regularexpressiontest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Administrator on 2017/3/7.
 * 作用：实名认证,身份证认证
 */

public class RealNameApproveActivity extends Activity {
    private EditText et_name_realname;
    private EditText et_idcard_realname;
    private Button bt_realname;
    String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
            "3", "2"};
    String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2"};
    String Ai = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname);
        et_idcard_realname = (EditText) findViewById(R.id.et_idcard_realname);
        et_name_realname = (EditText) findViewById(R.id.et_name_realname);
        //获取输入内容


        bt_realname = (Button) findViewById(R.id.bt_realname);
        bt_realname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_name_realname.getText().toString();
                String id_Card = et_idcard_realname.getText().toString();
                if (name.length() == 0) {
                    Toast.makeText(getApplicationContext(), "姓名不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    //身份证号验证
                    IDCardValidate(id_Card);
                }
                startActivity(new Intent(RealNameApproveActivity.this, MainActivity.class
                ));
            }
        });
    }

    private void IDCardValidate(String id_Card) {
        //号码的长度 15位或18位
        if (id_Card.length() != 15 && id_Card.length() != 18) {
            Toast.makeText(getApplicationContext(), "身份证号码长度应该为15位或18位。", Toast.LENGTH_SHORT).show();
        }
        //数字 除最后以为都为数字
        if (id_Card.length() == 18) {
            Ai = id_Card.substring(0, 17);
        } else if (id_Card.length() == 15) {
            Ai = id_Card.substring(0, 6) + "19" + id_Card.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {

            Toast.makeText(getApplicationContext(), "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。", Toast.LENGTH_SHORT).show();
        }

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {

            Toast.makeText(getApplicationContext(), "身份证生日无效。", Toast.LENGTH_SHORT).show();
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {

                Toast.makeText(getApplicationContext(), "身份证生日不在有效范围。", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {

            Toast.makeText(getApplicationContext(), "身份证月份无效。", Toast.LENGTH_SHORT).show();
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {

            Toast.makeText(getApplicationContext(), "身份证日期无效。", Toast.LENGTH_SHORT).show();
        }

        // ================ 地区码时候有效 ================
        Hashtable<String, String> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            Toast.makeText(getApplicationContext(), "身份证地区编码错误。", Toast.LENGTH_SHORT).show();
        }

        // ================ 判断最后一位的值 ==============
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (id_Card.length() == 18) {
            if (Ai.equals(id_Card) == false) {

                Toast.makeText(getApplicationContext(), "身份证无效，不是合法的身份证号码", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
