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
package com.duykhanh.a4l01_ex_thread.ex03_async_demo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.duykhanh.a4l01_ex_thread.R;

public class RotationAsyncDemo extends Activity {

	private ProgressBar bar = null;
	private RotationAwareTask task = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotationasyncdemo);
		bar = (ProgressBar)findViewById(R.id.progress);
		task = (RotationAwareTask) getLastNonConfigurationInstance();
		
		if (task == null) {
			task = new RotationAwareTask(this);
			task.execute();
		} else {
			task.attach(this);
			updateProgress(task.getProgress());
			if (task.getProgress() >= 100) {
				markAsDone();
			}
		}
	}
	
	
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		task.detach();
		return task;
	}



	static class RotationAwareTask extends AsyncTask<Void, Void, Void> {
		RotationAsyncDemo activity = null;
		int progress = 0;
		
		RotationAwareTask (RotationAsyncDemo activity) {
			attach(activity);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 0; i < 20; i++) {
				SystemClock.sleep(200);
				publishProgress();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			if (activity == null) {
				Log.w("RotationAsync", "onPostExecute() Skipped -- No activity!!!");
			} else {
				activity.markAsDone();
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			if (activity == null) {
				Log.w("RotationAsync", "onPostExecute() Skipped -- No activity!!!");
			} else {
				progress += 5;
				activity.updateProgress(progress);
			}
		}

		private void attach (RotationAsyncDemo activity) {
			this.activity = activity;
		}
		
		private void detach () {
			this.activity = null;
		}
		
		private int getProgress() {
			return progress;
		}
	}

	public void markAsDone() {
		Toast
			.makeText(RotationAsyncDemo.this, "done", Toast.LENGTH_SHORT)
			.show();
	}

	public void updateProgress(int progress) {
		bar.setProgress(progress);
	}
}
