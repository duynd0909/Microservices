package com.cmc.timesheet.entity;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project", indexes = {@Index(name = "rule_id", columnList = "rule_id")})
@SQLDelete(sql = "UPDATE project SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
@SuperBuilder
public class ProjectEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "project_id_seq", sequenceName = "project_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 5000)
    @Column(name = "description")
    private String description;

    @Column(name = "rule_id")
    private Integer ruleId;

    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MdRulesEntity rule;

}