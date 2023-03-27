package com.cmc.timesheet.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WorkingTypeEnum {
    NORMAL("Normal", 1),
    LATE_COMING("Late coming",2),
    EARLY_LEAVING("Early Leaving",3),
    ABSENT("Absent",4),
    LATE_COMING_EARLY_LEAVING("Late coming, Early Leaving",5);
    private String displayText;
    private Integer code;
}
