package com.esst.ts.model;

public class courseDirectory {
    private Integer coursedirectoryid;

    private String cdDname;

    private String cdDename;

    private String cdDpath;

    private String cdType;

    private Integer cdParentid;

    private Integer cdLeftid;

    private Integer cdRightid;

    private Integer cdIsroot;

    private Integer cdLevel;

    private Integer cdChildcount;

    private Integer cdShoworder;

    private Integer cdDelete;

    private Integer cdDocumentid;

    private String cdEntrancepath;

    public Integer getCoursedirectoryid() {
        return coursedirectoryid;
    }

    public void setCoursedirectoryid(Integer coursedirectoryid) {
        this.coursedirectoryid = coursedirectoryid;
    }

    public String getCdDname() {
        return cdDname;
    }

    public void setCdDname(String cdDname) {
        this.cdDname = cdDname == null ? null : cdDname.trim();
    }

    public String getCdDename() {
        return cdDename;
    }

    public void setCdDename(String cdDename) {
        this.cdDename = cdDename == null ? null : cdDename.trim();
    }

    public String getCdDpath() {
        return cdDpath;
    }

    public void setCdDpath(String cdDpath) {
        this.cdDpath = cdDpath == null ? null : cdDpath.trim();
    }

    public String getCdType() {
        return cdType;
    }

    public void setCdType(String cdType) {
        this.cdType = cdType == null ? null : cdType.trim();
    }

    public Integer getCdParentid() {
        return cdParentid;
    }

    public void setCdParentid(Integer cdParentid) {
        this.cdParentid = cdParentid;
    }

    public Integer getCdLeftid() {
        return cdLeftid;
    }

    public void setCdLeftid(Integer cdLeftid) {
        this.cdLeftid = cdLeftid;
    }

    public Integer getCdRightid() {
        return cdRightid;
    }

    public void setCdRightid(Integer cdRightid) {
        this.cdRightid = cdRightid;
    }

    public Integer getCdIsroot() {
        return cdIsroot;
    }

    public void setCdIsroot(Integer cdIsroot) {
        this.cdIsroot = cdIsroot;
    }

    public Integer getCdLevel() {
        return cdLevel;
    }

    public void setCdLevel(Integer cdLevel) {
        this.cdLevel = cdLevel;
    }

    public Integer getCdChildcount() {
        return cdChildcount;
    }

    public void setCdChildcount(Integer cdChildcount) {
        this.cdChildcount = cdChildcount;
    }

    public Integer getCdShoworder() {
        return cdShoworder;
    }

    public void setCdShoworder(Integer cdShoworder) {
        this.cdShoworder = cdShoworder;
    }

    public Integer getCdDelete() {
        return cdDelete;
    }

    public void setCdDelete(Integer cdDelete) {
        this.cdDelete = cdDelete;
    }

    public Integer getCdDocumentid() {
        return cdDocumentid;
    }

    public void setCdDocumentid(Integer cdDocumentid) {
        this.cdDocumentid = cdDocumentid;
    }

    public String getCdEntrancepath() {
        return cdEntrancepath;
    }

    public void setCdEntrancepath(String cdEntrancepath) {
        this.cdEntrancepath = cdEntrancepath == null ? null : cdEntrancepath.trim();
    }
}