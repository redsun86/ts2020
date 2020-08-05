package com.esst.ts.model;

/**
 * 创建标识：梁建磊 2020/6/19 9:49
 */
public class StatisticalChartAvgPOJO {
    /**
     * 平均时长（min：分钟）
     */
    private String avgDuration;
    /**
     * 总时长（min：分钟）
     */
    private String totalDuration;
    /**
     * 平均分
     */
    private String avgScore;
    /**
     * 最高分
     */
    private String totalScore;
    /**
     * 满分
     */
    private String sumScore;

    public String getAvgDuration() {
        return avgDuration;
    }

    public void setAvgDuration(String avgDuration) {
        this.avgDuration = avgDuration;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getSumScore() {
        return sumScore;
    }

    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }
}
