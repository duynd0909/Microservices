package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.TimeSheetEntity;
import com.cmc.timesheet.model.response.NoteTimeLineEntityResponse;
import com.cmc.timesheet.model.response.TimeLineEntityResponse;
import com.cmc.timesheet.model.response.TimeSheetExistedResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheetEntity, String>, TimeSheetRepositoryCustom {
    /**
     * Find all attendance of all employee existed in timesheet
     *
     * @return
     */
    @Query("SELECT new com.cmc.timesheet.model.response.TimeSheetExistedResponse" +
            "(timesheet.workingDate,timesheet.employeeId) FROM TimeSheetEntity timesheet")
    List<TimeSheetExistedResponse> findAllExistedTimeSheet();

    @Query(value = "SELECT new com.cmc.timesheet.model.response.TimeLineEntityResponse(" +
            "timesheet.id," +
            "timesheet.employeeId," +
            "timesheet.workingDate, " +
            "timesheet.attachments, " +
            "timesheet.earlyLeavingMinutes," +
            "timesheet.lateComingMinutes," +
            "timesheet.missingMinutes," +
            "timesheet.notes," +
            "timesheet.otFrom," +
            "timesheet.otTo," +
            "timesheet.overTimeHours," +
            "timesheet.regularHours," +
            "timesheet.timeIn," +
            "timesheet.timeOut," +
            "timesheet.workingType ," +
            "master.code ) from EmployeeEntity employee " +
            "join TimeSheetEntity timesheet on timesheet.employeeId =  employee.id " +
            "join MasterDataEntity master on timesheet.workingType = master.id " +
            "where  timesheet.workingDate between :startingDate and :endingDate AND  employee.id = :employeeId and timesheet.deletedAt is null ")
    List<TimeLineEntityResponse> getTimeLineList(@Param("startingDate") Date startingDate,
                                                 @Param("endingDate") Date endingDate,
                                                 @Param("employeeId") Integer employeeId);


    @Query("SELECT new com.cmc.timesheet.model.response.NoteTimeLineEntityResponse(" +
            "timesheet.id," +
            "timesheet.workingDate," +
            "timesheet.workingType," +
            "timesheet.timeIn," +
            "timesheet.timeOut," +
            "timesheet.employeeId," +
            "timesheet.otFrom," +
            "timesheet.otTo," +
            "timesheet.regularHours," +
            "timesheet.overTimeHours," +
            "timesheet.missingMinutes," +
            "timesheet.lateComingMinutes," +
            "timesheet.earlyLeavingMinutes," +
            "timesheet.notes," +
            "employee.fullName, " +
            "employee.ldap, " +
            "employee.email, " +
            "employee.du, " +
            "employee.projectId, " +
            "project.name ," +
            "timesheet.attachments," +
            "timesheet.createdAt)" +
            "from TimeSheetEntity timesheet " +
            "join EmployeeEntity employee on employee.id = timesheet.employeeId " +
            "join ProjectEntity project on employee.projectId = project.id " +
            "join MasterDataEntity master on timesheet.workingType = master.id " +
            "where timesheet.id = :timeSheetId and timesheet.deletedAt is null ")
    NoteTimeLineEntityResponse getDetailTimeline(@Param("timeSheetId") String timeSheetId);

    /**
     * @param employeeId
     * @return
     */
    List<TimeSheetEntity> findByEmployeeId(Integer employeeId);

    @Query(value = "select count(1) from timesheet t where t.employee_id = ?1 and " +
            " t.working_date between ?2 and ?3 and t.working_type != ?4 ", nativeQuery = true)
    Integer countByUserIdAndMonthAndYear(Integer userId, LocalDate fromDate, LocalDate toDate, Integer workingTypeNormal);


}
