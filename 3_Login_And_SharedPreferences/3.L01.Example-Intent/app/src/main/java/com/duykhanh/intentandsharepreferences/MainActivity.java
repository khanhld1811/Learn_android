package com.duykhanh.intentandsharepreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duykhanh.intentandsharepreferences.view.Intents.MainIntentOneActivity;
import com.duykhanh.intentandsharepreferences.view.Intents.MainIntentTwoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exampleIntent();
    }

    private void exampleIntent(){
        findViewById(R.id.btnIntent).setOnClickListener(new View.OnClickListener() {
            @Override

            /* Thực hiện một hành động nào đó khi nhân vào nut này */
            public void onClick(View view) {

//                Intent read1 = new Intent();
//                read1.setAction(Intent.ACTION_VIEW);
//                read1.setData(ContactsContract.Contacts.CONTENT_URI);
//
//                // Verify that the intent will reoslve to an activily
//                if(read1.resolveActivity(getPackageManager()) != null){
//                    startActivity(read1);
//                }
//                startActivity(read1);

                startActivity(new Intent(MainActivity.this,MainIntentOneActivity.class));
            }
        });

        findViewById(R.id.btn_intent_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainIntentTwoActivity.class));
            }
        });
    }

}
