package com.cmc.timesheet.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineRequestCreateNote {

    @NotNull
    private String id;

    private Date workingDate;

    private Integer workingType;

    private Time timeIn;

    private Time timeOut;
    @NotNull
    private Integer employeeId;

    private Time otFrom;

    private Time otTo;

    private Time regularHours;

    private Time overTimeHours;

    private Integer missingMinutes;

    private Integer lateComingMinutes;

    private Integer earlyLeavingMinutes;

    private String notes;

    private String[] attachments;

    @NotNull
    private Integer projectId;

    private Date createdAt;



}
