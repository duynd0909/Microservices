package com.cmc.timesheet.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeInsertRequest {
    private Integer id;
    private String name;
    private String du;
    private String ldap;
    private String projectName;
    private String knoxId;
    private String email;

}
