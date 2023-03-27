package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailResponse extends ProjectResponse {

    private Integer id;

    private String name;

    private String description;

    private MdRulesResponse rule;

}