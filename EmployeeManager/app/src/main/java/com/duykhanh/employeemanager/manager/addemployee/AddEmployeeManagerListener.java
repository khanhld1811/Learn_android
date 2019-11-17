package com.duykhanh.employeemanager.manager.addemployee;

import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.presenter.addemployee.AddEmployeePresenterListener;

import okhttp3.MultipartBody;

/**
 * Created by Duy Khánh on 11/15/2019.
 */
public interface AddEmployeeManagerListener {
    void sendDataCreateEmployee(AddEmployeePresenterListener presenter, Employee employeeData);

    void sendFileEmployee(AddEmployeePresenterListener presenter, MultipartBody.Part body, String codeEmployee);
}
