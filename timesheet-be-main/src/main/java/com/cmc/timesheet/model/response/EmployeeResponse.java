package com.cmc.timesheet.model.response;

import com.cmc.timesheet.entity.ProjectEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private Integer id;

    private String name;

    private String fullName;

    private String knoxId;

    private String ldap;

    private String email;

    private String du;

    private Integer projectId;

    private Integer ruleId;

    private Integer leaveRemaining;

    private ProjectFilterRespone project;
}