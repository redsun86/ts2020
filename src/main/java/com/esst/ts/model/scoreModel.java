package com.esst.ts.model;

public class scoreModel {
    private String id;//:"1",
    private String machine_id;//":"PC01",
    private String user_name;//":"张三",
    private String student_num;//":"1100",
    private String template_id;//":"任务单或试卷id",
    private String template_name;//":"任务单或试卷名",
    private String task_id;//":"任务或试题id",
    private String task_name;//":"任务或试题名",
    private String score;//":10.3,
    private String total_score;//":60.1,
    private String learning_time;//":25.6,
    private String status;//":1,
    private String detailesscore;//":"带排版的详细成绩",
    private String report_url;//":"www.esonline.com/report.pdf"

    public String getTrain_id() {
        return train_id;
    }

    public void setTrain_id(String train_id) {
        this.train_id = train_id;
    }

    private String train_id;

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    private String teacher_id;//老师ID

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    public int getStudy_type() {
        return study_type;
    }

    public void setStudy_type(int study_type) {
        this.study_type = study_type;
    }

    public scoreModel() {
        super();
    }

    private int study_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(String machine_id) {
        this.machine_id = machine_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStudent_num() {
        return student_num;
    }

    public void setStudent_num(String student_num) {
        this.student_num = student_num;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public String getLearning_time() {
        return learning_time;
    }

    public void setLearning_time(String learning_time) {
        this.learning_time = learning_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetailesscore() {
        return detailesscore;
    }

    public void setDetailesscore(String detailesscore) {
        this.detailesscore = detailesscore;
    }

    public String getReport_url() {
        return report_url;
    }

    public void setReport_url(String report_url) {
        this.report_url = report_url;
    }

}
