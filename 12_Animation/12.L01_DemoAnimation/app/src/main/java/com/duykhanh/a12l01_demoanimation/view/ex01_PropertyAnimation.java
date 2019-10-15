package com.duykhanh.a12l01_demoanimation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.duykhanh.a12l01_demoanimation.R;

public class ex01_PropertyAnimation extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex01__drawable_animation);

        ImageView wheel = findViewById(R.id.wheel);
        /*
        * Lấy hiệu ứng được thiết lập trong animation
        */
        AnimatorSet wheelSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.wheel_spin);
        /*
        * Gắn hiệu ứng vào hình ảnh wheel
        */
        wheelSet.setTarget(wheel);
        /*
        * Bắt đầu animation
        */
        wheelSet.start();

        //get the sun view
        ImageView sun = findViewById(R.id.sun);

        //load the sun movement animation
        AnimatorSet sunSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sun_swing);

        //set the view as target
        sunSet.setTarget(sun);

        //start the animation
        sunSet.start();

        /*
        * ts1: tham chiếu layou
        * ts2: thuộc tính muốn thay dổi (backgroundColor,textColor,...)
        * ts3,ts4: giá trị thay dổi
        */
        ValueAnimator skyAnim = ObjectAnimator.ofInt(findViewById(R.id.car_layout), "backgroundColor",
                Color.rgb(0x66, 0xcc, 0xff), Color.rgb(0x00, 0x66, 0x99));
        /*
        * Thiết lập thời gian mỗi hiệu ứng
        */
        skyAnim.setDuration(3000);
        /*
        * Số lần lặp
        * INFINITE: lặp vô hạn
        * int: số lần lặp
        */
        skyAnim.setRepeatCount(2);
        /*
        * Cách thức lặp
        * REVERSE: đảo ngược
        * RESTART: bắt đầu lại
        */
        skyAnim.setRepeatMode(ValueAnimator.REVERSE);
        skyAnim.setEvaluator(new ArgbEvaluator());
        skyAnim.start();

        ObjectAnimator cloundAnim = ObjectAnimator.ofFloat(findViewById(R.id.clound1), "x", -350);
        cloundAnim.setDuration(3000);
        cloundAnim.setRepeatCount(ValueAnimator.INFINITE);
        cloundAnim.setRepeatMode(ValueAnimator.REVERSE);
        cloundAnim.start();

        ObjectAnimator cloundAnim2 = ObjectAnimator.ofFloat(findViewById(R.id.cloud2), "x", -300);
        cloundAnim2.setDuration(3000);
        cloundAnim2.setRepeatCount(ValueAnimator.INFINITE);
        cloundAnim2.setRepeatMode(ValueAnimator.REVERSE);
        cloundAnim2.start();
    }
}
