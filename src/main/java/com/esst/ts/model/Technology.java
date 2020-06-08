package com.esst.ts.model;

public class Technology {
    private Integer id;

    private String technologyCode;

    private String technologyZhName;

    private String technologyEnName;

    private String styleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTechnologyCode() {
        return technologyCode;
    }

    public void setTechnologyCode(String technologyCode) {
        this.technologyCode = technologyCode == null ? null : technologyCode.trim();
    }

    public String getTechnologyZhName() {
        return technologyZhName;
    }

    public void setTechnologyZhName(String technologyZhName) {
        this.technologyZhName = technologyZhName == null ? null : technologyZhName.trim();
    }

    public String getTechnologyEnName() {
        return technologyEnName;
    }

    public void setTechnologyEnName(String technologyEnName) {
        this.technologyEnName = technologyEnName == null ? null : technologyEnName.trim();
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId == null ? null : styleId.trim();
    }
}