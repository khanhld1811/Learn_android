package com.duykhanh.employeemanager.view.main;

import com.duykhanh.employeemanager.model.Employee;

import java.util.List;

/**
 * Created by Duy Khánh on 11/14/2019.
 */
public interface MainViewListener {
    void setDataToRecyclerView(List<Employee> employeeList);

    void doFaild();

    void doFaildThrowable(Throwable t);

    void showProgress();

    void hideProgress();


}
