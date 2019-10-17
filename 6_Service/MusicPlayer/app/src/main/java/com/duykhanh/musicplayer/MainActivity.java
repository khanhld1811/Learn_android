package com.duykhanh.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.musicplayer.service.MusicPlayService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MusicPlayService musicPlayService;
    private boolean iMusicService = false;
    private TextView mTitleSong;
    private SeekBar mSeekBar;
    Intent intent;

    private boolean mIsPlaying = true;

    /*
    * Theo dõi hành động của service
    */
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            /*
            * Tham chếu tới MusicPlayService
            */
            MusicPlayService.BoundMusicPlayer binder = (MusicPlayService.BoundMusicPlayer) iBinder;
            musicPlayService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Tạo đối tượng intent để gọi đối tượng service
        */
        intent = new Intent(this, MusicPlayService.class);

        /*
        * Gọi hàm để bắt đầu boundService
        */
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
//        unbindService(serviceConnection);
        super.onStop();

    }

    /*
    * Dứng service khi tắt app
    */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
    * Ánh xạ view
    */
    private void initView() {
        mTitleSong = findViewById(R.id.txtSongLabel);
        findViewById(R.id.previous).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
    }

    /*
    * Gắn hành động cho view
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous:

                break;
            case R.id.play:
                if (mIsPlaying) {
                    musicPlayService.playMusic();
                    findViewById(R.id.play).setBackgroundResource(R.drawable.pause);
                    mIsPlaying = false;
                } else {
                    musicPlayService.pauseMusic();
                    findViewById(R.id.play).setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    mIsPlaying = true;
                }

                break;
            case R.id.next:

                break;
        }
    }

    /*
    * Phương thức stop music
    */
    private void stopPlayer() {
        musicPlayService.stopMusic();
    }
}

