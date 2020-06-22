package com.esst.ts.model;

import java.util.List;

public class UserLoginLogLists {
    private String date;
    private List<UserLoginLogListitem> listsitem;

    public List<UserLoginLogListitem> getListsitem() {
        return listsitem;
    }
    public void setListsitem(List<UserLoginLogListitem> listsitem) {
        this.listsitem = listsitem;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
