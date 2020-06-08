package com.esst.ts.model;

public class Trouble {
    private Integer id;

    private String troubleName;

    private Integer technology;

    private byte[] troubleCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTroubleName() {
        return troubleName;
    }

    public void setTroubleName(String troubleName) {
        this.troubleName = troubleName == null ? null : troubleName.trim();
    }

    public Integer getTechnology() {
        return technology;
    }

    public void setTechnology(Integer technology) {
        this.technology = technology;
    }

    public byte[] getTroubleCode() {
        return troubleCode;
    }

    public void setTroubleCode(byte[] troubleCode) {
        this.troubleCode = troubleCode;
    }
}