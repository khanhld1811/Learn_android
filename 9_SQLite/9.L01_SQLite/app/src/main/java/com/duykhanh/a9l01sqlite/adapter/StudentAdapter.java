package com.duykhanh.a9l01sqlite.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.a9l01sqlite.Interface.RecyclerViewClickListener;
import com.duykhanh.a9l01sqlite.R;
import com.duykhanh.a9l01sqlite.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private List<Student> studentList;
    private int resource;
    private Context context;

    private RecyclerViewClickListener mListener;

    public StudentAdapter(List<Student> studentList, int resource, Context context,RecyclerViewClickListener mListener) {
        this.studentList = studentList;
        this.resource = resource;
        this.context = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, final int position) {
        Student student = studentList.get(position);
        Log.d("onBindViewHolder", "onBindViewHolder: "+student.getName());
        holder.txtName.setText(student.getName());
        holder.txtAddress.setText(student.getAddress());
        holder.txtPhoneNumber.setText(student.getPhone_number());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDelete(position);
            }
        });

    }

    /*
    * Số item tối da khi hiển thị
    */
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtAddress, txtPhoneNumber;
        private ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtAddress = itemView.findViewById(R.id.txt_address);
            txtPhoneNumber = itemView.findViewById(R.id.txt_phone);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
