package com.duykhanh.employeemanager.manager.main;

import android.util.Log;
import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.network.ApiUtils;
import com.duykhanh.employeemanager.presenter.main.EmployeePresenterListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Duy Khánh on 11/14/2019.
 */
public class EmployeeManager implements EmployeeListModelListener {

    private static final String TAG = EmployeeManager.class.getSimpleName();

    //TODO Lấy danh sách nhân viên
    @Override
    public void getEmployeeList(final EmployeePresenterListener presenter) {
        Call<List<Employee>> call = ApiUtils.getListEmployee().getDataEmployee();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(response.isSuccessful()) {
                    List<Employee> employeeList = response.body();
                    presenter.sendDataView(employeeList);
                }
                else{
                    presenter.doFaild();
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t );
                presenter.doFaildThrowable(t);
            }
        });
    }
}
