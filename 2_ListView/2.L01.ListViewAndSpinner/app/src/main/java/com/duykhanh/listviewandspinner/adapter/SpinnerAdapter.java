package com.duykhanh.listviewandspinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.model.Contact;
import com.duykhanh.listviewandspinner.model.Country;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Country> {


    public SpinnerAdapter(@NonNull Context context, @NonNull List<Country> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_spinner, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        Country countryItem = getItem(position);

        if (countryItem != null) {
            imageViewFlag.setImageResource(countryItem.getFlagImage());
            textViewName.setText(countryItem.getCountryName());
        }

        return convertView;
    }
}
