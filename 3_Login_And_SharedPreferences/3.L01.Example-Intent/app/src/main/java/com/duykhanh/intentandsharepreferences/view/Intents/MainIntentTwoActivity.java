package com.duykhanh.intentandsharepreferences.view.Intents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.duykhanh.intentandsharepreferences.R;

public class MainIntentTwoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnOpenWebPage, btnOpenLocalMap, btnShareText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent_two);
        setTitle(MainIntentTwoActivity.class.getSimpleName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
    }

    private void initViews() {
        //TODO (1) ánh xạ button
        btnOpenWebPage  = findViewById(R.id.btn_openWebPage);
        btnOpenLocalMap = findViewById(R.id.btn_openLocalMap);
        btnShareText    = findViewById(R.id.btn_shareText);

        // TODO (2) Button that was Clicked
        btnOpenWebPage.setOnClickListener(this);
        btnOpenLocalMap.setOnClickListener(this);
        btnShareText.setOnClickListener(this);
    }

    //TODO (4) open webpage
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // TODO (5) Kiểm tra hệ thống android xem có ứng dụng nào có thể thực hiện yêu cầu này không
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "NO!", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO (6) Show Map
    private void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareText(String textToShare){
        String mimeType = "text/plain";
        String title = "Learning How to Share";

        ShareCompat.IntentBuilder.from(this)
                .setChooserTitle(title)
                .setType(mimeType)
                .setText(textToShare)
                .startChooser();

    }

    //TODO (3) bắt sựu kiện onclick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_openWebPage:
                String url = "https://github.com/search?q=library+android";
                openWebPage(url);
                break;
            case R.id.btn_openLocalMap:
                String addressString = "1600 Amphitheatre Parkway, CA";
                // set path local map
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("geo")
                        .path("0,0")
                        .appendQueryParameter("q", addressString);
                Uri addressUri = builder.build();

                showMap(addressUri);
                break;
            case R.id.btn_shareText:
                String textToShare = "Hello there";
                shareText(textToShare);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
