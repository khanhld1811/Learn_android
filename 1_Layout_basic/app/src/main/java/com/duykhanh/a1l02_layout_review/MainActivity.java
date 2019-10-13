package com.duykhanh.a1l02_layout_review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.duykhanh.a1l02_layout_review.view.RelativelayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickRelativeLayout(View view) {
        startActivity(new Intent(MainActivity.this, RelativelayoutActivity.class));
    }
}
