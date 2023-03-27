package com.cmc.timesheet.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeSheetViewDetailResponse  implements Serializable {
    private String timeSheetId;

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
