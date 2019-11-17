package com.duykhanh.employeemanager.presenter.addemployee;

import com.duykhanh.employeemanager.manager.addemployee.AddEmployeeManager;
import com.duykhanh.employeemanager.manager.addemployee.AddEmployeeManagerListener;
import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.view.addEmployee.AddEmployeeViewListener;

import okhttp3.MultipartBody;

/**
 * Created by Duy Khánh on 11/15/2019.
 */
public class AddEmployeePresenter implements AddEmployeePresenterListener {

    private AddEmployeeManagerListener manager;
    private AddEmployeeViewListener view;

    public AddEmployeePresenter(AddEmployeeViewListener view) {
        this.manager = new AddEmployeeManager();
        this.view = view;
    }

    @Override
    public void requestDataFormServer(Employee employeeData) {
        manager.sendDataCreateEmployee(this,employeeData);
    }

    @Override
    public void requestFileFormServer(MultipartBody.Part body, String employeeCode) {
        manager.sendFileEmployee(this,body,employeeCode);
    }

    @Override
    public void doSuccess(int request) {
        view.doSuccess(request);
    }

    @Override
    public void doFaild(int request) {
        view.doFaild(request);
    }

    @Override
    public void doFaildThrowable(Throwable t) {
        view.doFaildThrowable(t);
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
