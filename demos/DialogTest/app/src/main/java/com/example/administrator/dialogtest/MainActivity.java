package com.example.administrator.dialogtest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 对话框集
 * 创建对话框：一般采用AlertDialog和Dialog。
 * 注：官方不推荐直接使用Dialog创建对话框。
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btDialogCommon;
    private Button btDialogMoreCommon;
    private Button btDialogWait;
    private Button btDialogList;
    private Button btDialogSigle;
    private Button btDialogCheckbox;
    private Button btDialogProgress;
    private Button btDialogInput;
    private Button btDialogCustom;
    private Button btDialogFagment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        btDialogCommon = (Button) findViewById(R.id.bt_dialog_common);
        btDialogMoreCommon = (Button) findViewById(R.id.bt_dialog_more_common);
        btDialogList = (Button) findViewById(R.id.bt_dialog_list);
        btDialogSigle = (Button) findViewById(R.id.bt_dialog_sigle);
        btDialogCheckbox = (Button) findViewById(R.id.bt_dialog_checkbox);
        btDialogProgress = (Button) findViewById(R.id.bt_dialog_progress);
        btDialogWait = (Button) findViewById(R.id.bt_dialog_wait);
        btDialogInput = (Button) findViewById(R.id.bt_dialog_input);
        btDialogCustom = (Button) findViewById(R.id.bt_dialog_custom);
        btDialogFagment=(Button) findViewById(R.id.bt_dialog_fagment);

        btDialogCommon.setOnClickListener(this);
        btDialogMoreCommon.setOnClickListener(this);
        btDialogList.setOnClickListener(this);
        btDialogSigle.setOnClickListener(this);
        btDialogCheckbox.setOnClickListener(this);
        btDialogProgress.setOnClickListener(this);
        btDialogWait.setOnClickListener(this);
        btDialogInput.setOnClickListener(this);
        btDialogCustom.setOnClickListener(this);
        btDialogFagment.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btDialogCommon) {
            // 两个按钮的普通对话框
            showCommonDialog();
        } else if (v == btDialogMoreCommon) {
            // 三个按钮的普通对话框
            showCommonMoreDialog();
        } else if (v == btDialogList) {
            // 列表对话框
            showListDialog();
        } else if (v == btDialogSigle) {
            // 单选对话框
            showSigleDialog();
        } else if (v == btDialogCheckbox) {
            // 多选对话框
            showCheckboxDialog();
        } else if (v == btDialogProgress) {
            // 进度对话框
            showProgressDialog();
        } else if (v == btDialogWait) {
            // 等待对话框
            showWaitDialog();
        } else if (v == btDialogInput) {
            // 可输入对话框
            showInputDialog();
        } else if (v == btDialogCustom) {
            // 自定义对话框
            startActivity(new Intent(MainActivity.this, CustomDialogActivity.class));
        } else if (v == btDialogFagment) {
            // DialogFragment 创建对话框
            startActivity(new Intent(MainActivity.this, DialogFragmentActivity.class));
        }
    }


    private void showInputDialog() {
        final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("可输入Dialog").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void showWaitDialog() {
        ProgressDialog waitingDialog =
                new ProgressDialog(MainActivity.this);
        waitingDialog.setTitle("等待Dialog");
        waitingDialog.setMessage("加载中，请稍等...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(true);
        waitingDialog.show();
    }

    private void showProgressDialog() {
        final int MAX_PROGRESS = 100;
        final ProgressDialog progressDialog =
                new ProgressDialog(MainActivity.this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("进度条Dialog");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(MAX_PROGRESS);
        progressDialog.show();
    /* 模拟进度增加的过程
     * 新开一个线程，每个100ms，进度增加1
     */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;
                while (progress < MAX_PROGRESS) {
                    try {
                        Thread.sleep(100);
                        progress++;
                        progressDialog.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 进度达到最大值后，窗口消失
                progressDialog.cancel();
            }
        }).start();
    }

    ArrayList<Integer> yourChoices = new ArrayList<>();

    private void showCheckboxDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4"};
        // 设置默认选中的选项，全为false默认均未选中
        final boolean initChoiceSets[] = {false, false, false, false};
        yourChoices.clear();
        AlertDialog.Builder multiChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);
        multiChoiceDialog.setTitle("多选Dialog");
        multiChoiceDialog.setMultiChoiceItems(items, initChoiceSets,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            yourChoices.add(which);
                        } else {
                            yourChoices.remove(which);
                        }
                    }
                });
        multiChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int size = yourChoices.size();
                        String str = "";
                        for (int i = 0; i < size; i++) {
                            str += items[yourChoices.get(i)] + " ";
                        }
                        Toast.makeText(MainActivity.this,
                                "你选中了" + str,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        multiChoiceDialog.show();
    }

    int yourChoice;

    private void showSigleDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4"};
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(MainActivity.this);
        singleChoiceDialog.setTitle("单选Dialog");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            Toast.makeText(MainActivity.this,
                                    "你选择了" + items[yourChoice],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        singleChoiceDialog.show();
    }

    private void showListDialog() {
        final String[] items = {"我是1", "我是2", "我是3", "我是4"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(MainActivity.this);
        listDialog.setTitle("列表Dialog");

        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this,
                        "你点击了" + items[which],
                        Toast.LENGTH_SHORT).show();
            }
        });
        listDialog.show();

    }

    private void showCommonMoreDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle("普通三个按钮Dialog").setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNeutralButton("返回",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ...To-do
                    }
                });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ...To-do
            }
        });
        // 创建实例并显示
        normalDialog.show();
    }


    private void showCommonDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.ic_launcher);
        normalDialog.setTitle("普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }

}
