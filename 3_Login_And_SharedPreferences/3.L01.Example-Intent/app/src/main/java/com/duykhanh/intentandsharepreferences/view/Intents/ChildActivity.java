package com.duykhanh.intentandsharepreferences.view.Intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.duykhanh.intentandsharepreferences.R;

public class ChildActivity extends AppCompatActivity {

    private TextView mDisplayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        setTitle(ChildActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDisplayText = findViewById(R.id.tv_display);

        Intent intentThatStartedThisActivity = getIntent();

        // Kiểm trả có dữ liệu được gửi qua không
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayText.setText(textEntered);
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
