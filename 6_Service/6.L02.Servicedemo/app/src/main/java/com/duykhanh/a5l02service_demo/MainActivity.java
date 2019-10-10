package com.duykhanh.a5l02service_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duykhanh.a5l02service_demo.view.ex01_StartService.StartServiceActivity;
import com.duykhanh.a5l02service_demo.view.ex02_BoundService.BoundServiceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StartServiceActivity.class));
            }
        });
        findViewById(R.id.btn_bound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BoundServiceActivity.class));
            }
        });

    }
}
