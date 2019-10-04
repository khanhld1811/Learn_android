package com.duykhanh.a4l01_ex_thread.ex01_thread_demo;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.duykhanh.a4l01_ex_thread.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ex_thread_01 extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_thread_01);
        imageView = findViewById(R.id.imageView);


    }

    public void onPowPrimeClick(View view) {
        /*
        * TODO (1) Tạo một luồng thực hiện việc load một ảnh từ internet
        *
        * Nếu chỉ dùng 1 luồng sẽ vi phạm quy tắc android về việc truy cập UI android bên ngoài UI thread
        * Để khắc phụ -> dùng View.post(Runnable) -> Runnable sẽ được chạy trên interface thread
        * */
        new Thread(new Runnable() {
            @Override
            public void run() {
            final Bitmap bitmap = loadImageFromNetwork("https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");

            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });

            }
        }).start();
    }

    private Bitmap loadImageFromNetwork(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            e.printStackTrace();
            return icon;
        }
    }
}

