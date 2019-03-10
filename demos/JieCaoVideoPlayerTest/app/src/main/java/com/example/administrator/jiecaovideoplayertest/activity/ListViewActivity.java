package com.example.administrator.jiecaovideoplayertest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.administrator.jiecaovideoplayertest.R;

/**
 * 在列表中使用jiecaovideoplayer
 *
 * @author ZD
 *         created at 2017/6/29 14:26
 *         description：
 */
public class ListViewActivity extends AppCompatActivity implements View.OnClickListener {
    Button mNormalList, mViewPagerList, mMultiHolderList, mRecyleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("About ListView");
        setContentView(R.layout.activity_list_view);

        mNormalList = (Button) findViewById(R.id.normal_list);
        mViewPagerList = (Button) findViewById(R.id.viewpayer_list);
        mMultiHolderList = (Button) findViewById(R.id.multi_holder_list);
        mRecyleView = (Button) findViewById(R.id.recyleview);

        mNormalList.setOnClickListener(this);
        mViewPagerList.setOnClickListener(this);
        mMultiHolderList.setOnClickListener(this);
        mRecyleView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normal_list://普遍的ListView中播放视频
                startActivity(new Intent(ListViewActivity.this, ListViewNormalActivity.class));
                break;
            case R.id.viewpayer_list://ViewPager中的ListView播放视频
                startActivity(new Intent(ListViewActivity.this, ListViewViewpagerActivity.class));
                break;
            case R.id.multi_holder_list://分类型的 ListView中播放视频
                startActivity(new Intent(ListViewActivity.this, ListViewMultiHolderActivity.class));
                break;
            case R.id.recyleview://Recyleview中播放视频
                startActivity(new Intent(ListViewActivity.this, RecyclerViewNormalActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
