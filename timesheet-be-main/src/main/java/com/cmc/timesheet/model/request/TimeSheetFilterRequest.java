package com.cmc.timesheet.model.request;

import com.cmc.timesheet.model.query.PagingQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetFilterRequest extends PagingQuery {
    private Date fromDate;
    private Date toDate;
    private List<Integer> projectIds;
    private String account;
}
