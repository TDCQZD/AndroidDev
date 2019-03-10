package com.example.zhangdai.recyclerviewtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zhangdai.recyclerviewtest.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btBasisUsage;
    private Button btUniversalAdapter;
    private Button btReclerviewRefesh;
    private Button btExtend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        btBasisUsage = (Button) findViewById(R.id.bt_basis_usage);
        btUniversalAdapter = (Button) findViewById(R.id.bt_universal_adapter);
        btReclerviewRefesh = (Button) findViewById(R.id.bt_reclerview_refesh);
        btExtend = (Button) findViewById(R.id.bt_extend);

        btBasisUsage.setOnClickListener(this);
        btUniversalAdapter.setOnClickListener(this);
        btReclerviewRefesh.setOnClickListener(this);
        btExtend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btBasisUsage) {
            Toast.makeText(MainActivity.this, "通用用法", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
            startActivity(intent);
        } else if (v == btUniversalAdapter) {
            // Handle clicks for btUniversalAdapter
        } else if (v == btReclerviewRefesh) {
            // Handle clicks for btReclerviewRefesh
        } else if (v == btExtend) {
            // Handle clicks for btExtend
        }
    }

    public void BaseRecyclerViewAdapterHelper(View view) {
        startActivity(new Intent(MainActivity.this, BaseRecyclerViewAdapterHelperActivity.class));
    }

}
