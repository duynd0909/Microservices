package com.cmc.timesheet.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectRequest {

    private Integer id;

    private String name;

    private String description;

    private Integer ruleId;

}