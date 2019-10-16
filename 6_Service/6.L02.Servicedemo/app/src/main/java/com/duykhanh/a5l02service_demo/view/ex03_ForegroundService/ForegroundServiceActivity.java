package com.duykhanh.a5l02service_demo.view.ex03_ForegroundService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duykhanh.a5l02service_demo.R;
import com.duykhanh.a5l02service_demo.service.ForegroundService;

public class ForegroundServiceActivity extends AppCompatActivity {

    private Button startService;
    private Button stopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);
        startService = findViewById(R.id.start);
        stopService = findViewById(R.id.stop_Service);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(view.getContext(), ForegroundService.class));
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(view.getContext(),ForegroundService.class));
            }
        });
    }
}
