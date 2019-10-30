package com.duykhanh.a12l01_demoanimation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.duykhanh.a12l01_demoanimation.R;

public class ex03_DrawableAnimation extends AppCompatActivity {
     AnimationDrawable animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex03__drawable_animation);

        ImageView imageView = findViewById(R.id.imageBackground);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.start();
            }
        });
    }
}
