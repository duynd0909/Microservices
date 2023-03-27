package com.cmc.timesheet.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLineEntityResponse {

    private String timeSheetId;
    private Integer employeeId;
    private Date workingDate;

    private String attachments[];
    private Integer earlyLeavingMinutes;
    private Integer lateComingMinutes;

    private Integer missingMinutes;
    private String notes;
    private Time otFrom;
    private Time otTo;
    private Time overTimeHours;
    private Time regularHours;
    private Time timeIn;
    private Time timeOut;
    private Integer workingType;

    private String WorkingTypeCode;

}
