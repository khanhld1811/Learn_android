/*
 * Copyright (C) 2012 Scott M. Everts Greysky Software.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.duykhanh.a4l01_ex_thread.ex01_thread_demo;

import java.util.concurrent.atomic.AtomicBoolean;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import com.duykhanh.a4l01_ex_thread.R;

public class ThreadDemo extends Activity {
ProgressBar bar;
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			bar.incrementProgressBy(5);
		}
	};
	
	AtomicBoolean isRunning = new AtomicBoolean(false);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threaddemo);
        bar = (ProgressBar)findViewById(R.id.progress);
    }
    
    public void onStart() {
    	super.onStart();
    	
    	bar.setProgress(0);
    	
    	Thread background = new Thread(new Runnable() {
			
			public void run() {
				try {
					for (int i=0;i<20 && isRunning.get(); i++) {
						Thread.sleep(1000);
						handler.sendMessage(handler.obtainMessage());
					}
				} catch (Throwable t) {
				}
			}
		});
    	
    	isRunning.set(true);
    	background.start();
    }
    
    public void onStop() {
    	super.onStop();
    	isRunning.set(false);
    }
}
