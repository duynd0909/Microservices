package com.cmc.timesheet.entity;

import com.cmc.timesheet.entity.id.ProjectEmpId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project_emp")
public class ProjectEmpEntity {

    @EmbeddedId
    private ProjectEmpId id;

    @Column(name = "joining_date")
    private Date joiningDate;

    @Column(name = "leaving_date")
    private Date leavingDate;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    EmployeeEntity employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    ProjectEntity project;

}