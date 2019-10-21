package com.duykhanh.recordingappdemo.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.duykhanh.recordingappdemo.R;
import com.duykhanh.recordingappdemo.model.RecordingItem;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaybackFragment extends DialogFragment {

    private static final String LOG_TAG = "PlaybackFragment";

    private static final String ARG_ITEM = "recording_item";
    private RecordingItem item;

    private Handler mHandler = new Handler();

    private MediaPlayer mMediaPlayer = null;

    private SeekBar mSeekBar = null;
    private FloatingActionButton mPlayButton = null;
    private TextView mCurrentProgressTextView = null;
    private TextView mFileNameTextView = null;
    private TextView mFileLengthTextView = null;

    //store whether or not the mediaplayer is currently playing audio
    private boolean isPlaying = false;

    // stores minutes and seconds of the length of the life;

    long minutes = 0;
    long seconds = 0;




    public PlaybackFragment newInstace(RecordingItem item){
        // Required empty public constructor
        PlaybackFragment f = new PlaybackFragment();
        Bundle b = new Bundle();
        b.putParcelable(ARG_ITEM,item);
        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = getArguments().getParcelable(ARG_ITEM);

        long itemDuration = item.getLength();
        minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration);
        seconds = TimeUnit.MILLISECONDS.toSeconds(itemDuration) - TimeUnit.MINUTES.toSeconds(minutes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_playback,null);

        mFileNameTextView = view.findViewById(R.id.file_name_text_view);
        mFileLengthTextView = view.findViewById(R.id.file_length_text_view);
        mCurrentProgressTextView = view.findViewById(R.id.current_progress_text_view);

        mSeekBar = view.findViewById(R.id.seekbar);
        ColorFilter filter = new LightingColorFilter(
                getResources().getColor(R.color.primary),getResources().getColor(R.color.primary));
        mSeekBar.getProgressDrawable().setColorFilter(filter);
        mSeekBar.getThumb().setColorFilter(filter);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mMediaPlayer != null && fromUser){
                    mMediaPlayer.seekTo(progress);
                    mHandler.removeCallbacks(mRunnable);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getCurrentPosition()
                                    - TimeUnit.MINUTES.toSeconds(minutes));
                    mCurrentProgressTextView.setText(String.format("%02d:%02d",minutes,seconds));

                    updateSeekbar();
                }
                else if(mMediaPlayer == null && fromUser){
                    prepareMediaPlayerFromPoint(progress);
                    updateSeekbar();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(mMediaPlayer != null){
                    //remove message Handler from updateing progress bar
                    mHandler.removeCallbacks(mRunnable);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mMediaPlayer != null){
                    mHandler.removeCallbacks(mRunnable);
                    mMediaPlayer.seekTo(seekBar.getProgress());

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mMediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mMediaPlayer.getCurrentPosition()
                                    - TimeUnit.MINUTES.toSeconds(minutes));
                    mCurrentProgressTextView.setText(String.format("%02d:%02d",minutes,seconds));
                    updateSeekbar();
                }
            }
        });

        mPlayButton = view.findViewById(R.id.fab_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay(isPlaying);
                isPlaying = !isPlaying;
            }
        });

        mFileNameTextView.setText(item.getName());
        mFileLengthTextView.setText(String.format("%02d:%02d",minutes,seconds));

        builder.setView(view);

        // request a windown without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return builder.create();

    }

    @Override
    public void onStart() {
        super.onStart();

        //set transparent background
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        // disable button from dialog
        AlertDialog alertDialog = (AlertDialog) getDialog();
        alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
        alertDialog.getButton(Dialog.BUTTON_NEGATIVE).setEnabled(false);
        alertDialog.getButton(Dialog.BUTTON_NEUTRAL).setEnabled(false);

    }

    @Override
    public void onPause() {
        super.onPause();

        if(mMediaPlayer != null){
            stopPlaying();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mMediaPlayer != null){
            stopPlaying();
        }
    }


    private void onPlay(boolean isPlaying) {
        if(!isPlaying){
            //currenttly MediaPlayer is not playing audio
            if(mMediaPlayer == null){
                startPlaying();//start from beginning
            }
            else{
                //pause the MediaPlayer
                resumePlaying();
            }
        }
        else{
            //pause the MediaPlayer
            pausePlaying();
        }
    }


    private void startPlaying() {
        mPlayButton.setImageResource(R.drawable.ic_media_pause);
        mMediaPlayer = new MediaPlayer();


            try {
                mMediaPlayer.setDataSource(item.getFilePath());
                mMediaPlayer.prepare();
                mSeekBar.setMax(mMediaPlayer.getDuration());

                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mMediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "prepare() failed" );
            }
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlaying();
                }
            });
            updateSeekbar();

            //keep screen on while playing audio
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void prepareMediaPlayerFromPoint(int progress) {
        //set mediaPlayer to star tfrom middle of the audio file

        mMediaPlayer = new MediaPlayer();

        try{
            mMediaPlayer.setDataSource(item.getFilePath());
        }
        catch (IOException e){
            Log.e(LOG_TAG,  "prepare() failed");
        }

        //keep screen on while playing audio
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void pausePlaying(){
        mPlayButton.setImageResource(R.drawable.ic_media_play);
        mHandler.removeCallbacks(mRunnable);
        mMediaPlayer.pause();
    }

    private void resumePlaying(){
        mPlayButton.setImageResource(R.drawable.ic_media_pause);
        mHandler.removeCallbacks(mRunnable);
        mMediaPlayer.start();
        updateSeekbar();
    }

    private void stopPlaying(){
        mPlayButton.setImageResource(R.drawable.ic_media_play);
        mHandler.removeCallbacks(mRunnable);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;

        mSeekBar.setProgress(mSeekBar.getMax());
        isPlaying = !isPlaying;

        mCurrentProgressTextView.setText(mFileLengthTextView.getText());
        mSeekBar.setProgress(mSeekBar.getMax());

        // allow the screen to turn off again once audio is finished playing
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mMediaPlayer != null){
                int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                mSeekBar.setProgress(mCurrentPosition);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition) - TimeUnit.MINUTES.toSeconds(minutes);
                mCurrentProgressTextView.setText(String.format("%02d:%02d",minutes, seconds));

                updateSeekbar();
            }
        }
    };

    private void updateSeekbar() {
        mHandler.postDelayed(mRunnable,1000);
    }
}
