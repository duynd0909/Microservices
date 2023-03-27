package com.cmc.timesheet.entity;

import com.cmc.timesheet.constants.BaseEntityConstant;
import com.cmc.timesheet.constants.TimeSheetConstant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;
    @Column(name = "to_mail")
    private String toMail;
    @Column(name = "cc_mail")
    private String ccMail;
    @Column(name = "content", columnDefinition = "VARCHAR(10000)")
    private String content;
    @Column(name = "subject")
    private String subject;
    @Column(name = "attachment", columnDefinition = "TEXT")
    private String attachment[];
    @Column(name = "month")
    private Integer month;
    @Column(name = "year")
    private Integer year;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "is_sent")
    private Boolean isSent;
    @CreationTimestamp
    @Column(name = BaseEntityConstant.COLUMN_CREATED_AT)
    private LocalDateTime createdAt;

}
