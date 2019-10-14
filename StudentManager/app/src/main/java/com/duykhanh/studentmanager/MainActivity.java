package com.duykhanh.studentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.duykhanh.studentmanager.View.fragment.FragmentSearch;
import com.duykhanh.studentmanager.View.fragment.FragmentStudent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();

        /*
         * Khởi tạo hàm ánh xạ view
         */
        initView();

        /*
         * Khởi tạo hàm bắt sự kiện click view
         */
        onClickView();

        // load fragment mặc định
        toolbar.setTitle("Student");
        loadFragment(new FragmentStudent());
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
    }

    private void onClickView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    /*
     * Bắt sự kiện click item BottomNavigationView
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_list:
                    toolbar.setTitle("Student");
                    fragment = new FragmentStudent();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_search:
                    toolbar.setTitle("Search");
                    fragment = new FragmentSearch();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    /*
     * Hàm khởi tạo fragment và truyền vào fragment
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
