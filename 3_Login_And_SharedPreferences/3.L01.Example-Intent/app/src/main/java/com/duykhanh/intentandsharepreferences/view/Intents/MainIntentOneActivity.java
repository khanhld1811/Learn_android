package com.duykhanh.intentandsharepreferences.view.Intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.duykhanh.intentandsharepreferences.R;

public class MainIntentOneActivity extends AppCompatActivity {

    EditText mNameEntry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent_one);
        setTitle(MainIntentOneActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameEntry = findViewById(R.id.ed_text);

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Gắn text người dùng vừa nhập vào Editext vào biến string.
                String textEntered = mNameEntry.getText().toString();

//              Intent intent = new Intent(MainIntentOneActivity.this,ChildActivity.class);
////            startActivity(intent);

                Context context = MainIntentOneActivity.this;

                Class destinationActivity = ChildActivity.class;

                Intent intent = new Intent(context, destinationActivity);

                // textEntrered biến lưu trữ text data
              intent.putExtra(Intent.EXTRA_TEXT,textEntered);

                startActivity(intent);
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
