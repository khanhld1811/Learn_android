package com.duykhanh.recordingappdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.duykhanh.recordingappdemo.fragments.FileViewerFragment;
import com.duykhanh.recordingappdemo.fragments.RecordFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Tiện ích phân trang cho viewpager
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        onClickView();

        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);


        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

    }


    private void onClickView() {

    }

    private void initView() {
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);
    }



    @Override
    public void onClick(View view) {

    }

    public class MyAdapter extends FragmentPagerAdapter{

        private String[] titles = {getString(R.string.tab_title_record),
                getString(R.string.tab_title_saved_recordings)};

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:{
                    return RecordFragment.newInstance(position);
                }
                case 1:
                    return FileViewerFragment.newInstance(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

}

