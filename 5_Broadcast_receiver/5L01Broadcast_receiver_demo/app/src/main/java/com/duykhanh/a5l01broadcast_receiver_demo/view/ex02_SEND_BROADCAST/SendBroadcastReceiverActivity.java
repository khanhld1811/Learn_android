package com.duykhanh.a5l01broadcast_receiver_demo.view.ex02_SEND_BROADCAST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.duykhanh.a5l01broadcast_receiver_demo.R;

public class SendBroadcastReceiverActivity extends AppCompatActivity {

    private EditText ed_content_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_broadcast_receiver);

        initView();
    }

    private void initView() {
//        ed_content_send = findViewById(R.id.ed_content_send);
//        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.putExtra("name",ed_content_send.getText().toString());
//                intent.setAction("com.android.send.CONTENT_NAME");
//                sendBroadcast(intent);
//            }
//        });
    }
}
