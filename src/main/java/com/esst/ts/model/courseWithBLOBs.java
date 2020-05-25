package com.esst.ts.model;

public class courseWithBLOBs extends course {
    private String cDescription;

    private String cTags;

    private String cSimnetxml;

    private String cSimnetprojects;

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription == null ? null : cDescription.trim();
    }

    public String getcTags() {
        return cTags;
    }

    public void setcTags(String cTags) {
        this.cTags = cTags == null ? null : cTags.trim();
    }

    public String getcSimnetxml() {
        return cSimnetxml;
    }

    public void setcSimnetxml(String cSimnetxml) {
        this.cSimnetxml = cSimnetxml == null ? null : cSimnetxml.trim();
    }

    public String getcSimnetprojects() {
        return cSimnetprojects;
    }

    public void setcSimnetprojects(String cSimnetprojects) {
        this.cSimnetprojects = cSimnetprojects == null ? null : cSimnetprojects.trim();
    }
}