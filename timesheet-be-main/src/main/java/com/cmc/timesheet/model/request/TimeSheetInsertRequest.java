package com.cmc.timesheet.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeSheetInsertRequest {
    private Date workingDate;
    private String fullName;
    private Integer userId;
    private String du;
    private String ldap;
    private String project;
    private String timeIn;
    private String timeOut;
    private String exception;
    private String regularHours;
    private String overTimeHours;
    private String totalWorkHours;
    private String knoxId;

}
