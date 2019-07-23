package com.pravah.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Contribution {
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private String role;
    private String description;

    public Contribution(){}
    public Contribution( Date date, String role, String description) {

        this.date = date;
        this.role = role;
        this.description = description;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
