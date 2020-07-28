package com.esst.ts.model;

public class DetailScore {
    private Integer id;

    private String trainId;

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

    public String getDetailScore() {
        return detailScore;
    }

    public void setDetailScore(String detailScore) {
        this.detailScore = detailScore == null ? null : detailScore.trim();
    }
}