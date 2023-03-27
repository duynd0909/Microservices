package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFilterRespone {
    private Integer id;

    private String name;

    private Integer ruleId;
}
