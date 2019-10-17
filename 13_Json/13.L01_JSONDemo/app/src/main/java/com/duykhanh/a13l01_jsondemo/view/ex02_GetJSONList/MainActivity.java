package com.duykhanh.a13l01_jsondemo.view.ex02_GetJSONList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.duykhanh.a13l01_jsondemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    // ArrayList for person name, email Id's and mobile number
    ArrayList<String> personName = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumber = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the reference of RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            //get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //fetch JSONArray named users
            JSONArray userArray = obj.getJSONArray("users");
            //implement for loop for getting users list data
            Log.d(TAG, "onCreate: " + userArray.length());
            for (int i = 0; i < userArray.length(); i++) {
                //create a JSONObject for fetching single user data
                JSONObject userDetail = userArray.getJSONObject(i);
                //fetch email and name and store it in arrayList
                personName.add(userDetail.getString("name"));
                emailIds.add(userDetail.getString("email"));
                //create a object for getting contact data from JSONObject
                JSONObject contact = userDetail.getJSONObject("contact");
                //fetch mobile number and store it in arrayList
                mobileNumber.add(contact.getString("mobile"));
                Toast.makeText(this, "name: " + contact.getString("mobile"), Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: " + e.getMessage());
            Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // call the constructor of CustomAdapter to send the reference and data to Adapter

        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personName, emailIds, mobileNumber);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("users_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
