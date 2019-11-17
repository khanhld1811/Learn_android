package com.duykhanh.employeemanager.presenter.main;

import com.duykhanh.employeemanager.model.Employee;

import java.util.List;

/**
 * Created by Duy Khánh on 11/14/2019.
 */
public interface EmployeePresenterListener {

    void requestDataFormServer();

    void sendDataView(List<Employee> employeeList);

    void doFaild();

    void doFaildThrowable(Throwable t);

    void onDestroy();
}
