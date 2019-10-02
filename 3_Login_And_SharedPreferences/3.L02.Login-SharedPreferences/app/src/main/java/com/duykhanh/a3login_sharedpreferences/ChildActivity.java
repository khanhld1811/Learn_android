package com.duykhanh.a3login_sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

    private TextView tvUserName, tvPassword;
    private SharedPreferences sharedPreferencesLogout;
    private SharedPreferences.Editor editorClearData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        setTitle(ChildActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        * Khởi tạo biến SharedPreferences
        */
        sharedPreferencesLogout = getSharedPreferences("LOGIN",MODE_PRIVATE);
        /*
         * Mở giao diện sửa đổi trong SharedPreferences
         */
        editorClearData         = sharedPreferencesLogout.edit();
        /*
        * Gọi hàm ánh xạ cho các view càn thiết
        */
        initViews();

        /*
         * Lấy giá trị được truyền qua từ MainActivity
         */
        Intent iGetLogin = getIntent();
        /*
        * Đưa giá trị vừa lấy được vào textview trên màn hình ChildActivity
        */
        tvUserName.setText(iGetLogin.getStringExtra("USERNAME"));
        tvPassword.setText(iGetLogin.getStringExtra("PASSWORD"));
    }

    private void initViews() {
        tvUserName  = findViewById(R.id.tv_username);
        tvPassword  = findViewById(R.id.tv_password);

        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * Xóa toàn bộ dữ liệu tài khoản người dùng đã lưu khi nhấn nút đăng xuất
                */
                editorClearData.clear();
                editorClearData.commit();
                finish();
            }
        });
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
