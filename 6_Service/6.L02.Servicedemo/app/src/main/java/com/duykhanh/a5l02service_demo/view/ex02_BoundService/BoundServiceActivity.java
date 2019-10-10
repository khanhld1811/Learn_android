package com.duykhanh.a5l02service_demo.view.ex02_BoundService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.duykhanh.a5l02service_demo.R;
import com.duykhanh.a5l02service_demo.service.BoundService;

public class BoundServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_start, btn_stop;
    private boolean boundService = false;
    private BoundService iBoundService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, BoundService.class);

        switch (view.getId()) {
            case R.id.btn_start:
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop:
                if(boundService) {
                    unbindService(serviceConnection);
                    boundService = false;
                }
                break;
        }
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.BoundExample bider = (BoundService.BoundExample) iBinder;
            iBoundService = bider.getService();

            Toast.makeText(BoundServiceActivity.this, "onServiceConnected"+iBoundService.getCurrentTime(), Toast.LENGTH_SHORT).show();
            boundService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            boundService = false;
            Toast.makeText(BoundServiceActivity.this, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
        }
    };
}
