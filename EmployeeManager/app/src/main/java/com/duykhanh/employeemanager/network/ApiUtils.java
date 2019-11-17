package com.duykhanh.employeemanager.network;

/**
 * Created by Duy Khánh on 11/14/2019.
 */

import static com.duykhanh.employeemanager.utils.Constants.*;
public class ApiUtils {
    public static DataApi getListEmployee(){
        return RetrofitResponse.getClient(BASE_URL).create(DataApi.class);
    }

}
