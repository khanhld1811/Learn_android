package com.duykhanh.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnLinearLayout, btnRelativeLayout, btnGridLayout, btnTableLayout, btnFrameLayout, btnConstraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnLinearLayout = findViewById(R.id.btn_linearLayout);
        btnRelativeLayout = findViewById(R.id.btn_relativeLayout);
        btnGridLayout = findViewById(R.id.btn_gridLayout);
        btnTableLayout = findViewById(R.id.btn_tableLayout);
        btnFrameLayout = findViewById(R.id.btn_frameLayout);
        btnConstraintLayout = findViewById(R.id.btn_constraintLayout);

        btnLinearLayout.setOnClickListener(this);
        btnRelativeLayout.setOnClickListener(this);
        btnGridLayout.setOnClickListener(this);
        btnTableLayout.setOnClickListener(this);
        btnFrameLayout.setOnClickListener(this);
        btnConstraintLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_linearLayout:
                    startActivity(new Intent(this,LinearLayout.class));
                break;
            case R.id.btn_relativeLayout:
                    startActivity(new Intent(this,RelativeLayout.class));
                break;

            case R.id.btn_gridLayout:
                    startActivity(new Intent(this,GridLayout.class));
                break;
            case R.id.btn_tableLayout:
                    startActivity(new Intent(this,TableLayout.class));
                break;
            case R.id.btn_frameLayout:
                    startActivity(new Intent(this,FrameLayout.class));
                break;
            case R.id.btn_constraintLayout:
                    startActivity(new Intent(this,ConstraintLayout.class));
                break;
        }
    }
}
