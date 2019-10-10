package com.duykhanh.a5l01broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Broadcast broadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        broadcast = new Broadcast();
        /*
        *  Sự kiện này lắn nghe quyền AIRPLANE_MODE (bật tắt chế độ máy bay)
        */
        IntentFilter filter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        registerReceiver(broadcast,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcast);
    }
}
