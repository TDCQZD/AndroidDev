package com.example.audiotest;

import android.app.Service;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * 音频管理器
 *
 * @author ZD
 *         created at 2017/8/7 16:41
 *         description：
 */
public class MainActivity extends AppCompatActivity {
    Button play, up, down;
    ToggleButton mute;
    AudioManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取系统的音频服务
        aManager = (AudioManager) getSystemService(
                Service.AUDIO_SERVICE);
        // 获取界面中三个按钮和一个ToggleButton控件
        play = (Button) findViewById(R.id.play);
        up = (Button) findViewById(R.id.up);
        down = (Button) findViewById(R.id.down);
        mute = (ToggleButton) findViewById(R.id.mute);
        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton source,
                                         boolean isChecked) {
                // 指定调节音乐的音频，根据isChecked确定是否需要静音
                aManager.setStreamMute(AudioManager.STREAM_MUSIC,
                        isChecked);
            }
        });
    }

    public void play(View view) {
        //初始化MediaPlayer对象，准备播放音乐
        MediaPlayer mPlayer = MediaPlayer.create(
                MainActivity.this, R.raw.earth);
        // 设置循环播放
        mPlayer.setLooping(true);
        // 开始播放
        mPlayer.start();
    }

    public void up(View view) {
// 指定调节音乐的音频，增大音量，而且显示音量图形示意
        aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    }

    public void down(View view) {
// 指定调节音乐的音频，降低音量，而且显示音量图形示意
        aManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }


}


