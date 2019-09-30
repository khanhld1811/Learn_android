package com.duykhanh.layout.ViewMain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.duykhanh.layout.R;

public class ControlAcitivity extends AppCompatActivity {

    private AutoCompleteTextView atv_name;

    String [] dataName = {"Khánh", "Hoàng", "Anh", "Bình", "Cận", "Đần","Dũng", "Hải", "Gấu", "Jerry", "Lực", "Minh", "Ninh",
    "Phương", "Quân", "Việt", "Xâu","Sâm", "Yến","Zilien"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acitivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ControlActivity");

        initViews();
        AutoCompleteTextView();
    }

    private void initViews() {
        atv_name = findViewById(R.id.atv_name);
    }

    private void AutoCompleteTextView(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,dataName);

        // Số chữ bắt đầu hiện auto text
        atv_name.setThreshold(1);
        atv_name.setAdapter(adapter);

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
