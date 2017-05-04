package com.ly.milestone.model;

import java.io.Serializable;

/**
 * Created by lyc28724 on 2017/3/30.
 */
public class UserInfo implements Serializable {

    private String username;
    private String department;
    private String departmentId;
    private String userId;
    private String workId;
    private String gender;
    private String email;
    private String deptLevelId;
    private String deptLevelName;
    private String phoneNumber;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeptLevelId() {
        return deptLevelId;
    }

    public void setDeptLevelId(String deptLevelId) {
        this.deptLevelId = deptLevelId;
    }

    public String getDeptLevelName() {
        return deptLevelName;
    }

    public void setDeptLevelName(String deptLevelName) {
        this.deptLevelName = deptLevelName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
