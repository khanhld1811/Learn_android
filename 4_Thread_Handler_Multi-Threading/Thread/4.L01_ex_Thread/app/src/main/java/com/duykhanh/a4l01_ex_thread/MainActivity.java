package com.duykhanh.a4l01_ex_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duykhanh.a4l01_ex_thread.ex01_thread_demo.Ex_thread_01;
import com.duykhanh.a4l01_ex_thread.ex01_thread_demo.ThreadDemo;
import com.duykhanh.a4l01_ex_thread.ex03_async_demo.AsyncDemo;
import com.duykhanh.a4l01_ex_thread.ex03_async_demo.RotationAsyncDemo;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onThreadDemoClick(View v) {
        Intent intent = new Intent(this, ThreadDemo.class);
        startActivity(intent);
    }

    public void onAsynch(View v) {
        Intent intent = new Intent(this, AsyncDemo.class);
        startActivity(intent);
    }

    public void onRotationAsynch (View v) {
        Intent intent = new Intent(this, RotationAsyncDemo.class);
        startActivity(intent);
    }

    public void onThreadDemo01Click(View view) {
        Intent intent = new Intent(this, Ex_thread_01.class);
        startActivity(intent);
    }
}
