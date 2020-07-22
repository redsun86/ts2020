package com.esst.ts.model;

/**
 * 请求数据的基类
 * 创建标识：梁建磊 2020/7/20 17:04
 */
public class baseRequestPOJO {
    /**
     * token
     */
    private String token;
    /**
     * 请求者用户id
     */
    private Integer userId;
    /**
     * 请求数据的主键/唯一标识。多条数据逗号分隔
     */
    private String reqId;
    /**
     * 请求数据的关键字，如姓名等字符串
     */
    private String searchKey;
    /**
     * 请求数据的开始时间
     */
    private String startTime;
    /**
     * 请求数据的结束时间
     */
    private String stopTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
}
