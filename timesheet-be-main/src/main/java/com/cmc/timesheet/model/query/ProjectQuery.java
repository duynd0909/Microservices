package com.cmc.timesheet.model.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectQuery {

    private Integer id;

    private String name;

    private Integer rule_id;

    private String description;
}