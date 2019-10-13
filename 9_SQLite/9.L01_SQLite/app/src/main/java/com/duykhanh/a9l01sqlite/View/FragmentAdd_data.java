package com.duykhanh.a9l01sqlite.View;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duykhanh.a9l01sqlite.R;
import com.duykhanh.a9l01sqlite.dao.DAO_DB_student;
import com.duykhanh.a9l01sqlite.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdd_data extends Fragment implements View.OnClickListener{

    View view;
    private DAO_DB_student daoDbStudent;
    private Student student;

    private EditText edName, edAddress, edPhoneNumber;
    private Button btnAdd;

    String name         = "";
    String address      = "";
    String phoneNumber  = "";
    List<Student> listStudent ;

    public static FragmentAdd_data newInstance() {
        FragmentAdd_data fragmentAdd_data = new FragmentAdd_data();
        return fragmentAdd_data;
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("AAAA","run");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_add_data, container, false);

        initializationFirst();

        initView();
        setOnclickView();

        return view;
    }

    // Khởi tạo dối tượng và các thành phần cần thiết khác
    private void  initializationFirst(){
        daoDbStudent    = new DAO_DB_student(getContext());
        student         = new Student();
        listStudent     = new ArrayList<>();
    }

    // Ánh xạ view
    private void initView() {
        edName          = view.findViewById(R.id.ed_name);
        edAddress       = view.findViewById(R.id.ed_address);
        edPhoneNumber   = view.findViewById(R.id.ed_phone_number);

        btnAdd          = view.findViewById(R.id.btnAdd);
    }

    // Lấy dữ liệu từ view
    private void getDataView(){
        name = edName.getText().toString();
        address = edAddress.getText().toString();
        phoneNumber = edPhoneNumber.getText().toString();
        listStudent = daoDbStudent.getAllStudent();
    }

    // Bắt sự kiện click view
    private void setOnclickView(){
        btnAdd.setOnClickListener(this);
    }

    // Thêm sinh vien vào CSDL
    private void AddStudent() {
        getDataView();

        student.setName(name);
        student.setAddress(address);
        student.setPhone_number(phoneNumber);

        daoDbStudent.addStudent(student);
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.btnAdd:
               AddStudent();
               break;
       }
    }


}
