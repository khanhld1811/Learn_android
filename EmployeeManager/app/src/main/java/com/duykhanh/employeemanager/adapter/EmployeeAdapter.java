package com.duykhanh.employeemanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duykhanh.employeemanager.R;
import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.view.main.MainActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.duykhanh.employeemanager.utils.Constants.*;

/**
 * Created by Duy Khánh on 11/14/2019.
 */
public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder>{

    private static final String TAG = EmployeeAdapter.class.getSimpleName();
    private MainActivity activity;
    private List<Employee> employeeList;

    public EmployeeAdapter(MainActivity activity, List<Employee> employeeList) {
        this.activity = activity;
        this.employeeList = employeeList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_employee,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        Log.d("c", "EmployeeAdapter: " + employeeList.get(0).getFullName());
        String[] arrayPathImage = employee.getImage().split("\\/");
        String imgUrl = BASE_URL + arrayPathImage[3] + "/" + arrayPathImage[4];
        Log.d(TAG, "onBindViewHolder: " + imgUrl);
        holder.txtName.setText(employee.getFullName());
        holder.txtPosition.setText(employee.getPosition());

        Glide.with(activity)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.noneimg)
                .into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + employeeList.size());
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgUser;
        TextView txtName,
                 txtPosition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser     = itemView.findViewById(R.id.imgUser);
            txtName     = itemView.findViewById(R.id.txtName);
            txtPosition = itemView.findViewById(R.id.txtPosition);
        }
    }
}
