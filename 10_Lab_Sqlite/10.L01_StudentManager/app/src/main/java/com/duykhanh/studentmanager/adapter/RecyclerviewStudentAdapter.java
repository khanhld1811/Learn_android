package com.duykhanh.studentmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.studentmanager.Interface.RecyclerviewClickListener;
import com.duykhanh.studentmanager.Model.Student;
import com.duykhanh.studentmanager.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewStudentAdapter extends RecyclerView.Adapter<RecyclerviewStudentAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Student> studentList;
    private LayoutInflater inflater;

    private RecyclerviewClickListener mListener;

    private List<Student> studentListFull;

    public RecyclerviewStudentAdapter(Context context, List<Student> studentList,RecyclerviewClickListener mListener) {
        this.context = context;
        this.studentList = studentList;
        this.mListener = mListener;
        inflater = LayoutInflater.from(context);
        studentListFull = new ArrayList<>(studentList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_student,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickItemListener(viewHolder.getPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Student student = studentList.get(position);

        holder.txtNameStudent.setText(student.getFullName());
        holder.txtCodeStudent.setText(student.getCodeSV());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickDeleteItemListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public Filter getFilter() {
        return studentFilter;
    }
    private Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
           List<Student> filterList = new ArrayList<>();

           if(charSequence == null || charSequence.length() == 0){
               filterList.addAll(studentListFull);
           }
           else{
               String filterPattern = charSequence.toString().toLowerCase().trim();

               for(Student item : studentListFull){
                   if(item.getCodeSV().toLowerCase().contains(filterPattern)){
                       filterList.add(item);
                   }
                   if(item.getFullName().toLowerCase().contains(filterPattern)){
                       filterList.add(item);
                   }
               }
           }
           FilterResults results = new FilterResults();
           results.values = filterList;
           return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
                studentList.clear();
                studentList.addAll( (List) results.values);
                notifyDataSetChanged();
        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCodeStudent, txtNameStudent;
        private ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCodeStudent = itemView.findViewById(R.id.txt_code_student);
            txtNameStudent = itemView.findViewById(R.id.txt_name_student);
            imgDelete      = itemView.findViewById(R.id.img_delete_student);
        }
    }
}
