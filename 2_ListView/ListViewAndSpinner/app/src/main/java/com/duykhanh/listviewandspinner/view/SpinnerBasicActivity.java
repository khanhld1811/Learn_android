package com.duykhanh.listviewandspinner.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.data.Cheeses;

public class SpinnerBasicActivity extends AppCompatActivity {

    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_basic);
        setTitle("SpinnerBasicActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinner = findViewById(R.id.spinner_basic);

        ArrayAdapter<String> arrSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Cheeses.CHESES);
        arrSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrSpinner);
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
