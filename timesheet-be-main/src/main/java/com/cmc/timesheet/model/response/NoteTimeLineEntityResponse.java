package com.cmc.timesheet.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteTimeLineEntityResponse {
    private String timeSheetId;

    private Date workingDate;

    private Integer workingType;

    private Time timeIn;

    private Time timeOut;

    private Integer employeeId;

    private Time otFrom;

    private Time otTo;

    private Time regularHours;

    private Time overtimeHours;

    private Integer missingMinutes;

    private Integer lateComingMinutes;

    private Integer earlyLeavingMinutes;

    private String notes;

    private String employeeName;

    private String ldap;

    private String email;

    private String du;

    private Integer projectId;

    private String project;

    private String[] attachments;
    private Date createdAt;

}
