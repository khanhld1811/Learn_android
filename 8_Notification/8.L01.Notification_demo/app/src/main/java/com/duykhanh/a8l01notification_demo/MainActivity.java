package com.duykhanh.a8l01notification_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private int numMessage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_notification_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showNotification();
            }
        });

        findViewById(R.id.btn_notification_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBigNotification();
            }
        });

    }

    private void showNotification(){
        //TODO (1) Xây dựng bó cục thông báo
        /*
         * Khởi tạo dối tượng xây dựng bố cục thông báo
         */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
        /*
         * Đặt các thuộc tính cho thông báo
         */
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Notification demo")
                .setContentText("Demo notification!");

        // TODO (2) Tạo một hành động khi người dùng nhấn vào thông báo
        /*
         * Gắn hành động start một activity khi người dùng nhấn vào notification trên thanh
         * tiêu đề
         */
        Intent resultIntent = new Intent(this,ResultActivity.class);
        /*
         * Gắn một activity lên đỉnh của một stack
         */

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ResultActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(resultPendingIntent);

//        PendingIntent content = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(content);

        //TODO (3) Cập nhật thông báo lên thanh tiêu đề
        /*
         * Cập nhật thông báo lên thanh tiêu đề
         */
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,builder.build());

    }

    private void showBigNotification(){

        Intent resultIntent = new Intent(this,ResultActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ResultActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle("New Message")
                .setContentText("You've received new message")
                .setTicker("New Message Alert!")
                .addAction(R.mipmap.ic_launcher,"call",resultPendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher);

        mBuilder.setNumber(++numMessage);

        /*
        * Cấu hình view mở rộng
        */
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        String [] events = new String[6];
        events[0] = new String("This is first line....");
        events[1] = new String("This is second line...");
        events[2] = new String("This is third line...");
        events[3] = new String("This is 4th line...");
        events[4] = new String("This is 5th line...");
        events[5] = new String("This is 6th line...");

        /*
        * Tiêu đề cho view mở rộng
        */
        inboxStyle.setBigContentTitle("Big Title Details:");

        for(int i = 0; i < events.length;i++){
            inboxStyle.addLine(events[i]);
        }

        mBuilder.setStyle(inboxStyle);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(numMessage,mBuilder.build());

    }
}
