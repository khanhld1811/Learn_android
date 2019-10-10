package com.duykhanh.a4l01_ex_thread.ex02_handler_demo.TestHandle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.duykhanh.a4l01_ex_thread.R;

public class T01_Handle_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t01__handle_01);
    }

    private class LooperThread extends Thread {
        public Handler mHandler;

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                }
            };

            Looper.loop();

            super.run();
        }
    }
}
