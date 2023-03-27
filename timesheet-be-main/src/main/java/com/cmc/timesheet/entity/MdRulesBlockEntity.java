package com.cmc.timesheet.entity;

import com.cmc.timesheet.constants.MdRulesConstant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "md_rules_block")
public class MdRulesBlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = MdRulesConstant.COLUMN_ID, nullable = false)
    private Integer id;

    @Column(name = MdRulesConstant.COLUMN_START_MINUTE, nullable = false)
    private Integer startMinute;

    @Column(name = MdRulesConstant.COLUMN_BLOCK_MINUTE, nullable = false)
    private Integer blockMinute;

    @Column(name = MdRulesConstant.COLUMN_CALC_MINUTE, nullable = false)
    private Integer calcMinute;

}
