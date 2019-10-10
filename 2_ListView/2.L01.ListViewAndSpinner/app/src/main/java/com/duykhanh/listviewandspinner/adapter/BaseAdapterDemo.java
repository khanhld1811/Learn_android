package com.duykhanh.listviewandspinner.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.model.Study;

import java.util.ArrayList;

public class BaseAdapterDemo extends BaseAdapter {

    private Context context;
     ArrayList<Study> studyList = new ArrayList<>();
    private LayoutInflater inflater;

    public BaseAdapterDemo(Context context, ArrayList<Study> studyList) {
        this.context = context;
        this.studyList = studyList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return studyList.size();
    }

    @Override
    public Object getItem(int i) {
        return studyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        TextView txtName,txtAge;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder ;
        if(view == null){
            view = inflater.inflate(R.layout.item_listview_base,viewGroup,false);
            holder = new ViewHolder();
            holder.txtName = view.findViewById(R.id.txt_name);
            holder.txtAge = view.findViewById(R.id.txt_age);

            view.setTag(holder);
        } else{
            holder = (ViewHolder) view.getTag();
        }

        Study study = (Study) getItem(i);
        holder.txtName.setText(study.getName());
        holder.txtAge.setText(study.getAge());
        return view;
    }
}
