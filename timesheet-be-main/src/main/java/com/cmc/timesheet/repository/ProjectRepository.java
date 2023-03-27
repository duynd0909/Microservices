package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.ProjectEntity;
import com.cmc.timesheet.model.response.DashboardResponse;
import com.cmc.timesheet.model.response.ProjectManagerRespone;
import com.cmc.timesheet.model.response.ProjectResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    /**
     * @param name
     * @param pageable
     * @return
     */
    List<ProjectEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     *
     * @param name
     * @return
     */
    ProjectEntity findByName(String name);
    
    @Query(value = "SELECT new com.cmc.timesheet.model.response.ProjectManagerRespone(project.id," +
            " project.name,rule.name, project.ruleId, project.description, SUM(coalesce(timesheet.missingMinutes,0))) " +
            "FROM ProjectEntity project left join EmployeeEntity employee on project.id = employee.projectId " +
            "left join TimeSheetEntity timesheet on timesheet.employeeId = employee.id " +
            "left join MdRulesEntity rule on project.ruleId = rule.id " +
            "WHERE LOWER(project.name)  LIKE lower(:name) " +
            "GROUP BY project.id,rule.name " +
            "ORDER BY project.createdAt DESC ")
    Page<ProjectEntity> searchProjectByName(@Param("name") String name, Pageable pageable);

    @Query(value = "SELECT new com.cmc.timesheet.model.response.DashboardResponse(project.id, project.name," +
            " SUM(coalesce(timesheet.missingMinutes,0) ))  " +
            "FROM ProjectEntity project left join EmployeeEntity employee on project.id = employee.projectId " +
            "left join TimeSheetEntity timesheet on timesheet.employeeId = employee.id " +
            "left join MasterDataEntity  tmd on timesheet.workingType = tmd.id " +
            "left join MdRulesEntity rule on project.ruleId = rule.id " +
            "WHERE tmd.code IN :listWorkingType AND timesheet.workingDate BETWEEN :firstDay and :currentDay " +
            "GROUP BY project.id,rule.name")
    List<DashboardResponse> getDataDashboard(
            List<String> listWorkingType , @Param("firstDay") Date firstDay, @Param("currentDay") Date currentDay);
}

