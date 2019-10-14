package com.duykhanh.studentmanager.View.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.duykhanh.studentmanager.Interface.RecyclerviewClickListener;
import com.duykhanh.studentmanager.Model.Student;
import com.duykhanh.studentmanager.R;
import com.duykhanh.studentmanager.View.AddStudentActivity;
import com.duykhanh.studentmanager.View.DetailStudentActivity;
import com.duykhanh.studentmanager.adapter.RecyclerviewStudentAdapter;
import com.duykhanh.studentmanager.dao.DAO_student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStudent extends Fragment implements View.OnClickListener, RecyclerviewClickListener {

    View view;
    private FloatingActionButton fab;
    private RecyclerView rclStudent;

    private DAO_student daoStudent;
    private List<Student> studentList;
    private RecyclerviewStudentAdapter adapterStudent;

    public FragmentStudent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_student, container, false);


        initView();
        onClickView();
        initializationObject();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllStudent();
    }

    private void initializationObject(){
        daoStudent      = new DAO_student(getActivity());
        studentList     = new ArrayList<>();
    }

    /*
    * Ánh xạ view
    */
    private void initView() {
        fab         = view.findViewById(R.id.fab_add_student);
        rclStudent  = view.findViewById(R.id.rcl_student);
    }

    /*
    * Bắt sự kiện click cho view
    */
    private void onClickView() {
        fab.setOnClickListener(this);
    }

    /*
    * Lấy toàn bộ danh sách sinh viên và đưa vào reyclerview
    */
    private void getAllStudent(){
        studentList = daoStudent.getAllStudent();

        adapterStudent  = new RecyclerviewStudentAdapter(getActivity(),studentList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rclStudent.setLayoutManager(layoutManager);
        adapterStudent.notifyDataSetChanged();
        rclStudent.setAdapter(adapterStudent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_student:
                Intent iAddStudent = new Intent(getContext(), AddStudentActivity.class);
                startActivity(iAddStudent);
                break;
        }
    }

    @Override
    public void onClickItemListener(int studentId) {
        Intent intent = new Intent(getContext(), DetailStudentActivity.class);
        Student student = studentList.get(studentId);
        intent.putExtra("InfoStudent",student);
        startActivity(intent);
    }

    @Override
    public void onClickDeleteItemListener(int studentId) {
        daoStudent.deleteStudent(studentList.get(studentId).getIdSV());
        studentList.clear();
        studentList.addAll(daoStudent.getAllStudent());
        adapterStudent.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_student,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterStudent.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


}
