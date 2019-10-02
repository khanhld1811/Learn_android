package com.duykhanh.listviewandspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.duykhanh.listviewandspinner.view.ListViewActivity;
import com.duykhanh.listviewandspinner.view.ListViewMainActivity;
import com.duykhanh.listviewandspinner.view.SpinnerMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        findViewById(R.id.btnListView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListViewMainActivity.class));
            }
        });

        findViewById(R.id.btn_spinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SpinnerMainActivity.class));
            }
        });
    }
}
