package com.duykhanh.employeemanager.network;

import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.model.Result;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Duy Khánh on 11/14/2019.
 */
public interface DataApi {

    @GET("api/employee")
    Call<List<Employee>> getDataEmployee();

    @Multipart
    @PUT("api/employee/{eplCode}")
    Call<Result> uploadImage(@Part MultipartBody.Part photo, @Path("eplCode") String codeEmployee);

    @POST("api/employee")
    Call<Result> createEmployee(@Body Employee employee);
}
