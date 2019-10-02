package com.duykhanh.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duykhanh.layout.ViewMain.ControlAcitivity;
import com.duykhanh.layout.ViewMain.LayoutActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnUILayout, btnUIControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("UI Interface");

        initViews();
    }

    private void initViews() {
        btnUILayout = findViewById(R.id.btnUILayout);
        btnUIControl = findViewById(R.id.btnUIControl);

        btnUILayout.setOnClickListener(this);
        btnUIControl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnUILayout:
                startActivity(new Intent(this, LayoutActivity.class));
                break;
            case R.id.btnUIControl:
                startActivity(new Intent(this, ControlAcitivity.class));
                break;
        }
    }
}
