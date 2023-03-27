package com.cmc.timesheet.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {

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

}