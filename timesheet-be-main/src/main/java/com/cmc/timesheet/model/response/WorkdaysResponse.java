package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkdaysResponse {

    private String timeSheetId;

    private Date workingDate;

    private Integer workingType;

    private Time timeIn;

    private Time timeOut;

    private Integer employeeId;

    private Time regularHours;

    private Time overtimeHours;

    private Time otFrom;

    private Time otTo;

    private Integer missingMinutes;

    private String notes;

    private String attachments[];

    private String workingTypeCode;

    private String timeLineStatus;

    private String strDateKey;

}
