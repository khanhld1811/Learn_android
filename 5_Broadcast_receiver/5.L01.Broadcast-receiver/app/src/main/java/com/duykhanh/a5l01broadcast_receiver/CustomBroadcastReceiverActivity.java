package com.duykhanh.a5l01broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.duykhanh.a5l01broadcast_receiver.view.MyBroadcastReceiver;

public class CustomBroadcastReceiverActivity extends AppCompatActivity {

    private EditText edString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_broadcast_receiver);
        edString = findViewById(R.id.ed_send);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("khanh",edString.getText().toString());
                intent.setAction("test.Broadcast");
                sendBroadcast(intent);
            }
        });

    }
}
