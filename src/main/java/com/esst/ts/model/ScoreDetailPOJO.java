package com.esst.ts.model;

public class ScoreDetailPOJO {
    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    private String studyDate;
    private String taskName;
    private String learnTime;
    private String score;

    public String getScoreDetail() {
        return scoreDetail;
    }

    public void setScoreDetail(String scoreDetail) {
        this.scoreDetail = scoreDetail;
    }

    private String scoreDetail;

    public int getOperateId() {
        return operateId;
    }

    public void setOperateId(int operateId) {
        this.operateId = operateId;
    }

    private int operateId;
}
