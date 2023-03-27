package com.cmc.timesheet.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSheetMdRuleVo {
    private Integer lateMinute;

    private Integer earlyMinute;

    private Integer missingMinute;

    private Integer  workingType;
}
