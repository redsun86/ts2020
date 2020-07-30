package com.esst.ts.model;

public class DetailScore {
    private Integer id;

    private String trainId;

    private Long updatetime;

    private Integer studentId;

    private String detailScore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId == null ? null : trainId.trim();
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDetailScore() {
        return detailScore;
    }

    public void setDetailScore(String detailScore) {
        this.detailScore = detailScore == null ? null : detailScore.trim();
    }
}