package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse  {

    private Integer id;

    private String name;

    private String description;

    private  Integer ruleId;

    private String ruleName;

    private Long missingMinute;
}