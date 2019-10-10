package com.duykhanh.downloadmultithread;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int WRITE_REQUEST_CODE = 300;
    Button btnDownload;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    EditText edt_url;
    DownloadImageTask downloadImageTask1,downloadImageTask2,downloadImageTask3,downloadImageTask4;
    int dem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        btnDownload = findViewById(R.id.btn_download);
        edt_url = findViewById(R.id.edt_url);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4);

        btnDownload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_download:

                String url;
                ++dem;
                /*
                 * Gọi execute và truyền vào tham số kiểu string -> là một đường dẫn
                 */
                //Check if SD card is present or not
                if (CheckForSDCard.isSDCardPresent()) {

                    //check if app has permission to write to the external storage.
                    if (EasyPermissions.hasPermissions(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //Get the URL entered
                        url = edt_url.getText().toString();


                        if (dem == 1) {
                            downloadImageTask1 = new DownloadImageTask(progressBar1);
                            StartAsyncTaskInParallel(downloadImageTask1,url);
                            Toast.makeText(this, dem + "", Toast.LENGTH_SHORT).show();
                        }
                        if (dem == 2) {
                            Toast.makeText(this, ""+progressBar1.getProgress(), Toast.LENGTH_SHORT).show();

                            downloadImageTask2 =  new  DownloadImageTask(progressBar2);
                            StartAsyncTaskInParallel(downloadImageTask2,url);
                            Toast.makeText(this, dem + "", Toast.LENGTH_SHORT).show();
                        }
                        if (dem == 3) {
                            downloadImageTask3 = new DownloadImageTask(progressBar3);
                            downloadImageTask3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
                            Toast.makeText(this, "" + dem, Toast.LENGTH_SHORT).show();
                        }

                        if (dem == 4) {
                            downloadImageTask4 = new DownloadImageTask(progressBar4);
                            downloadImageTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
                            Toast.makeText(this, "" + dem, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        //If permission is not present request for the same.
                        EasyPermissions.requestPermissions(MainActivity.this, "NO", WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                    }

                } else {
                    Toast.makeText(getApplicationContext(),
                            "SD Card not found", Toast.LENGTH_LONG).show();

                }


                break;
        }

    }

    // TODO (2) Download task 1
    private class DownloadImageTask extends AsyncTask<String, Integer, String> {
        /*
         * Nhận tham số truyền vào từ phương thức execute và return về kết quả sau khi xử lý
         * Phương thức này chạy trong một luồng riêng.
         * Không thể dùng hàm này để thực hiện các thao tác liên quan đến UI
         */
        ProgressBar progress;

        private String fileName;
        private String folder;

        public DownloadImageTask(ProgressBar target){
            progress = target;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            int count;
            try {
                URL url = new URL(urls[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL

                fileName = urls[0].substring(urls[0].lastIndexOf('/') + 1, urls[0].length());


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
                    SystemClock.sleep(1000);
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
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());

            }
            return "Something went wrong";

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, "Download comple!" + s, Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(DownloadImageTask task,String url) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,url);
        else
            task.execute(url);
    }

//    //TODO (3) Download task 2
//    private class DownloadImageTask2 extends AsyncTask<String, Integer, String> {
//        /*
//         * Nhận tham số truyền vào từ phương thức execute và return về kết quả sau khi xử lý
//         * Phương thức này chạy trong một luồng riêng.
//         * Không thể dùng hàm này để thực hiện các thao tác liên quan đến UI
//         */
//        ProgressBar progress;
//        private String fileName;
//        private String folder;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress = findViewById(R.id.progressBar2);
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            int count;
//            try {
//                URL url = new URL(urls[0]);
//                URLConnection connection = url.openConnection();
//                connection.connect();
//                // getting file length
//                int lengthOfFile = connection.getContentLength();
//
//                // input stream to read file - with 8k buffer
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//
//                //Extract file name from URL
//
//                fileName = urls[0].substring(urls[0].lastIndexOf('/') + 1, urls[0].length());
//
//
//                //Append timestamp to file name
//
//                fileName = timestamp + "_" + fileName;
//
//                //External directory path to save file
//                folder = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";
//
//                //Create androiddeft folder if it does not exist
//                File directory = new File(folder);
//
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                // Output stream to write file
//                OutputStream output = new FileOutputStream(folder + fileName);
//
//                byte data[] = new byte[2048];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    SystemClock.sleep(1000);
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress(Integer.valueOf("" + (int) ((total * 100) / lengthOfFile)));
//                    Log.d("AAA", "Progress: " + (int) ((total * 100) / lengthOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//                return "Downloaded at: " + folder + fileName;
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//
//            }
//            return "Something went wrong";
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            progress.setProgress(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, "Download comple!" + s, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    //TODO (3) Download task 3
//    private class DownloadImageTask3 extends AsyncTask<String, Integer, String> {
//        /*
//         * Nhận tham số truyền vào từ phương thức execute và return về kết quả sau khi xử lý
//         * Phương thức này chạy trong một luồng riêng.
//         * Không thể dùng hàm này để thực hiện các thao tác liên quan đến UI
//         */
//        ProgressBar progress;
//        private String fileName;
//        private String folder;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress = findViewById(R.id.progressBar3);
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            int count;
//            try {
//                URL url = new URL(urls[0]);
//                URLConnection connection = url.openConnection();
//                connection.connect();
//                // getting file length
//                int lengthOfFile = connection.getContentLength();
//
//                // input stream to read file - with 8k buffer
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//
//                //Extract file name from URL
//
//                fileName = urls[0].substring(urls[0].lastIndexOf('/') + 1, urls[0].length());
//
//
//                //Append timestamp to file name
//
//                fileName = timestamp + "_" + fileName;
//
//                //External directory path to save file
//                folder = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";
//
//                //Create androiddeft folder if it does not exist
//                File directory = new File(folder);
//
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                // Output stream to write file
//                OutputStream output = new FileOutputStream(folder + fileName);
//
//                byte data[] = new byte[2048];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    SystemClock.sleep(1000);
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress(Integer.valueOf("" + (int) ((total * 100) / lengthOfFile)));
//                    Log.d("AAA", "Progress: " + (int) ((total * 100) / lengthOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//                return "Downloaded at: " + folder + fileName;
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//
//            }
//            return "Something went wrong";
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            progress.setProgress(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, "Download comple!" + s, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    //TODO (3) Download task 3
//    private class DownloadImageTask4 extends AsyncTask<String, Integer, String> {
//        /*
//         * Nhận tham số truyền vào từ phương thức execute và return về kết quả sau khi xử lý
//         * Phương thức này chạy trong một luồng riêng.
//         * Không thể dùng hàm này để thực hiện các thao tác liên quan đến UI
//         */
//        ProgressBar progress;
//        private String fileName;
//        private String folder;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progress = findViewById(R.id.progressBar3);
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            int count;
//            try {
//                URL url = new URL(urls[0]);
//                URLConnection connection = url.openConnection();
//                connection.connect();
//                // getting file length
//                int lengthOfFile = connection.getContentLength();
//
//                // input stream to read file - with 8k buffer
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//
//                //Extract file name from URL
//
//                fileName = urls[0].substring(urls[0].lastIndexOf('/') + 1, urls[0].length());
//
//
//                //Append timestamp to file name
//
//                fileName = timestamp + "_" + fileName;
//
//                //External directory path to save file
//                folder = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";
//
//                //Create androiddeft folder if it does not exist
//                File directory = new File(folder);
//
//                if (!directory.exists()) {
//                    directory.mkdirs();
//                }
//
//                // Output stream to write file
//                OutputStream output = new FileOutputStream(folder + fileName);
//
//                byte data[] = new byte[2048];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    SystemClock.sleep(1000);
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress(Integer.valueOf("" + (int) ((total * 100) / lengthOfFile)));
//                    Log.d("AAA", "Progress: " + (int) ((total * 100) / lengthOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//                return "Downloaded at: " + folder + fileName;
//
//            } catch (Exception e) {
//                Log.e("Error: ", e.getMessage());
//
//            }
//            return "Something went wrong";
//
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            progress.setProgress(values[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Toast.makeText(MainActivity.this, "Download comple!" + s, Toast.LENGTH_SHORT).show();
//        }
//    }

//    private class MyObject {
//        private ProgressBar progressBar;
//        private int interval;
//
//        MyObject(ProgressBar progressBar, int interval) {
//            this.progressBar = progressBar;
//            this.interval = interval;
//        }
//    }
}
