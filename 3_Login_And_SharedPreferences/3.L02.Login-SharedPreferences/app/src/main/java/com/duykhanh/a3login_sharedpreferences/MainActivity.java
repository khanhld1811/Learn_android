package com.duykhanh.a3login_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edUserName, edPassword;
    private Button btnLogin;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Khởi tạo 1 đối tượng SharedPreferences*/
        pref = getSharedPreferences("LOGIN",MODE_PRIVATE);

        /*Ánh xạ cho view*/
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Lấy account đã lưu trong SharedPreferences và đưa vào trong edittext của màn hình đăng nhập*/
        getAccountUser();
    }

    private void initViews() {

        edUserName = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);

        /*Gắn sự kiện khi người dùng click cho button login*/
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                String username = edUserName.getText().toString();
                String password = edPassword.getText().toString();

                /* gọi hàm login và truyền vào 2 tham số*/
                login(username, password);

                /*gọi hàm lưu các giá trị tài khoản và đưa vào đó 2 tham số cần lưu*/
                rememberAccount(username,password);
                break;
        }
    }

    private void login(String username, String password) {
        Intent iLogin = new Intent(this, ChildActivity.class);

        /* Gửi dữ liệu đăng nhập sang màn hình ChildActivity */
        iLogin.putExtra("USERNAME", username);
        iLogin.putExtra("PASSWORD", password);

        startActivity(iLogin);
    }

    // Lưu tài khoản vừa nhập
    private void rememberAccount(String username, String password){
        /*
        * Mở giao diện sửa đổi trong SharedPreferences
        */
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("USERNAME",username);
        editor.putString("PASSWORD",password);
        /*
        * Tiến hành lưu tài khoản
        */
        editor.commit();
    }

    // Lấy tài khoản đã lưu
    private void getAccountUser(){
        SharedPreferences prefGetdata = getSharedPreferences("LOGIN",MODE_PRIVATE);
        /*gán giá trị lấy được cho biến String*/
        String gUsername = prefGetdata.getString("USERNAME","");
        String gPassword = prefGetdata.getString("PASSWORD","");

        /* đưa giá trị vào editext */
        edUserName.setText(gUsername);
        edPassword.setText(gPassword);

    }
}
