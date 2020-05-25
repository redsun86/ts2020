package com.esst.ts.model;

public class examUser {
    private Integer examuserid;

    private Integer euExamid;

    private Integer euType;

    private Integer euUserid;

    private Integer euDeptid;

    private Integer euPostid;

    private Integer euGradeid;

    private String euCustomerUserid;

    private Integer euCustomerDogid;

    private Integer euRelationexamid;

    public Integer getExamuserid() {
        return examuserid;
    }

    public void setExamuserid(Integer examuserid) {
        this.examuserid = examuserid;
    }

    public Integer getEuExamid() {
        return euExamid;
    }

    public void setEuExamid(Integer euExamid) {
        this.euExamid = euExamid;
    }

    public Integer getEuType() {
        return euType;
    }

    public void setEuType(Integer euType) {
        this.euType = euType;
    }

    public Integer getEuUserid() {
        return euUserid;
    }

    public void setEuUserid(Integer euUserid) {
        this.euUserid = euUserid;
    }

    public Integer getEuDeptid() {
        return euDeptid;
    }

    public void setEuDeptid(Integer euDeptid) {
        this.euDeptid = euDeptid;
    }

    public Integer getEuPostid() {
        return euPostid;
    }

    public void setEuPostid(Integer euPostid) {
        this.euPostid = euPostid;
    }

    public Integer getEuGradeid() {
        return euGradeid;
    }

    public void setEuGradeid(Integer euGradeid) {
        this.euGradeid = euGradeid;
    }

    public String getEuCustomerUserid() {
        return euCustomerUserid;
    }

    public void setEuCustomerUserid(String euCustomerUserid) {
        this.euCustomerUserid = euCustomerUserid == null ? null : euCustomerUserid.trim();
    }

    public Integer getEuCustomerDogid() {
        return euCustomerDogid;
    }

    public void setEuCustomerDogid(Integer euCustomerDogid) {
        this.euCustomerDogid = euCustomerDogid;
    }

    public Integer getEuRelationexamid() {
        return euRelationexamid;
    }

    public void setEuRelationexamid(Integer euRelationexamid) {
        this.euRelationexamid = euRelationexamid;
    }
}