package com.cmc.timesheet.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTineLineRequest {
    private Integer projectId;

    private Date startDate;
    private Date endDate;

    private String employeeName;

    private Integer page;

    private Integer pageSize;
}
