package com.duykhanh.listviewandspinner.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.duykhanh.listviewandspinner.R;

public class SpinnerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_main);

        setTitle("SpinnerMainActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btnSpinnerBasic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpinnerMainActivity.this,SpinnerBasicActivity.class));
            }
        });

        findViewById(R.id.btnCustomSpinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpinnerMainActivity.this, CustomSpinnerActivity.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
