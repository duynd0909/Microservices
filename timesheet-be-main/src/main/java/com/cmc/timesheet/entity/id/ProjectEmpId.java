package com.cmc.timesheet.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ProjectEmpId implements Serializable {

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "employee_id")
    private Integer employeeId;
}
