package com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duykhanh.a13l01_jsondemo.R;
import com.duykhanh.a13l01_jsondemo.view.ex03_GetContact.model.Comment;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    Context context;
    ArrayList<Comment> commentList;

    public ContactAdapter(Context context, ArrayList<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder = new ViewHolder();
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item,null);
            viewHolder.tv_id = view.findViewById(R.id.tv_id);
            viewHolder.tv_creator = view.findViewById(R.id.tv_creator);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Comment comment = commentList.get(i);
        viewHolder.tv_id.setText("" + comment.getId());
        viewHolder.tv_creator.setText(comment.getCreator());
        viewHolder.tv_time.setText(comment.getTime());
        return view;
    }

    public  class ViewHolder{
        TextView tv_id, tv_creator, tv_time;

    }
}
