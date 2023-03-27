package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectManagerRespone {
    private Integer id;

    private String name;

    private String ruleName;

    private Number ruleId;

    private String description;

    private Long missingMinute;
}
