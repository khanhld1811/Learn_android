package com.duykhanh.listviewandspinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.duykhanh.listviewandspinner.R;
import com.duykhanh.listviewandspinner.model.Contact;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private List<Contact> arrContact;

    public ListViewAdapter(Context context, int resource, ArrayList<Contact> arrContact) {
        super(context, resource, arrContact);
        this.context = context;
        this.resource = resource;
        this.arrContact = arrContact;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = view.findViewById(R.id.tvName);
            viewHolder.tvNumberPhone = view.findViewById(R.id.tvPhoneNumber);
            viewHolder.tvAvatar = view.findViewById(R.id.tvAvatar);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Contact contact = arrContact.get(position);
        viewHolder.tvAvatar.setBackgroundColor(contact.getColor());
        viewHolder.tvAvatar.setText(String.valueOf(position + 1));
        viewHolder.tvName.setText(contact.getName());
        viewHolder.tvNumberPhone.setText(contact.getPhoneNumber());
        return view;

    }

    public class ViewHolder {
        TextView tvName, tvNumberPhone, tvAvatar;
    }
}
