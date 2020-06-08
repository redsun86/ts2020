package com.esst.ts.model;

public class UserLiveDataWithBLOBs extends UserLiveData {
    private String scoreDetail;

    private String report;

    public String getScoreDetail() {
        return scoreDetail;
    }

    public void setScoreDetail(String scoreDetail) {
        this.scoreDetail = scoreDetail == null ? null : scoreDetail.trim();
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }
}