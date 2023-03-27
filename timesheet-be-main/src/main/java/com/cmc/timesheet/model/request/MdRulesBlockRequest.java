package com.cmc.timesheet.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MdRulesBlockRequest {
    private Integer id;

    private Integer startMinute;

    private Integer blockMinute;

    private Integer calcMinute;
}
