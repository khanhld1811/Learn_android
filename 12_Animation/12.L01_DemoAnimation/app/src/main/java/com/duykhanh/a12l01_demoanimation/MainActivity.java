package com.duykhanh.a12l01_demoanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout transitionsContainer ;
    TextView text;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transitionsContainer = findViewById(R.id.transitions_container);
        button = findViewById(R.id.button);
        text = findViewById(R.id.text);


        button.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                visible = !visible;
                text.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
    }


}
