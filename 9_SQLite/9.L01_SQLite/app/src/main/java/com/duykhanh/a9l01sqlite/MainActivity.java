package com.duykhanh.a9l01sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.duykhanh.a9l01sqlite.Interface.ViewMainClickListener;
import com.duykhanh.a9l01sqlite.View.FragmentAdd_data;
import com.duykhanh.a9l01sqlite.View.FragmentShowData;
import com.duykhanh.a9l01sqlite.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter_tab;
    private FragmentShowData fragmentShowData;

    private ViewMainClickListener callback;

//    public MainActivity (ViewMainClickListener callback){
//        this.callback = callback;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        firstCreate();
        addFragmentViewPager();
    }

    private void firstCreate(){
        adapter_tab = new ViewPagerAdapter(getSupportFragmentManager());
        fragmentShowData = new FragmentShowData();
    }

    private void initView() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
    }

    private void addFragmentViewPager(){
        adapter_tab.addFragment(new FragmentAdd_data(),"Thêm học sinh");
        adapter_tab.addFragment(new FragmentShowData(),"Danh sách học sinh");

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(getResources().getColor(android.R.color.black),getResources().getColor(R.color.colorWhite));
        viewPager.setAdapter(adapter_tab);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 1) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
