package com.duykhanh.recordingappdemo.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.recordingappdemo.R;
import com.duykhanh.recordingappdemo.adapter.FileViewerAdapter;

public class FileViewerFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = "FileViewerFragment";

    private int position;
    private FileViewerAdapter mFileViewerAdapter;

    public static FileViewerFragment newInstance(int position) {
        FileViewerFragment fFileViewer = new FileViewerFragment();
        Bundle bFileViewer = new Bundle();
        bFileViewer.putInt(ARG_POSITION, position);
        fFileViewer.setArguments(bFileViewer);

        return fFileViewer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        observer.startWatching();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_viewer, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //newest to oldest order (database stores from oldeest to newest)
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFileViewerAdapter = new FileViewerAdapter(getActivity(), llm);
        mRecyclerView.setAdapter(mFileViewerAdapter);

        return view;
    }

    FileObserver observer =
            new FileObserver(android.os.Environment.getExternalStorageDirectory().toString() + "/Soundrecorder") {
                //set up a file observer to watch this directory on sd card
                @Override
                public void onEvent(int event, @Nullable String file) {
                    if (event == FileObserver.DELETE) {
                        //user deletes a recording file out of the app

                        String filePath = android.os.Environment.getExternalStorageDirectory().toString()
                                + "/Soundrecorder" + file + "]";

                        Log.d(LOG_TAG, "File deleted ["
                                + android.os.Environment.getExternalStorageDirectory().toString()
                                + "/SoundRecorder" + file + "]");

                        //remove file from database and recyclerview
                        mFileViewerAdapter.removeOutOfApp(filePath);
                    }

                }
            };
}
