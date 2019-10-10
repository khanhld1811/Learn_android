package com.duykhanh.a5l01broadcast_receiver_demo.view.ex01_PHONE_STATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        // Kiểm tra đối tượng bundle có tồn tại hay không
        if(bundle != null){
            // Lấy trạng thái từ TelephonyManager
            String state = bundle.getString(TelephonyManager.EXTRA_STATE);
            // Kiểm tra trạng thái khi gọi
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                // Lấy số điện thoại gọi đến
                String phoneNumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

                Toast.makeText(context, phoneNumber, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
