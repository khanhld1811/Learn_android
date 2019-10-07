package com.duykhanh.a4l01_ex_thread.ex04_download_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duykhanh.a4l01_ex_thread.R;

public class DownloadFileDemo extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000 ;
    EditText ed_url;
    Button btn_download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file_demo);

        initView();

        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        String [] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        
                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else{
                        
                    }
                }
                else{
                   startDownloading(); 
                }
            }
        });
    }

    private void initView() {
        ed_url = findViewById(R.id.ed_url1);
        btn_download = findViewById(R.id.btn_download);
    }

    private void startDownloading() {
        String utl = ed_url.getText().toString();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(utl));

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Download file...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"" + System.currentTimeMillis());

                //get download servixe and enque file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startDownloading();
                }else{
                    Toast.makeText(this, "Permissin denied...!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
