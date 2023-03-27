package com.cmc.timesheet.entity;

import com.cmc.timesheet.constants.TimeSheetConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee",   indexes = {
        @Index(name ="project_id",columnList = "project_id")})
@SQLDelete(sql = "UPDATE employee SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
@SuperBuilder
public class EmployeeEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "knox_id")
    private String knoxId;

    @Column(name = "ldap")
    private String ldap;

    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "du")
    private String du;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "rule_id")
    private Integer ruleId;

    @Column(name = "leave_remaining")
    private Integer leaveRemaining;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable=false, updatable=false)
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id", insertable=false, updatable=false)
    private MdRulesEntity rule;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeId", cascade = CascadeType.ALL)
    private List<TimeSheetEntity> timeLineEntities;

}