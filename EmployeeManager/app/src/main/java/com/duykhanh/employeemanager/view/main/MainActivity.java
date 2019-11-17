package com.duykhanh.employeemanager.view.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.employeemanager.R;
import com.duykhanh.employeemanager.adapter.EmployeeAdapter;
import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.presenter.main.EmployeePresenter;
import com.duykhanh.employeemanager.view.addEmployee.AddEmployeeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewListener,ShowEmptyView{

    private static final String TAG = MainActivity.class.getSimpleName();

    EmployeePresenter presenter;
    private List<Employee> listEmployee;
    private RecyclerView rv_employee_list;
    private EmployeeAdapter adapter;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    TextView txtName, txtEmptyView;

    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Employee manager");

        init();

        setListener();

        presenter = new EmployeePresenter(this);

        presenter.requestDataFormServer();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        rv_employee_list = findViewById(R.id.rv_employee_list);

        listEmployee = new ArrayList<>();
        adapter = new EmployeeAdapter(this,listEmployee);
        mlayoutManager = new LinearLayoutManager(this);

        rv_employee_list.setLayoutManager(mlayoutManager);
        rv_employee_list.setItemAnimator(new DefaultItemAnimator());
        rv_employee_list.setAdapter(adapter);

        progressBar = findViewById(R.id.pb_loading);
        txtName = findViewById(R.id.txtName);
        fab = findViewById(R.id.fab_filter);
        txtEmptyView = findViewById(R.id.txt_empty_view);
    }

    private void setListener(){
        rv_employee_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0 && fab.getVisibility() == View.VISIBLE){
                    fab.hide();
                }
                else if(dy < 0 && fab.getVisibility() != View.VISIBLE){
                    fab.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent employeeAddIntent = new Intent(MainActivity.this, AddEmployeeActivity.class);
                startActivity(employeeAddIntent);
            }
        });
    }

    @Override
    public void setDataToRecyclerView(List<Employee> employeeList) {
        listEmployee.addAll(employeeList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void doFaild() {
        Toast.makeText(this, "Error in getting data. Please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doFaildThrowable(Throwable t) {
        Toast.makeText(this, "Error network. Please try again later: " + t, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        rv_employee_list.setVisibility(View.GONE);
        txtEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        rv_employee_list.setVisibility(View.VISIBLE);
        txtEmptyView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
