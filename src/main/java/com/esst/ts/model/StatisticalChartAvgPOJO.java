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
     * 平均分
     */
    private String avgScore;
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

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getSumScore() {
        return sumScore;
    }

    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }
}
