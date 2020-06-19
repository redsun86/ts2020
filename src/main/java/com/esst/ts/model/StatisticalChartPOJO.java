package com.esst.ts.model;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/18 15:32
 */
public class StatisticalChartPOJO {
    private String describe;
    private String notes;
    private List<StatisticalChartDataPOJO> dataList;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<StatisticalChartDataPOJO> getDataList() {
        return dataList;
    }

    public void setDataList(List<StatisticalChartDataPOJO> dataList) {
        this.dataList = dataList;
    }
}
