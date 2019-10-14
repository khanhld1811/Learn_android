package com.duykhanh.studentmanager.Model;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private int idSV;
    private String codeSV;
    private String fullName;
    private Date birthday;
    private String address;
    private String gender;
    private int positionGender;

    public Student() {
    }

    public Student(String codeSV, String fullName, Date birthday, String address, String gender, int positionGender) {
        this.codeSV = codeSV;
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.positionGender = positionGender;
    }

    public Student(int idSV, String codeSV, String fullName, Date birthday, String address, String gender, int positionGender) {
        this.idSV = idSV;
        this.codeSV = codeSV;
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.positionGender = positionGender;
    }

    public int getIdSV() {
        return idSV;
    }

    public void setIdSV(int idSV) {
        this.idSV = idSV;
    }

    public String getCodeSV() {
        return codeSV;
    }

    public void setCodeSV(String codeSV) {
        this.codeSV = codeSV;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPositionGender() {
        return positionGender;
    }

    public void setPositionGender(int positionGender) {
        this.positionGender = positionGender;
    }
}