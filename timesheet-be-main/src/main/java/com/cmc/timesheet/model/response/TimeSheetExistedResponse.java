package com.cmc.timesheet.model.response;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeSheetExistedResponse {

    private Date workingDate;
    private Integer employeeId;
}
