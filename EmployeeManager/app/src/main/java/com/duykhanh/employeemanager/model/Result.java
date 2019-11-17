package com.duykhanh.employeemanager.model;

/**
 * Created by Duy Khánh on 11/15/2019.
 */
public class Result {
    private int result_code;
    private String message;
    private String id;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
