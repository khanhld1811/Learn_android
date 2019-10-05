package com.duykhanh.exampleasyntask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private EditText ed_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        new randomNumberTask().execute();
        new increaseNumberOdd().execute();
    }

    private void initViews() {
        ed_description = findViewById(R.id.ed_description);
    }

    public void onExitListener(View view) {
        finish();
    }

    public void onClearListener(View view) {
        ed_description.setText("");
    }

    private class randomNumberTask extends AsyncTask<Void, Void,Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            SystemClock.sleep(1000);
            int rNumber = (int) (Math.random() * (100 - 50) + 50);
            return rNumber;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            ed_description.append("\n"+integer);
        }
    }

    private class increaseNumberOdd extends AsyncTask<Void, Void, Integer>{
        int oddNumber = 1;

        @Override
        protected Integer doInBackground(Void... voids) {
            while(true){
                SystemClock.sleep(2500);
                publishProgress();
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            oddNumber += 2;
            ed_description.append("\n" + oddNumber);
        }
    }
}
