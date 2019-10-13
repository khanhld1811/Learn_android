package com.duykhanh.a9l01sqlite.View;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.duykhanh.a9l01sqlite.Interface.RecyclerViewClickListener;
import com.duykhanh.a9l01sqlite.Interface.ViewMainClickListener;
import com.duykhanh.a9l01sqlite.MainActivity;
import com.duykhanh.a9l01sqlite.R;
import com.duykhanh.a9l01sqlite.adapter.StudentAdapter;
import com.duykhanh.a9l01sqlite.dao.DAO_DB_student;
import com.duykhanh.a9l01sqlite.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentShowData extends Fragment implements RecyclerViewClickListener {

    private static final String TAG = FragmentShowData.class.getSimpleName();
    View view;
    private Student student;
    public List<Student> studentList;
    public DAO_DB_student daoDbStudent;
    public StudentAdapter studentAdapter;

    public RecyclerView rcl_student;
    public RecyclerViewClickListener listener;

    public static FragmentShowData newInstance() {
        FragmentShowData fragmentShowData = new FragmentShowData();
        return fragmentShowData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_show_data, container, false);

        initView();
        initializationFirst();
        getAllStudent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    /*
     * TODO (1)Khởi tạo các đôi tượng cần thiết
     */
    private void initializationFirst() {
        student = new Student();
        studentList = new ArrayList<>();
        daoDbStudent = new DAO_DB_student(getContext());
        listener = this;

    }

    /*
     * TODO (2) Ánh xạ các view cần thiết
     */
    private void initView() {
        rcl_student = view.findViewById(R.id.rcl_student);
    }

    /*
     * TODO (3) Lấy toàn bộ dữ liệu student và show lên recyclerview
     */
    private void getAllStudent() {
        studentList = daoDbStudent.getAllStudent();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rcl_student.setLayoutManager(layoutManager);
        studentAdapter = new StudentAdapter(studentList, R.layout.item_student, getActivity(), listener);
        studentAdapter.notifyDataSetChanged();
        rcl_student.setAdapter(studentAdapter);
    }

    /*
    * TODO (4) Sự kiện khi người dùng click item student
    */
    @Override
    public void onClick(int position) {
        /*
        * Truyền bào hàm update vị trí của item
        */
        updateStudent(position);
        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
    }

    // TODO (5) Sự kiện xóa student khi người dùng click vào button delete
    @Override
    public void onDelete(int position) {
        Log.d(TAG, "onDelete: " + studentList.get(position).getId());
        /*
        * Truyền vào hàm deleteStudent id của học sinh và tiến cập nhập giao diện
        */
        daoDbStudent.deleteStudent(studentList.get(position).getId());
        studentAdapter.notifyDataSetChanged();
    }

    // TODO (6) Hàm update thông tin học sinh
    /*
    * Tạo một dialog khi người dùng click vào item student để cập nhận thông tin
    * cho student đó
    */
    private void updateStudent(final int position) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setTitle("Update student");

        final EditText edUpdateName, edUpdateAddress, edUpdatePhoneNumber;
        Button btnUpdateStudent;
        final String name, address, phoneNumber;

        edUpdateName = dialog.findViewById(R.id.ed_update_name);
        edUpdateAddress = dialog.findViewById(R.id.ed_update_address);
        edUpdatePhoneNumber = dialog.findViewById(R.id.ed_update_phone);
        btnUpdateStudent = dialog.findViewById(R.id.btn_updateStudent);

        final Student studenUpdate = new Student();

        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studenUpdate.setId(studentList.get(position).getId());
                studenUpdate.setName(edUpdateName.getText().toString());
                studenUpdate.setAddress(edUpdateAddress.getText().toString());
                studenUpdate.setPhone_number(edUpdatePhoneNumber.getText().toString());
                daoDbStudent.updateStudent(studenUpdate);
            }
        });
        dialog.show();

    }
}
