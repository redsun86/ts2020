package com.esst.ts.model;

public class paperWithBLOBs extends paper {
    private String pSingle;

    private String pMultiple;

    private String pJudge;

    private String pFillblank;

    private String pEssay;

    private String pCaculate;

    private String pSimnetxml;

    public String getpSingle() {
        return pSingle;
    }

    public void setpSingle(String pSingle) {
        this.pSingle = pSingle == null ? null : pSingle.trim();
    }

    public String getpMultiple() {
        return pMultiple;
    }

    public void setpMultiple(String pMultiple) {
        this.pMultiple = pMultiple == null ? null : pMultiple.trim();
    }

    public String getpJudge() {
        return pJudge;
    }

    public void setpJudge(String pJudge) {
        this.pJudge = pJudge == null ? null : pJudge.trim();
    }

    public String getpFillblank() {
        return pFillblank;
    }

    public void setpFillblank(String pFillblank) {
        this.pFillblank = pFillblank == null ? null : pFillblank.trim();
    }

    public String getpEssay() {
        return pEssay;
    }

    public void setpEssay(String pEssay) {
        this.pEssay = pEssay == null ? null : pEssay.trim();
    }

    public String getpCaculate() {
        return pCaculate;
    }

    public void setpCaculate(String pCaculate) {
        this.pCaculate = pCaculate == null ? null : pCaculate.trim();
    }

    public String getpSimnetxml() {
        return pSimnetxml;
    }

    public void setpSimnetxml(String pSimnetxml) {
        this.pSimnetxml = pSimnetxml == null ? null : pSimnetxml.trim();
    }
}