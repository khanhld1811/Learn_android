package com.duykhanh.employeemanager.view.addEmployee;

/**
 * Created by Duy Khánh on 11/15/2019.
 */
public interface AddEmployeeViewListener {

    void doSuccess(int request);

    void doFaild(int request);

    void doFaildThrowable(Throwable t);
}
