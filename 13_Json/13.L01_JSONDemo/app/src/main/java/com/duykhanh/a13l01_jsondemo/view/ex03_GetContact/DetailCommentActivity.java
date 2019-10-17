package com.duykhanh.a13l01_jsondemo.view.ex03_GetContact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.duykhanh.a13l01_jsondemo.R;
import com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.model.Comment;

public class DetailCommentActivity extends AppCompatActivity {

    private TextView txt_is_private,
            txt_count,
            txt_attachment_id,
            txt_creator,
            txt_author,
            txt_bug_id,
            txt_text,
            txt_id,
            txt_creation_time,
            txt_raw_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comment);

        initView();
        Intent intent = getIntent();
        Comment comment = (Comment) intent.getSerializableExtra("comment");
        txt_attachment_id.setText(""+comment.getAttachment_id());
        txt_count.setText(""+comment.getCount());
        txt_is_private.setText("" + comment.isIs_private());
        txt_creator.setText(comment.getCreator());
        txt_author.setText(comment.getAuthor());
        txt_bug_id.setText(""+comment.getBug_id());
        txt_text.setText(comment.getText());
        txt_id.setText(""+comment.getId());
        txt_creation_time.setText(comment.getCreation_time());
        txt_raw_text.setText(comment.getRaw_text());
    }

    private void initView() {
        txt_is_private = findViewById(R.id.txt_is_private);
        txt_attachment_id = findViewById(R.id.txt_attachment_id);
        txt_count = findViewById(R.id.txt_count);
        txt_creator = findViewById(R.id.txt_creator);
        txt_author = findViewById(R.id.txt_author);
        txt_bug_id = findViewById(R.id.txt_bug_id);
        txt_text = findViewById(R.id.txt_text);
        txt_id = findViewById(R.id.txt_id);
        txt_creation_time = findViewById(R.id.txt_creation_time);
        txt_raw_text = findViewById(R.id.txt_raw_text);
    }
}
