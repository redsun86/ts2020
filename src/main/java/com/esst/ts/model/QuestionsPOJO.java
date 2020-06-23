package com.esst.ts.model;

/**
 * 试题实体
 * 创建标识：梁建磊 2020-06-12
 */
public class QuestionsPOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private String QuestionName;//试题名称，默认为工况名称,
    private Integer exameId;//` int(11) NOT NULL COMMENT '所属试卷id',
    private String exameName;//` varchar(32) DEFAULT NULL COMMENT '试卷名称',
    private Integer technologyId;//`technology_id` int(11) DEFAULT NULL COMMENT '工况所属的工艺'
    private String technologyName;//` varchar(32) DEFAULT NULL COMMENT '工艺中文名',
    private Integer operateId;//` int(11) DEFAULT NULL COMMENT '工况id',
    private String operateName;//` varchar(32) DEFAULT NULL COMMENT '工况名'
    private Integer troubleId;//` int(11) DEFAULT NULL,
    private String troubleName;//` varchar(32) DEFAULT NULL COMMENT '事故名称',,
    private Integer styleId;//` int(11) DEFAULT NULL COMMENT '风格id',
    private String styleName;//` varchar(32) DEFAULT NULL COMMENT '风格名称',
    private Integer proportion;//` int(11) DEFAULT NULL COMMENT '得分所占比重',
    private Integer timeLimit;//` int(11) DEFAULT NULL COMMENT '答题时间限制',
    private Integer timeScale;//运行时标
    private Integer timeScaleName;//运行时标

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionName() {
        return QuestionName;
    }

    public void setQuestionName(String questionName) {
        QuestionName = questionName;
    }

    public Integer getExameId() {
        return exameId;
    }

    public void setExameId(Integer exameId) {
        this.exameId = exameId;
    }

    public String getExameName() {
        return exameName;
    }

    public void setExameName(String exameName) {
        this.exameName = exameName;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Integer getTroubleId() {
        return troubleId;
    }

    public void setTroubleId(Integer troubleId) {
        this.troubleId = troubleId;
    }

    public String getTroubleName() {
        return troubleName;
    }

    public void setTroubleName(String troubleName) {
        this.troubleName = troubleName;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(Integer timeScale) {
        this.timeScale = timeScale;
    }

    public Integer getTimeScaleName() {
        return timeScaleName;
    }

    public void setTimeScaleName(Integer timeScaleName) {
        this.timeScaleName = timeScaleName;
    }
}