package com.cmc.timesheet.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MdRulesResponse {
    private Integer id;

    private String name;

    private String description;

    private String workingTimeIn;

    private String workingTimeOut;

    private Integer lateLimit;

    private Integer minuteLimit;

    private Integer totalMinuteLimit;

    private Integer ruleType;

    private Integer blockId;

    private MdRulesBlockResponse mdRulesBlockResponse;

    private Date createdAt;
}