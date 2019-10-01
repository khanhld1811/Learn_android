package com.duykhanh.listviewandspinner.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.data.Cheeses;

public class ListViewActivity extends AppCompatActivity {

    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        setTitle("ListViewActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, Cheeses.CHESES);
        listView.setAdapter(adapter);

    }

    private void initViews() {
        listView = findViewById(R.id.listView);
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
