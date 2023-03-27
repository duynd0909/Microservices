package com.cmc.timesheet.model.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSheetViewDetailRequest {

    @NotNull
    private Integer employeeId;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private Integer page;
    private Integer pageSize;
}
