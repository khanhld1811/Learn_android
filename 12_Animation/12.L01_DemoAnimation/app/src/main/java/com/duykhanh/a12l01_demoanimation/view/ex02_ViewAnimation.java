package com.duykhanh.a12l01_demoanimation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.duykhanh.a12l01_demoanimation.R;

public class ex02_ViewAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex02__view_animation);
    }

    public void clockwise(View view) {
        ImageView imageView =  findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        imageView.startAnimation(animation);
    }

    public void fade(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);
    }

    public void zoom(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom);
        imageView.startAnimation(animation);
    }

    public void blink(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imageView.startAnimation(animation);
    }

    public void move(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        imageView.startAnimation(animation);
    }

    public void slide(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        imageView.startAnimation(animation);
    }
}
