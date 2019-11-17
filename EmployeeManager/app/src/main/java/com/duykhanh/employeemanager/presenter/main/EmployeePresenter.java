package com.duykhanh.employeemanager.presenter.main;

import com.duykhanh.employeemanager.manager.main.EmployeeListModelListener;
import com.duykhanh.employeemanager.manager.main.EmployeeManager;
import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.view.main.MainViewListener;

import java.util.List;


/**
 * Created by Duy Khánh on 11/14/2019.
 */
public class EmployeePresenter implements EmployeePresenterListener{

    MainViewListener view;
    EmployeeListModelListener modelListener;

    public EmployeePresenter (MainViewListener view){
        this.view = view;
        modelListener = new EmployeeManager();
    }

    @Override
    public void requestDataFormServer() {
        if(view != null){
            view.showProgress();
        }
        modelListener.getEmployeeList(this);
    }

    @Override
    public void sendDataView(List<Employee> employeeList) {
        if(view != null){
            view.hideProgress();
        }
        view.setDataToRecyclerView(employeeList);
    }


    @Override
    public void doFaild() {
        if(view != null){
            view.hideProgress();
        }
        view.doFaild();
    }

    @Override
    public void doFaildThrowable(Throwable t) {
        if(view != null){
            view.hideProgress();
        }
        view.doFaildThrowable(t);
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }


}
