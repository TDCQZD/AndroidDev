package com.example.administrator.picassotest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.administrator.picassotest.R;
import com.example.administrator.picassotest.adapter.PicassoTransformationsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
*
*@author ZD
*created at 2017/6/27 10:28
*description：使用transformations库对图片进行变换
*/
public class PicassoTransfromationsActivity extends AppCompatActivity {
    @BindView(R.id.lv_picasso_transfromations)
    ListView lvPicassoTransfromations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_transfromations);
        ButterKnife.bind(this);
        List<String> data = new ArrayList<>();

        for (int i = 1; i <= 36; i++) {
            data.add(i + "");
        }

        // 初始化listview
        PicassoTransformationsAdapter picassoTransformationsAdapter = new PicassoTransformationsAdapter(PicassoTransfromationsActivity.this, data);

        lvPicassoTransfromations.setAdapter(picassoTransformationsAdapter);

    }
}
