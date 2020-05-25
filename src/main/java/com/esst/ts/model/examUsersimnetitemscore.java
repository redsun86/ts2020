package com.esst.ts.model;

import java.util.Date;

public class examUsersimnetitemscore {
    private Integer usersimnetitemscoreid;

    private Integer usisUserdetailscoreid;

    private String usisExamproj;

    private Double usisQweight;

    private Double usisExamscore;

    private Date usisStarttime;

    private Date usisEndtime;

    private String usisScorereport;

    private String usisRemark;

    private Integer usisCourseid;

    public Integer getUsersimnetitemscoreid() {
        return usersimnetitemscoreid;
    }

    public void setUsersimnetitemscoreid(Integer usersimnetitemscoreid) {
        this.usersimnetitemscoreid = usersimnetitemscoreid;
    }

    public Integer getUsisUserdetailscoreid() {
        return usisUserdetailscoreid;
    }

    public void setUsisUserdetailscoreid(Integer usisUserdetailscoreid) {
        this.usisUserdetailscoreid = usisUserdetailscoreid;
    }

    public String getUsisExamproj() {
        return usisExamproj;
    }

    public void setUsisExamproj(String usisExamproj) {
        this.usisExamproj = usisExamproj == null ? null : usisExamproj.trim();
    }

    public Double getUsisQweight() {
        return usisQweight;
    }

    public void setUsisQweight(Double usisQweight) {
        this.usisQweight = usisQweight;
    }

    public Double getUsisExamscore() {
        return usisExamscore;
    }

    public void setUsisExamscore(Double usisExamscore) {
        this.usisExamscore = usisExamscore;
    }

    public Date getUsisStarttime() {
        return usisStarttime;
    }

    public void setUsisStarttime(Date usisStarttime) {
        this.usisStarttime = usisStarttime;
    }

    public Date getUsisEndtime() {
        return usisEndtime;
    }

    public void setUsisEndtime(Date usisEndtime) {
        this.usisEndtime = usisEndtime;
    }

    public String getUsisScorereport() {
        return usisScorereport;
    }

    public void setUsisScorereport(String usisScorereport) {
        this.usisScorereport = usisScorereport == null ? null : usisScorereport.trim();
    }

    public String getUsisRemark() {
        return usisRemark;
    }

    public void setUsisRemark(String usisRemark) {
        this.usisRemark = usisRemark == null ? null : usisRemark.trim();
    }

    public Integer getUsisCourseid() {
        return usisCourseid;
    }

    public void setUsisCourseid(Integer usisCourseid) {
        this.usisCourseid = usisCourseid;
    }
}