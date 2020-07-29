package com.esst.ts.model;

/**
 * 学员成绩记录的基础数据检索条件实体
 * 创建标识：梁建磊 2020/7/20 17:05
 */
public class baseDataRequest extends baseRequestPOJO {

    /**
     * 所属老师用户id
     */
    private Integer teacherId;
    /**
     * 数据类型。0：任务单；1：试卷
     */
    private  String studyType;
    /**
     * 是否历史数据。0：否；1：是；
     */
    private Integer isHistory;

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }
}
