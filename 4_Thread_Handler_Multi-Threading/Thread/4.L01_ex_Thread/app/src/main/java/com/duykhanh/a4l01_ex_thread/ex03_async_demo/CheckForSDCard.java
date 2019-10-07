package com.duykhanh.a4l01_ex_thread.ex03_async_demo;

import android.os.Environment;

/**
 * Created by Abhi on 11 Mar 2018 011.
 */

public class CheckForSDCard {
    //Method to Check If SD Card is mounted or not
    public static boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}