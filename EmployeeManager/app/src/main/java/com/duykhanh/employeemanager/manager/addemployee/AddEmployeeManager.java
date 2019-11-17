package com.duykhanh.employeemanager.manager.addemployee;

import android.util.Log;

import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.model.Result;
import com.duykhanh.employeemanager.network.ApiUtils;
import com.duykhanh.employeemanager.presenter.addemployee.AddEmployeePresenterListener;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.duykhanh.employeemanager.utils.Constants.*;

/**
 * Created by Duy Khánh on 11/15/2019.
 */
public class AddEmployeeManager implements AddEmployeeManagerListener {

    //TODO Tạo mới nhân viên
    @Override
    public void sendDataCreateEmployee(final AddEmployeePresenterListener presenterListener, Employee employeeData) {
        Call<Result> call = ApiUtils.getListEmployee().createEmployee(employeeData);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result employee = response.body();
                if(employee.getResult_code() == 200){
                    presenterListener.doSuccess(REQUEST_DATA_FORM_SERVER);
                }
                else{
                    presenterListener.doFaild(REQUEST_DATA_FORM_SERVER);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                    presenterListener.doFaildThrowable(t);
                Log.e(TAG, "onFailure: ", t );
            }
        });
    }

    @Override
    public void sendFileEmployee(final AddEmployeePresenterListener presenter, MultipartBody.Part body, String codeEmployee) {
        Call<Result> call = ApiUtils.getListEmployee().uploadImage(body,codeEmployee);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if(response.isSuccessful()){
                    presenter.doSuccess(REQUEST_IMAGE_FORM_SERVER);
                }
                else{
                    presenter.doFaild(REQUEST_IMAGE_FORM_SERVER);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                presenter.doFaildThrowable(t);
            }
        });

    }
}
