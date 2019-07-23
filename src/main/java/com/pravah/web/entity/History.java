package com.pravah.web.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class History {
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private String remark;
    private String userId;


    public History(Date date, String remark, String userId) {
        this.date = date;
        this.remark = remark;
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
