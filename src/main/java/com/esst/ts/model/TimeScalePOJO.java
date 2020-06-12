package com.esst.ts.model;

/**
 * 运行时标实体
 * 创建标识：梁建磊 2020-06-12
 */
public class TimeScalePOJO {
    private Integer id;
    private String timescaleCode;
    private String timescaleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimescaleCode() {
        return timescaleCode;
    }

    public void setTimescaleCode(String timescaleCode) {
        this.timescaleCode = timescaleCode;
    }

    public String getTimescaleName() {
        return timescaleName;
    }

    public void setTimescaleName(String timescaleName) {
        this.timescaleName = timescaleName;
    }
}
