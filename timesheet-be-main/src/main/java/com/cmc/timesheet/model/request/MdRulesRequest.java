package com.cmc.timesheet.model.request;

import com.cmc.timesheet.entity.MdRulesBlockEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetTime;
import java.util.Date;

@Getter
@Setter
public class MdRulesRequest {
    private Integer id;

    private String name;

    private String description;

    private OffsetTime workingTimeIn;

    private OffsetTime workingTimeOut;

    private Integer lateLimit;

    private Integer minuteLimit;

    private Integer totalMinuteLimit;

    private Integer blockId;

    private MdRulesBlockRequest mdRulesBlockRequest;

    private String ruleType;

    private Date createdAt;
}