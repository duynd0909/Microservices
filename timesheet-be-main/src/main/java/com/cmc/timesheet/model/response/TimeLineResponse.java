package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLineResponse {

    private Integer employeeId;

    private String employeeName;

    private String du;

    private String email;

    private String ldap;

    private String project;

    private Integer projectId;

    private List<WorkdaysResponse> workdaysResponseList;

    private String notes;

    private Date createdAt;

    private List<String> noteLeaveUnpaid;
    private List<String> noteOTTime;
    private List<String> noteLeaveAnnual;
}
