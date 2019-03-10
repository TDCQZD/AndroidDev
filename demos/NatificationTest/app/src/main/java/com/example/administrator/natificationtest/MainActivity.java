package com.example.administrator.natificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import java.io.File;


/**
 * Android Notification 学习使用
 *
 * @author ZD
 *         created at 2017/8/10 14:50
 *         description：
 */
public class MainActivity extends AppCompatActivity {
    NotificationManager manager;//通知管理器

    public void sendSmall(View v) {
        /*
        点击通知，跳转到详情页
         */
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("data","需要传递的数据：132131654156412222223");

        /*
        最后一个参数参数是int flags,这个值可以是FLAG_ONE_SHOT, FLAG_NO_CREATE, FLAG_CANCEL_CURRENT,
        int FLAG_CANCEL_CURRENT：如果该PendingIntent已经存在，则在生成新的之前取消当前的。
        int FLAG_NO_CREATE：如果该PendingIntent不存在，直接返回null而不是创建一个PendingIntent.
        int FLAG_ONE_SHOT:该PendingIntent只能用一次，在send()方法执行后，自动取消。
        int FLAG_UPDATE_CURRENT：如果该PendingIntent已经存在，则用新传入的Intent更新当前的数据。
        把最后一个参数改为PendingIntent.FLAG_UPDATE_CURRENT,这样在启动的Activity里就可以用接收Intent传送数据的方法正常接收
         */
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /*
        发送通知
         */
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat
                .Builder(this)
                .setContentTitle("通知")//设置通知栏标题
                .setContentText("要下雨了快回家吧！")//设置通知栏显示内容
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setSmallIcon(R.mipmap.ic_launcher)//设置通知小ICON
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置通知大图标
                .setContentIntent(pi)//设置跳转
                .setAutoCancel(true)//设置通知消失①
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))//设置通知播放音频
                /*
                手机震动  加振动器权限
                这个属性。它是一个长整型的数组，用于设置手机静止和振动的时长，以毫秒为单位。下标为0的值表示手机静止的时长，
                下标为1的值表示手机振动的时长，下标为2的值又表示手机静止的时长，以此类推。
                 所以，如果想要让手机在通知到来的时候立刻振动1秒，然后静止1秒，再振动1秒，代码如下：
                 */
//                .setVibrate(new long[]{0, 1000, 1000, 1000})//设置通知震动
                /*
              使用 setLights ()方法来实现手机LED灯闪动
              setLightsO 方法接收3个参数，第一个参数用于指定 LED 灯的颜色，第二个参数用于指定 LED 灯亮起的时长，以毫秒为单位，第三个参数用于指定 LED 灯暗去的时长，也是以毫秒为单位.
           所以，当通知到来时，如果想要实现 LED 灯以绿色的灯光一闪一闪的效果
                 */
//                .setLights(Color.GREEN, 1000, 1000)
                /*
               如果你不想进行那么多繁杂的设置，也可以直接使用通知的默认效果，它会根据当前手机的环境来决定播放什么铃声，以及如何振动
                 */
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                /*
                setStyleO 方法，这个方法允许我们构建出富文本的通知内容。也就是说通知中不光可以有文字和图标，还可以包含更多的东西。
                setStyle ()方法接收一个 Notification -Compat . Style 参数，这个参数就是用来构建具体的富文本信息的，如长文字、图片等。
                 */
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, send and sync data, and use voice actions. Get the official Android IDE and developer tools to build apps for Android."))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.mei8)))
                .setPriority(NotificationCompat.PRIORITY_MAX)//设置此通知相对优先级
                .build();
        manager.notify(1, notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendBig(View v){
//        Intent intent = new Intent(this, NotificationActivity.class);
//        intent.putExtra("data","需要传递的数据：Hello");
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent dismissIntent = new Intent(this, NotificationActivity.class);
//        dismissIntent.setAction();
        PendingIntent piDismiss = PendingIntent.getService( this, 0, dismissIntent, 0);
        Intent snoozeIntent = new Intent(this, NotificationActivity.class);
//        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat
                .Builder(this)
                .setContentTitle("通知")//设置通知栏标题
                .setContentText("要下雨了快回家吧！")//设置通知栏显示内容
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setSmallIcon(R.mipmap.ic_launcher)//设置通知小ICON
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))//设置通知大图标
//                .setContentIntent(pi)//设置跳转
                .setAutoCancel(true)//设置通知消失①
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.mei8)))
//               .setStyle(new NotificationCompat.BigTextStyle().bigText("helo"))
                .addAction(R.mipmap.ic_launcher,"删除",piDismiss)
                .addAction(R.mipmap.ic_launcher,"查看",piSnooze)
                .build();
        manager.notify(1, notification);
    }
}
