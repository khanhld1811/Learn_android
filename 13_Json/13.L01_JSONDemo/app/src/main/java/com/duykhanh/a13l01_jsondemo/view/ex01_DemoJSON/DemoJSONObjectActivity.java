package com.duykhanh.a13l01_jsondemo.view.ex01_DemoJSON;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.a13l01_jsondemo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class DemoJSONObjectActivity extends AppCompatActivity {

    private static final String TAG = DemoJSONObjectActivity.class.getSimpleName();
    private TextView txtId,txtName,txtEmail,txtGender, txtMobile,txtHome,txtOffice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_jsonobject);

        initView();

        String json = loadJSONFromAsset();

        try {
            JSONObject jObj = new JSONObject(json);

            JSONArray userArray = jObj.getJSONArray("users");

            JSONObject jsonObjectOne = userArray.getJSONObject(0);

            String id = jsonObjectOne.getString("id");
            String name = jsonObjectOne.getString("name");
            String email = jsonObjectOne.getString("email");
            String gender = jsonObjectOne.getString("gender");

            JSONObject contactJson = jsonObjectOne.getJSONObject("contact");
            String mobile  = contactJson.getString("mobile");
            String home = contactJson.getString("home");
            String office = contactJson.getString("office");

            txtId.setText(id);
            txtName.setText(name);
            txtEmail.setText(email);
            txtGender.setText(gender);
            txtMobile.setText(mobile);
            txtHome.setText(home);
            txtOffice.setText(office);

            Toast.makeText(this, "name: " + contactJson, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    private void initView() {
        txtId = findViewById(R.id.txt_id);
        txtName = findViewById(R.id.txt_name);
        txtEmail = findViewById(R.id.txt_email);
        txtGender = findViewById(R.id.txt_gender);
        txtMobile = findViewById(R.id.txt_mobile);
        txtHome = findViewById(R.id.txt_home);
        txtOffice = findViewById(R.id.txt_office);
    }

    public String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("users_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
