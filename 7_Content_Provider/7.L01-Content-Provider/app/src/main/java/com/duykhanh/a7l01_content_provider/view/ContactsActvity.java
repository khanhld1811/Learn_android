package com.duykhanh.a7l01_content_provider.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duykhanh.a7l01_content_provider.R;

public class ContactsActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_actvity);
        findViewById(R.id.btn_show_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactsActvity.this,ShowAllContactActivity.class));
            }
        });

    }
}
