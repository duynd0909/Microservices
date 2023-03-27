package com.cmc.timesheet.entity;

import com.cmc.timesheet.constants.MdRulesConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.time.OffsetTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "md_rules")
@SQLDelete(sql = "UPDATE md_rules SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
public class MdRulesEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MdRulesConstant.COLUMN_ID)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = MdRulesConstant.COLUMN_NAME)
    private String name;

    @Size(max = 5000)
    @Column(name = MdRulesConstant.COLUMN_DESCRIPTION)
    private String description;

    @Column(name = MdRulesConstant.COLUMN_WORKING_TIME_IN, nullable = false)
    private OffsetTime workingTimeIn;

    @Column(name = MdRulesConstant.COLUMN_WORKING_TIME_OUT, nullable = false)
    private OffsetTime workingTimeOut;

    @Column(name = MdRulesConstant.COLUMN_LATE_LIMIT)
    private Integer lateLimit;

    @Column(name = MdRulesConstant.COLUMN_MINUTE_LIMIT)
    private Integer minuteLimit;

    @Column(name = MdRulesConstant.COLUMN_TOTAL_MINUTE_LIMIT)
    private Integer totalMinuteLimit;

    @Column(name = MdRulesConstant.COLUMN_RULE_TYPE, nullable = false)
    private Integer ruleType;

    @Column(name = MdRulesConstant.COLUMN_BLOCK_ID)
    private Integer blockId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rule")
    private List<ProjectEntity> projects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rule")
    private List<EmployeeEntity> employees;

}