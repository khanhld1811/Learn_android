package com.duykhanh.a4l01_ex_thread.ex02_handler_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.a4l01_ex_thread.R;

public class Ex_handler_01 extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler;
    private static final int MSG_UPDATE_NUMBER = 100;
    private static final int MSG_UPDATE_NUMBER_DONE = 101;
    private TextView mTextNumber;
    private boolean mIsCounting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_handler_01);

        initViews();
        listenerHandler();
    }


    private void initViews() {
        mTextNumber = findViewById(R.id.text_number);
        findViewById(R.id.button_count).setOnClickListener(this);
    }

    private void countNumbers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    Message message = new Message();
                    message.what = MSG_UPDATE_NUMBER;
                    message.arg1 = i;
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mHandler.sendEmptyMessage(MSG_UPDATE_NUMBER_DONE);
            }
        }).start();
    }

    private void listenerHandler() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_UPDATE_NUMBER:
                        mIsCounting = true;
                        mTextNumber.setText(String.valueOf(msg.arg1));
                        break;
                    case MSG_UPDATE_NUMBER_DONE:
                        mTextNumber.setText("Done!");
                        mIsCounting = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_count:
                Toast.makeText(this, "" + mIsCounting, Toast.LENGTH_SHORT).show();
                if (!mIsCounting) {
                    countNumbers();
                }
                break;
            default:
                break;
        }
    }
}
