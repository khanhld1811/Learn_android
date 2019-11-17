package com.duykhanh.employeemanager.presenter.addemployee;

import com.duykhanh.employeemanager.model.Employee;

import okhttp3.MultipartBody;

/**
 * Created by Duy Khánh on 11/15/2019.
 */
public interface AddEmployeePresenterListener {

    void requestDataFormServer(Employee employeeData);

    void requestFileFormServer(MultipartBody.Part body, String codeEmployee);

    void doSuccess(int request);

    void doFaild(int request);

    void doFaildThrowable(Throwable t);

    void onDestroy();
}
