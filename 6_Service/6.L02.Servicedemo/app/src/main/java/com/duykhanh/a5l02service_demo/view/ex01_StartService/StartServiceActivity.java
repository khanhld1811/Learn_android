package com.duykhanh.a5l02service_demo.view.ex01_StartService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duykhanh.a5l02service_demo.R;
import com.duykhanh.a5l02service_demo.service.StartService;

public class StartServiceActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStart, btnStop;
    public static final String NAME = "khanh";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, StartService.class);
        intent.putExtra(NAME,"Le Duy Khanh");
        switch (view.getId()){
            case R.id.btn_start:
                startService(intent);
                break;
            case R.id.btn_stop:
                stopService(intent);
                break;
        }
    }
}
