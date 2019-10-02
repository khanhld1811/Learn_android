package com.duykhanh.listviewandspinner.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("i", "onItemSelected: " + view.getContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
