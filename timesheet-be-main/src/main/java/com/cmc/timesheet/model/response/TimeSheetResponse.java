package com.cmc.timesheet.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetResponse {

    private Integer employeeId;

    private String fullName;

    private String knoxID;

    private String projectName;

    private String wfhDateList;

    private String otDateList;

    private String compensatoryDateList;

    private String abnormalLeaveList;

    private  String dayOffList;

    private String notes;
}
