package com.duykhanh.a4l01_ex_thread.ex02_handler_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.duykhanh.a4l01_ex_thread.R;

public class Ex_handler_02 extends AppCompatActivity {

    private static final String TAG = Ex_handler_02.class.getSimpleName();
    private Button btnStart, btnStop;
    private Switch swt_click;

    private volatile  boolean stopThrad = false;

    /*
    * Cần phải gửi kết quả qua handler cho UI thread
    */

    private Handler mainHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_handler_02);
        initView();
    }

    private void initView() {
        btnStart = findViewById(R.id.btn_start);
    }

    public void startThread(View view) {
//        for(int i = 0; i < 10; i++){
//            Log.d(TAG, "startThread: " + i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        ExampleThread thread = new ExampleThread(10);
//        thread.start();

//        ExampleRunnable thread = new ExampleRunnable(10);
//        new Thread(thread).start();

        stopThrad = false;
        ExampleRunnableHandler thread = new ExampleRunnableHandler(10);
        new Thread(thread).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
    }

    public void stopThread(View view) {
        stopThrad = true;
    }

    public void switchClick(View view) {
    }

    class ExampleThread extends Thread {
        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunnable implements Runnable {
        int seconds;

        ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                if(i == 5){
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btnStart.setText("50%");
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunnableHandler implements Runnable {
        int seconds;

        ExampleRunnableHandler(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                if(stopThrad)
                    return;
                if(i == 5){
//                    Handler threadHandler = new Handler(Looper.getMainLooper());
//                    threadHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            btnStart.setText("50%");
//                        }
//                    });

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnStart.setText("50%");
                        }
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
