package com.duykhanh.a1l02_contrainslayout.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duykhanh.a1l02_contrainslayout.R;

public class MarginActivity extends AppCompatActivity {

    private Button btnA, btnB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margin);

        btnA = findViewById(R.id.buttonAA);
    }

    public void onClickButtonA(View view) {
        btnB.setVisibility(View.VISIBLE);
    }
}
