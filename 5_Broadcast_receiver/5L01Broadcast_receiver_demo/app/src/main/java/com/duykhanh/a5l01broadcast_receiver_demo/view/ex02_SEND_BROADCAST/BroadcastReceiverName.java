package com.duykhanh.a5l01broadcast_receiver_demo.view.ex02_SEND_BROADCAST;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastReceiverName extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
    }
}
