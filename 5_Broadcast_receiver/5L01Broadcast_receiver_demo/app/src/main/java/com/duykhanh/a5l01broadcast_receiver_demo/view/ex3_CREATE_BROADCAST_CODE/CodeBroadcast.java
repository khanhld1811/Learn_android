package com.duykhanh.a5l01broadcast_receiver_demo.view.ex3_CREATE_BROADCAST_CODE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CodeBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Chế độ máy bay", Toast.LENGTH_SHORT).show();
    }
}
