package com.esst.ts.model;

/**
 * 试卷实体
 * 创建标识：梁建磊 2020-06-12
 */
public class ExamPOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT COMMENT '递增主键',
    private String examName;//` varchar(32) DEFAULT NULL COMMENT '试卷名称',
    private Integer status;//` int(1) DEFAULT NULL COMMENT '试卷状态（发布/未发布）',
    private String statusName;//` int(1) DEFAULT NULL COMMENT '试卷状态（发布/未发布）',
    private Integer createUser;//` int(11) NOT NULL COMMENT '试卷创建者的userid',
    private Integer userCount;//学员人数
    private Integer examAttribute;
    private Integer questionsCount;//试题数量
    private String editInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getExamAttribute() {
        return examAttribute;
    }

    public void setExamAttribute(Integer examAttribute) {
        this.examAttribute = examAttribute;
    }

    public Integer getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(Integer questionsCount) {
        this.questionsCount = questionsCount;
    }

    public String getEditInfo() {
        return editInfo;
    }

    public void setEditInfo(String editInfo) {
        this.editInfo = editInfo;
    }
}
