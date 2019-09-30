package com.duykhanh.layout.ViewMain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.duykhanh.layout.R;

import java.util.ArrayList;
import java.util.List;

public class ControlAcitivity extends AppCompatActivity{

    private AutoCompleteTextView atv_name;
    private Spinner spinner;

    String[] dataName = {"Khánh", "Hoàng", "Anh", "Bình", "Cận", "Đần", "Dũng", "Hải", "Gấu", "Jerry", "Lực", "Minh", "Ninh",
            "Phương", "Quân", "Việt", "Xâu", "Sâm", "Yến", "Zilien"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_acitivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("ControlActivity");

        initViews();
        AutoCompleteTextView();
        Spinner();
    }

    private void initViews() {
        atv_name = findViewById(R.id.atv_name);
        spinner = findViewById(R.id.spinner);

    }

    private void AutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, dataName);

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

    private void Spinner() {
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        //Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);

        //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
}
