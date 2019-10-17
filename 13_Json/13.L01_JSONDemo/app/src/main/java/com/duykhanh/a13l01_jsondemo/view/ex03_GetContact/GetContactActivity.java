package com.duykhanh.a13l01_jsondemo.view.ex03_GetContact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.duykhanh.a13l01_jsondemo.R;
import com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.model.Comment;

public class GetContactActivity extends AppCompatActivity {

    private ListView lvContact;
    GetContact getContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contact);

        lvContact = findViewById(R.id.listContact);

        getContact = new GetContact(lvContact,GetContactActivity.this);
        getContact.execute();

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean is_private = getContact.commentList.get(i).isIs_private();
                int count = getContact.commentList.get(i).getCount();
                int attachment_id  = getContact.commentList.get(i).getAttachment_id();
                String creator =  getContact.commentList.get(i).getCreator();
                String time = getContact.commentList.get(i).getTime();
                String author = getContact.commentList.get(i).getAuthor();
                int bug_id = getContact.commentList.get(i).getBug_id();
                String text = getContact.commentList.get(i).getText();
                int id =  getContact.commentList.get(i).getId();
                String creation_time = getContact.commentList.get(i).getCreation_time();
                String raw_text = getContact.commentList.get(i).getRaw_text();

                Comment comment = new Comment();
                comment.setIs_private(is_private);
                comment.setCount(count);
                if(attachment_id != 0)
                    comment.setAttachment_id(attachment_id);
                comment.setCreator(creator);
                comment.setTime(time);
                comment.setAuthor(author);
                comment.setBug_id(bug_id);
                comment.setText(text);
                comment.setId(id);
                comment.setCreation_time(creation_time);
                comment.setRaw_text(raw_text);

                Intent intent = new Intent(GetContactActivity.this,DetailCommentActivity.class);
                intent.putExtra("comment",comment);
                startActivity(intent);


            }
        });
    }
}
