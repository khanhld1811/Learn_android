package com.duykhanh.listviewandspinner.view;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.adapter.ListViewAdapter;
import com.duykhanh.listviewandspinner.data.Cheeses;
import com.duykhanh.listviewandspinner.model.Contact;

import java.util.ArrayList;

public class CustomListViewBasicActivity extends AppCompatActivity {

    public ListView list_custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_basic);
        setTitle("CustomListViewBasicActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_custom = findViewById(R.id.list_custom);
        ArrayList<Contact> arrContact = new ArrayList<>();

        Contact contact1 = new Contact("Lê Duy Khánh", "0399 594 xxx", Color.RED);
        Contact contact2 = new Contact("Lê Duy Thành", "0398 594 xxx", Color.GREEN);
        Contact contact3 = new Contact("Lê Duy Như", "0397 594 xxx", Color.GRAY);
        Contact contact4 = new Contact("Lê Duy Đần", "0396 594 xxx", Color.YELLOW);
        Contact contact5 = new Contact("Lê Duy Độn", "0395 594 xxx", Color.BLACK);
        Contact contact6 = new Contact("Lê Duy Lâm", "0394 594 xxx", Color.BLUE);
        Contact contact7 = new Contact("Lê Duy Tặc", "0393 594 xxx", Color.CYAN);

        arrContact.add(contact1);
        arrContact.add(contact2);
        arrContact.add(contact3);
        arrContact.add(contact4);
        arrContact.add(contact5);
        arrContact.add(contact6);
        arrContact.add(contact7);

        ListViewAdapter customAdaper = new ListViewAdapter(this, R.layout.item_listview, arrContact);
        list_custom.setAdapter(customAdaper);

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
