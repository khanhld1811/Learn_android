package com.duykhanh.a7l01_content_provider.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.duykhanh.a7l01_content_provider.R;

import java.util.ArrayList;

public class ShowAllContactActivity extends AppCompatActivity {

    private static final String TAG = ShowAllContactActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_contact);
        findViewById(R.id.btnShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllContacts();
            }
        });
    }

    public void showAllContacts(){
        /*
        * Phân tích chuỗi thành dạng uri
        */
        Uri uri = Uri.parse("content://contacts/people");
        Log.d(TAG, "showAllContacts: "+uri);
        /*
        * Lưu trữ danh bạ
        */
        ArrayList<String> list = new ArrayList<>();
        CursorLoader loader = new CursorLoader(this,uri,null,null,null,null);

        Cursor c1 = loader.loadInBackground();

        c1.moveToFirst();
        /*
        * Trả về việc con trỏ đến vị trí cuối hay không
        */
        while (c1.isAfterLast() == false){

            String s = "";
            /*
            * Lấy id danh bạ
            */
            String idColumnName = ContactsContract.Contacts._ID;

            int idIndex = c1.getColumnIndex(idColumnName);

            s = c1.getString(idIndex) + " _ ";
            /*
            * Tên người dùng trong danh bạ
            */
            String nameColumnName = ContactsContract.Contacts.DISPLAY_NAME;

            int nameIndex = c1.getColumnIndex(nameColumnName);
            Log.d(TAG, "showAllContacts: "+c1.getString(nameIndex));
            s += c1.getString(nameIndex);
            c1.moveToNext();
            list.add(s);
        }
        c1.close();
        ListView lv = findViewById(R.id.lv_contact);
        ArrayAdapter<String> adatper = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adatper);
    }
}
