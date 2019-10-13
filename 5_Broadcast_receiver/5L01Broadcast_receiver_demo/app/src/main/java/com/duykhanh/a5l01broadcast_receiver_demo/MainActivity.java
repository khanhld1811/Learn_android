package com.duykhanh.a5l01broadcast_receiver_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duykhanh.a5l01broadcast_receiver_demo.view.ex02_SEND_BROADCAST.SendBroadcastReceiverActivity;
import com.duykhanh.a5l01broadcast_receiver_demo.view.ex3_CREATE_BROADCAST_CODE.CodeBroadcastReceiverActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_ex02).setOnClickListener(this);
        findViewById(R.id.btn_ex03).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ex02:
                startActivity(new Intent(MainActivity.this, SendBroadcastReceiverActivity.class));
                break;
            case R.id.btn_ex03:
                startActivity(new Intent(MainActivity.this, CodeBroadcastReceiverActivity.class));
                break;
        }
    }
}
