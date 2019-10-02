package com.duykhanh.layout.ViewMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.duykhanh.layout.Layout.AbsoluteLayout;
import com.duykhanh.layout.Layout.ConstraintLayout;
import com.duykhanh.layout.Layout.FrameLayout;
import com.duykhanh.layout.Layout.GridLayout;
import com.duykhanh.layout.Layout.LinearLayout;
import com.duykhanh.layout.Layout.RelativeLayout;
import com.duykhanh.layout.Layout.TableLayout;
import com.duykhanh.layout.R;

public class LayoutActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btnLinearLayout, btnRelativeLayout, btnGridLayout, btnTableLayout, btnFrameLayout, btnConstraintLayout, btnAbsoluteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("LayoutActivity");

        initView();
    }

    private void initView() {
        btnLinearLayout = findViewById(R.id.btn_linearLayout);
        btnRelativeLayout = findViewById(R.id.btn_relativeLayout);
        btnGridLayout = findViewById(R.id.btn_gridLayout);
        btnTableLayout = findViewById(R.id.btn_tableLayout);
        btnFrameLayout = findViewById(R.id.btn_frameLayout);
        btnConstraintLayout = findViewById(R.id.btn_constraintLayout);
        btnAbsoluteLayout = findViewById(R.id.btn_absoluteLayout);

        btnLinearLayout.setOnClickListener(this);
        btnRelativeLayout.setOnClickListener(this);
        btnGridLayout.setOnClickListener(this);
        btnTableLayout.setOnClickListener(this);
        btnFrameLayout.setOnClickListener(this);
        btnConstraintLayout.setOnClickListener(this);
        btnAbsoluteLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_linearLayout:
                    startActivity(new Intent(this, LinearLayout.class));
                break;
            case R.id.btn_relativeLayout:
                    startActivity(new Intent(this, RelativeLayout.class));
                break;

            case R.id.btn_gridLayout:
                    startActivity(new Intent(this, GridLayout.class));
                break;
            case R.id.btn_tableLayout:
                    startActivity(new Intent(this, TableLayout.class));
                break;
            case R.id.btn_frameLayout:
                    startActivity(new Intent(this, FrameLayout.class));
                break;
            case R.id.btn_constraintLayout:
                    startActivity(new Intent(this, ConstraintLayout.class));
                break;
            case R.id.btn_absoluteLayout:
                startActivity(new Intent(this, AbsoluteLayout.class));
                break;
        }
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
