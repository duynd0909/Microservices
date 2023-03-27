package com.cmc.timesheet.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailResponse extends EmployeeResponse {

    private Integer id;

    private String name;

    private String fullName;

    private String knoxId;

    private String ldap;

    private String email;

    private String du;

    private Integer projectId;

    private Integer leaveRemaining;

    private MdRulesResponse rule;

}