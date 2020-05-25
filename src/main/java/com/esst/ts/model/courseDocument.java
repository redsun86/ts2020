package com.esst.ts.model;

public class courseDocument {
    private Integer coursedocumentid;

    private String cdName;

    private String cdEname;

    private String cdFilepath;

    private Integer cdFiletype;

    private Integer cdFilesize;

    private String cdExtensionname;

    private Integer cdIsconversion;

    private Integer cdOriginalfilesize;

    private String cdOriginalextensionname;

    private Integer cdFilesourcetype;

    private Integer cdFilestoragetype;

    private String cdMetaid;

    private String cdComment;

    public Integer getCoursedocumentid() {
        return coursedocumentid;
    }

    public void setCoursedocumentid(Integer coursedocumentid) {
        this.coursedocumentid = coursedocumentid;
    }

    public String getCdName() {
        return cdName;
    }

    public void setCdName(String cdName) {
        this.cdName = cdName == null ? null : cdName.trim();
    }

    public String getCdEname() {
        return cdEname;
    }

    public void setCdEname(String cdEname) {
        this.cdEname = cdEname == null ? null : cdEname.trim();
    }

    public String getCdFilepath() {
        return cdFilepath;
    }

    public void setCdFilepath(String cdFilepath) {
        this.cdFilepath = cdFilepath == null ? null : cdFilepath.trim();
    }

    public Integer getCdFiletype() {
        return cdFiletype;
    }

    public void setCdFiletype(Integer cdFiletype) {
        this.cdFiletype = cdFiletype;
    }

    public Integer getCdFilesize() {
        return cdFilesize;
    }

    public void setCdFilesize(Integer cdFilesize) {
        this.cdFilesize = cdFilesize;
    }

    public String getCdExtensionname() {
        return cdExtensionname;
    }

    public void setCdExtensionname(String cdExtensionname) {
        this.cdExtensionname = cdExtensionname == null ? null : cdExtensionname.trim();
    }

    public Integer getCdIsconversion() {
        return cdIsconversion;
    }

    public void setCdIsconversion(Integer cdIsconversion) {
        this.cdIsconversion = cdIsconversion;
    }

    public Integer getCdOriginalfilesize() {
        return cdOriginalfilesize;
    }

    public void setCdOriginalfilesize(Integer cdOriginalfilesize) {
        this.cdOriginalfilesize = cdOriginalfilesize;
    }

    public String getCdOriginalextensionname() {
        return cdOriginalextensionname;
    }

    public void setCdOriginalextensionname(String cdOriginalextensionname) {
        this.cdOriginalextensionname = cdOriginalextensionname == null ? null : cdOriginalextensionname.trim();
    }

    public Integer getCdFilesourcetype() {
        return cdFilesourcetype;
    }

    public void setCdFilesourcetype(Integer cdFilesourcetype) {
        this.cdFilesourcetype = cdFilesourcetype;
    }

    public Integer getCdFilestoragetype() {
        return cdFilestoragetype;
    }

    public void setCdFilestoragetype(Integer cdFilestoragetype) {
        this.cdFilestoragetype = cdFilestoragetype;
    }

    public String getCdMetaid() {
        return cdMetaid;
    }

    public void setCdMetaid(String cdMetaid) {
        this.cdMetaid = cdMetaid == null ? null : cdMetaid.trim();
    }

    public String getCdComment() {
        return cdComment;
    }

    public void setCdComment(String cdComment) {
        this.cdComment = cdComment == null ? null : cdComment.trim();
    }
}