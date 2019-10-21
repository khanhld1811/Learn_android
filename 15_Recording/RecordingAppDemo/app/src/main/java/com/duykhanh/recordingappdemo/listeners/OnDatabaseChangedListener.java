package com.duykhanh.recordingappdemo.listeners;

public interface OnDatabaseChangedListener {
    void onNewDatabaseEntryAdded();
    void onDatabaseEntryRenamed();
}
