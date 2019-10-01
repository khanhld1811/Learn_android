package com.duykhanh.listviewandspinner.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.adapter.ListViewAdapter;
import com.duykhanh.listviewandspinner.adapter.SpinnerAdapter;
import com.duykhanh.listviewandspinner.model.Contact;
import com.duykhanh.listviewandspinner.model.Country;

import java.util.ArrayList;

public class CustomSpinnerActivity extends AppCompatActivity {

    private ArrayList<Country> mCountryList;
    private SpinnerAdapter adapter;
    private Spinner sp_customSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_spinner);

        setTitle("AbsoluteLayout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCountryList = new ArrayList<>();
        mCountryList.add(new Country("VietName", R.drawable.vietname));
        mCountryList.add(new Country("ThaiLan", R.drawable.thailan));
        mCountryList.add(new Country("Singapo", R.drawable.singapor));
        mCountryList.add(new Country("UC", R.drawable.uc));
        mCountryList.add(new Country("America", R.drawable.america));
        mCountryList.add(new Country("Korean", R.drawable.korean));

        sp_customSpinner = findViewById(R.id.sp_customSpinner);

        adapter = new SpinnerAdapter(this,mCountryList);
        sp_customSpinner.setAdapter(adapter);

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
