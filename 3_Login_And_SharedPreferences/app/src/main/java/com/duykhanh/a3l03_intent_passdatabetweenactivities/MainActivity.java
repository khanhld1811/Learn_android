package com.duykhanh.a3l03_intent_passdatabetweenactivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvContacts;
    private Button btnAdd;
    private Button btnUpdate;

    ArrayList<String> contacts = new ArrayList<>();

    int selectedPosition = -1;

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContacts = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);

        contacts.add("Mr.A");
        contacts.add("Mr.B");
        contacts.add("Mr.C");
        contacts.add("Mr.B");
        contacts.add("Mr.D");
        adapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1,contacts);

        lvContacts.setAdapter(adapter);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String name = contacts.get(position);

                // Lưu lại vị trí người dùng click]
                selectedPosition = position;

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("name",name);
                startActivityForResult(intent,0);
                Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                startActivityForResult(intent,99);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = 1;
                contacts.set(selectedPosition,"Mr.Khanh");
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case RESULT_OK:
                if(requestCode == 99){
                    String newName = data.getStringExtra("new_name");
                    contacts.add(newName);
                    adapter.notifyDataSetChanged();
                    //add
                } else if(requestCode == 0){
                    String newName = data.getStringExtra("new_name");
                    contacts.set(selectedPosition,newName);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "" + newName, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
