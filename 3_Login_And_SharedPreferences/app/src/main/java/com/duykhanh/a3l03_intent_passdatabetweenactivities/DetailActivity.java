package com.duykhanh.a3l03_intent_passdatabetweenactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private EditText edName;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        edName = findViewById(R.id.edName);
        btnUpdate = findViewById(R.id.btnUpdate);

        //get received data
        Intent receivedIntent = getIntent();
        String name = receivedIntent.getStringExtra("name");
        Toast.makeText(this, "name: " + name, Toast.LENGTH_SHORT).show();
        if(name == null){
            btnUpdate.setText("Add");
        }
        else{
            btnUpdate.setText("Update");
        }
        edName.setText(name);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = edName.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("new_name",newName);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
