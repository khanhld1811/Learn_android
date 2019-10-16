package com.duykhanh.musicplayer.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.duykhanh.musicplayer.R;

public class MusicPlayService extends Service {

    private IBinder iBinder = new BoundMusicPlayer();
    MediaPlayer player;

    public static final int NOTIFY_ID = 123;
    public static final String ACTION_NOTIFICATION = "action_notification";
    public static final String BUTTON_INDEX = "button_index";
    public static final String BUTTON_PREV = "0";
    public static final String BUTTON_PLAY = "1";
    public static final String BUTTON_NEXT = "2";

    private boolean isPlay = true;

    private RemoteViews remoteView;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        remoteView = new RemoteViews(getPackageName(),R.layout.layout_notification);
        Intent intent = new Intent(this, MusicPlayService.class);
        intent.setAction(ACTION_NOTIFICATION);

        intent.putExtra(BUTTON_INDEX,BUTTON_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getService(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);


        intent.putExtra(BUTTON_INDEX, BUTTON_PLAY);
        PendingIntent  pendingIntentPrev = PendingIntent.getService(this, 2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_play, pendingIntentPrev);

        intent.putExtra(BUTTON_INDEX, BUTTON_NEXT);
        pendingIntentPrev = PendingIntent.getService(this, 3, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_next, pendingIntentPrev);

        intent.putExtra(BUTTON_INDEX, BUTTON_PREV);
        pendingIntentPrev = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.btn_prev, pendingIntentPrev);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContent(remoteView);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        String stringExtra = intent.getStringExtra(BUTTON_INDEX);
        if (TextUtils.equals(action, ACTION_NOTIFICATION)) {
            if (TextUtils.equals(stringExtra, BUTTON_PLAY)) {
                if(isPlay) {
                    playMusic();
                    remoteView.setImageViewResource(R.id.btn_play, R.drawable.pause);
                    mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
                }else{
                    remoteView.setImageViewResource(R.id.btn_play,R.drawable.ic_play_arrow_black_24dp);
                    mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
                    pauseMusic();
                }
            }
        }
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        mNotificationManager.cancelAll();
        super.onDestroy();
    }

    public class BoundMusicPlayer extends Binder {
        public MusicPlayService getService() {
            return MusicPlayService.this;
        }
    }

    public void playMusic() {
        isPlay = false;
        remoteView.setImageViewResource(R.id.btn_play, R.drawable.pause);
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
        if(player == null)
            player = MediaPlayer.create(this, R.raw.jazz_in_paris);
        player.start();
    }

    public void pauseMusic(){
        isPlay = true;
        remoteView.setImageViewResource(R.id.btn_play, R.drawable.ic_play_arrow_black_24dp);
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
        if(player != null){
            player.pause();
            mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
        }
    }

    public void stopMusic(){
        remoteView.setImageViewResource(R.id.btn_play, R.drawable.ic_play_arrow_black_24dp);
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "Stop music", Toast.LENGTH_SHORT).show();
        }
    }
}
