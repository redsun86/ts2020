package com.esst.ts.model;

public class TimeScale {
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
        this.timescaleCode = timescaleCode == null ? null : timescaleCode.trim();
    }

    public String getTimescaleName() {
        return timescaleName;
    }

    public void setTimescaleName(String timescaleName) {
        this.timescaleName = timescaleName == null ? null : timescaleName.trim();
    }
}