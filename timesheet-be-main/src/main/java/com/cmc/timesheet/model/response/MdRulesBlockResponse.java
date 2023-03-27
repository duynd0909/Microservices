package com.cmc.timesheet.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MdRulesBlockResponse {
    private Integer id;

    private Integer startMinute;

    private Integer blockMinute;

    private Integer calcMinute;
}
