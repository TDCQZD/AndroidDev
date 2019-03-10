package com.example.filetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btDataSave;
    private Button btDataRead;
    private TextView tvShow;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        btDataSave = (Button) findViewById(R.id.bt_Data_save);
        btDataRead = (Button) findViewById(R.id.bt_Data_read);
        tvShow = (TextView) findViewById(R.id.tv_show);

        btDataSave.setOnClickListener(this);
        btDataRead.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btDataSave) {
//            saveData("123456");
            saveData();
        } else if (v == btDataRead) {
//            readData("123456");
            readData();
        }
    }

    private void readData() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        tvShow.setText(content.toString());
    }

    private void saveData() {
        String s = "dssaerhhbsdfrtfdnrtrrrrrr2312";
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(MainActivity.this, "数据写入成功", Toast.LENGTH_SHORT).show();
    }

    /*
    读取指定文件夹数据
     */
    private void readData(String user) {
        FileInputStream inputStream = null;
        String signcert = "";

        String filesDir = context.getFilesDir().getPath();//获取Files目录地址
        filesDir = filesDir.substring(0, filesDir.length() - 6);
        String certificatePath = filesDir + "/vkey001/" + user;//创建存放证书的路径
        File file = new File(certificatePath + "/" + "zdya_vkey.dat");
//        Log.i("readSignCert", "file" + file);

        try {
            inputStream = new FileInputStream(file);//读取指定文件夹下数据

            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            signcert = new String(buffer);
            tvShow.setText(signcert);
            inputStream.close();
            Log.i("readSignCert", "signcert: -->" + signcert);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*
    读取指定文件夹数据
     */
    private void saveData(String account) {

        String s = "dsfdnrtrrrrrr2312";
        String filesDir = context.getFilesDir().getPath();//获取Files目录地址
        //保存数据到指定文件夹下
        filesDir = filesDir.substring(0, filesDir.length() - 6);
        String certificatePath = filesDir + "/vkey001/" + account;//创建存放证书的路径
        File dir = new File(certificatePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File cerfile = new File(certificatePath, "zdya_vkey.dat");


        try {
            FileOutputStream outPutStream = new FileOutputStream("cerfile", false);//存数据到指定文件夹

            outPutStream.write(s.getBytes());
            outPutStream.close();
            Log.i("TAG", "MainActivity saveData()--->" + "数据保存成功");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(MainActivity.this, "数据写入成功", Toast.LENGTH_SHORT).show();
    }
}
