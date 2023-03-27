package com.cmc.timesheet.entity;

import com.cmc.timesheet.constants.BaseEntityConstant;
import com.cmc.timesheet.constants.TimeSheetConstant;
import com.cmc.timesheet.model.request.TimeSheetDTO;
import com.cmc.timesheet.model.response.TimeSheetResponse;
import com.cmc.timesheet.model.response.TimeSheetViewDetailResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TimeSheetConstant.TABLE_NAME,
        indexes = {
                @Index(name = TimeSheetConstant.COLUMN_EMPLOYEE_ID,
                        columnList = TimeSheetConstant.COLUMN_EMPLOYEE_ID),
                @Index(name = TimeSheetConstant.COLUMN_WORKING_TYPE,
                        columnList = TimeSheetConstant.COLUMN_WORKING_TYPE)})
@SQLDelete(sql = "UPDATE timeSheet SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
@SuperBuilder
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "TimeSheetDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = TimeSheetDTO.class,
                                columns = {
                                        @ColumnResult(name = "workingDate", type = Date.class),
                                        @ColumnResult(name = "workingDateStr", type = String.class),
                                        @ColumnResult(name = "fullName", type = String.class),
                                        @ColumnResult(name = "userId", type = Integer.class),
                                        @ColumnResult(name = "du", type = String.class),
                                        @ColumnResult(name = "ldap", type = String.class),
                                        @ColumnResult(name = "project", type = String.class),
                                        @ColumnResult(name = "timeIn", type = String.class),
                                        @ColumnResult(name = "timeOut", type = String.class),
                                        @ColumnResult(name = "email", type = String.class),
                                        @ColumnResult(name = "workingType", type = Integer.class),
                                        @ColumnResult(name = "exception", type = String.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "TimeSheetResponse",
                classes = {
                        @ConstructorResult(
                                targetClass = TimeSheetResponse.class,
                                columns = {
                                        @ColumnResult(name = "employeeId", type = Integer.class),
                                        @ColumnResult(name = "fullName", type = String.class),
                                        @ColumnResult(name = "knoxID", type = String.class),
                                        @ColumnResult(name = "projectName", type = String.class),
                                        @ColumnResult(name = "wfhDateList", type = String.class),
                                        @ColumnResult(name = "otDateList", type = String.class),
                                        @ColumnResult(name = "compensatoryDateList", type = String.class),
                                        @ColumnResult(name = "abnormalLeaveList", type = String.class),
                                        @ColumnResult(name = "dayOffList", type = String.class),
                                        @ColumnResult(name = "notes", type = String.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "TimeSheetViewDetailResponse",
                classes = {
                        @ConstructorResult(
                                targetClass = TimeSheetViewDetailResponse.class,
                                columns = {
                                        @ColumnResult(name = "timeSheetId", type = String.class),
                                        @ColumnResult(name = "employeeId", type = Integer.class),
                                        @ColumnResult(name = "fullName", type = String.class),
                                        @ColumnResult(name = "knoxID", type = String.class),
                                        @ColumnResult(name = "projectName", type = String.class),
                                        @ColumnResult(name = "wfhDateList", type = String.class),
                                        @ColumnResult(name = "otDateList", type = String.class),
                                        @ColumnResult(name = "compensatoryDateList", type = String.class),
                                        @ColumnResult(name = "abnormalLeaveList", type = String.class),
                                        @ColumnResult(name = "dayOffList", type = String.class),
                                        @ColumnResult(name = "notes", type = String.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "Integer",
                classes = {
                        @ConstructorResult(
                                targetClass = Integer.class,
                                columns = {
                                        @ColumnResult(name = "total", type = Integer.class),
                                }
                        )
                }
        )})
public class TimeSheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = TimeSheetConstant.COLUMN_ID, columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(name = TimeSheetConstant.COLUMN_WORKING_DATE, nullable = false)
    private Date workingDate;

    @Column(name = TimeSheetConstant.COLUMN_WORKING_TYPE, nullable = false)
    private Integer workingType;

    @Column(name = TimeSheetConstant.COLUMN_TIME_IN)
    private Time timeIn;

    @Column(name = TimeSheetConstant.COLUMN_TIME_OUT)
    private Time timeOut;

    @Column(name = TimeSheetConstant.COLUMN_EMPLOYEE_ID)
    private Integer employeeId;

    @Column(name = TimeSheetConstant.COLUMN_OT_FROM)
    private Time otFrom;

    @Column(name = TimeSheetConstant.COLUMN_OT_TO)
    private Time otTo;

    @Column(name = TimeSheetConstant.COLUMN_REGULAR_HOURS)
    private Time regularHours;

    @Column(name = TimeSheetConstant.COLUMN_OVERTIME_HOURS)
    private Time overTimeHours;

    @Column(name = TimeSheetConstant.COLUMN_MISSING_MINUTES)
    private Integer missingMinutes;

    @Column(name = TimeSheetConstant.COLUMN_LATE_COMING)
    private Integer lateComingMinutes;
    @Column(name = TimeSheetConstant.COLUMN_EARLY_LEAVING)
    private Integer earlyLeavingMinutes;

    @Column(name = TimeSheetConstant.COLUMN_NOTES, columnDefinition = "VARCHAR(5000)")
    private String notes;

    @Column(name = TimeSheetConstant.COLUMN_ATTACHMENTS, columnDefinition = "TEXT[]")
    private String[] attachments;

    @CreationTimestamp
    @Column(name = BaseEntityConstant.COLUMN_CREATED_AT)
    private Date createdAt;

    @Column(name = BaseEntityConstant.COLUMN_DELETED_AT)
    private Date deletedAt;

}
