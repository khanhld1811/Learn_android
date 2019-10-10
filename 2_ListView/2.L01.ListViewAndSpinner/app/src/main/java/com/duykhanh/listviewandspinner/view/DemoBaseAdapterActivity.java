package com.duykhanh.listviewandspinner.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.adapter.BaseAdapterDemo;
import com.duykhanh.listviewandspinner.model.Study;

import java.util.ArrayList;

public class DemoBaseAdapterActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Study> arrayList;

    String[] name = new String[]{
            "Name 1", "Name 2", "Name 3", "Name 4",
            "Name 5", "Name 6", "Name 7", "Name 8"
    };
    int[] age = new int[]{
            1, 2, 3, 4,
            5, 6, 7, 8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_base_adapter);
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();

        for(int i = 0; i < name.length;i++) {
            Study study = new Study();
            study.setName(name[i]);
            study.setAge(age[i]);
            arrayList.add(study);
        }
        Log.d("ABC", "onCreate: " + arrayList.get(0).getName());
        BaseAdapterDemo adapter = new BaseAdapterDemo(this,arrayList);
        listView.setAdapter(adapter);
    }

}
