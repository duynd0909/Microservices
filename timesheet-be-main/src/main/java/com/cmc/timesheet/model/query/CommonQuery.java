package com.cmc.timesheet.model.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonQuery {

    private Date fromDate;

    private Date toDate;

    private Integer projectId;

    private Integer employeeId;

}
