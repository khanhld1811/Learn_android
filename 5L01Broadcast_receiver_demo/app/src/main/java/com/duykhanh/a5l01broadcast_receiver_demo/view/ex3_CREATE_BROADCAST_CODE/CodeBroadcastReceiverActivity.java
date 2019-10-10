package com.duykhanh.a5l01broadcast_receiver_demo.view.ex3_CREATE_BROADCAST_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

import com.duykhanh.a5l01broadcast_receiver_demo.R;

public class CodeBroadcastReceiverActivity extends AppCompatActivity {

    private CodeBroadcast broadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_broadcast_receiver);
        broadcast = new CodeBroadcast();
        IntentFilter filter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        registerReceiver(broadcast,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcast);
    }
}
