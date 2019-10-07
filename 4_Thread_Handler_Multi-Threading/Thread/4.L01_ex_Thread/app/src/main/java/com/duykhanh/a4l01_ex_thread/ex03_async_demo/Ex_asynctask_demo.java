package com.duykhanh.a4l01_ex_thread.ex03_async_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.a4l01_ex_thread.MainActivity;
import com.duykhanh.a4l01_ex_thread.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;

public class Ex_asynctask_demo extends AppCompatActivity {
    private static final int WRITE_REQUEST_CODE = 300;
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText ed_url,ed_url2,ed_url3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_asynctask_demo);
        ed_url = findViewById(R.id.ed_url1);
        ed_url2 = findViewById(R.id.ed_url3);
        ed_url3 = findViewById(R.id.ed_url4);
    }

    /*
    * TODO (1) Bắt sự kiến click button
    * */
    public void onAsyncTaskClick(View view) {
        /*
        * Gọi execute và truyền vào tham số kiểu string -> là một đường dẫn
        */
        //Check if SD card is present or not
        if (CheckForSDCard.isSDCardPresent()) {

            //check if app has permission to write to the external storage.
            if (EasyPermissions.hasPermissions(Ex_asynctask_demo.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Get the URL entered

                String url = ed_url.getText().toString();
                String url2 = ed_url2.getText().toString();
                String url3 = ed_url3.getText().toString();

                new DownloadImageTask().execute(url,url2,url3);

            } else {
                //If permission is not present request for the same.
                EasyPermissions.requestPermissions(Ex_asynctask_demo.this, "NO", WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        } else {
            Toast.makeText(getApplicationContext(),
                    "SD Card not found", Toast.LENGTH_LONG).show();

        }

    }

    // TODO (2) Tạo một class kết thừa AsyncTask

    private class DownloadImageTask extends AsyncTask<String, Integer, String>{
        /*
        * Nhận tham số truyền vào từ phương thức execute và return về kết quả sau khi xử lý
        * Phương thức này chạy trong một luồng riêng.
        * Không thể dùng hàm này để thực hiện các thao tác liên quan đến UI
        */
        ProgressBar progress;
        TextView tv_task;
        private String fileName;
        private String folder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = findViewById(R.id.progress);
            tv_task = findViewById(R.id.tv_task);
        }

        @Override
        protected String doInBackground(String... urls) {
            int count;

            try {
                for (int i = 0; i < urls.length;i++) {
                    URL url = new URL(urls[i]);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    // getting file length
                    int lengthOfFile = connection.getContentLength();


                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                    //Extract file name from URL

                    fileName = urls[i].substring(urls[i].lastIndexOf('/') + 1, urls[i].length());


                    //Append timestamp to file name

                   fileName = timestamp + "_" + fileName;

                    //External directory path to save file
                    folder = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";

                    //Create androiddeft folder if it does not exist
                    File directory = new File(folder);

                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    // Output stream to write file
                    OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[2048];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    SystemClock.sleep(100);
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(Integer.valueOf("" + (int) ((total * 100) / lengthOfFile)));
                    Log.d("AAA", "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                }
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());

            }
            return "Something went wrong";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv_task.setText(values[0]+"%");
            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Ex_asynctask_demo.this, "Download comple!" + s, Toast.LENGTH_SHORT).show();
        }
    }
}
