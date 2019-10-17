package com.duykhanh.a13l01_jsondemo.view.ex03_GetContact;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.adapter.ContactAdapter;
import com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.data.HttpHandler;
import com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetContact extends AsyncTask<Void, Void, Void> {

    private String TAG = GetContact.class.getSimpleName();

    public static String url = "https://bugzilla.mozilla.org/rest/bug/707428/comment";
    ArrayList<Comment> commentList;
    private ProgressDialog pDialog;
    private ListView lv;
    Context context;
    ContactAdapter adapter;

    public GetContact(ListView lv, Context context) {
        commentList = new ArrayList<>();
        this.lv = lv;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpHandler handler = new HttpHandler();
        String jsonStr = handler.makeServiceCall(url);
        Log.d(TAG, "doInBackground: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject bugs = jsonObject.getJSONObject("bugs");
                JSONObject key = bugs.getJSONObject("707428");
                JSONArray comments = key.getJSONArray("comments");
                Log.d(TAG, "AAA: " + comments);

                for (int i = 0; i < comments.length(); i++) {
                    JSONObject commentCreator = comments.getJSONObject(i);
                    boolean is_private = commentCreator.getBoolean("is_private");
                    int count = commentCreator.getInt("count");
                    int attachment_id = 0;
                    if (commentCreator.isNull("attachment_id") == true) {

                    } else {
                        attachment_id = commentCreator.getInt("attachment_id");
                    }
                    String creator = commentCreator.getString("creator");
                    String time = commentCreator.getString("time");
                    String author = commentCreator.getString("author");
                    int bug_id = commentCreator.getInt("bug_id");
                    String text = commentCreator.getString("text");
                    int id = commentCreator.getInt("id");
                    String creation_time = commentCreator.getString("creation_time");
                    String raw_text = commentCreator.getString("raw_text");

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
                    commentList.add(comment);

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "doInBackground: " + e.getMessage());

            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        adapter = new ContactAdapter(context, commentList);
        lv.setAdapter(adapter);
    }
}
