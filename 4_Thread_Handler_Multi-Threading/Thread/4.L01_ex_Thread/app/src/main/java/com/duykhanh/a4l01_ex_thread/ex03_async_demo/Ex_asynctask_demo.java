package com.duykhanh.a4l01_ex_thread.ex03_async_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.duykhanh.a4l01_ex_thread.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Ex_asynctask_demo extends AppCompatActivity {

    private ImageView img_brother;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_asynctask_demo);
        img_brother = findViewById(R.id.img_brother);
    }

    /*
    * TODO (1) Bắt sự kiến click button
    * */
    public void onAsyncTaskClick(View view) {
        /*
        * Gọi execute và truyền vào tham số kiểu string -> là một đường dẫn
        */
        new DownloadImageTask().execute("https://images.pexels.com/photos/248797/pexels-photo-248797.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
    }

    // TODO (2) Tạo một class kết thừa AsyncTask
    /*
    * tham sos AsyncTask
    * String: sẽ được truyền vào hàm doInBackground() như input
    * Void :
    * Bitmap: trả về kết quả là một Bitmap từ doInBackground() và truyền nó vào onPostExecute() như đối số
    */
    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap>{
        /*
        * Nhận tham số truyền vào từ phương thức execute và return về kết quả sau khi xử lý
        * Phương thức này chạy trong một luồng riêng.
        * Không thể dùng hàm này để thực hiện các thao tác liên quan đến UI
        */
        @Override
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }
        /*
        * Phương thức đồng bộ nói với UI thread -> udpate UI.
        * Nó sẽ chạy khi doInBackground kết thúc.
        */
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            img_brother.setImageBitmap(result);
        }
    }

    private Bitmap loadImageFromNetwork(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            e.printStackTrace();
            return icon;
        }
    }
}
