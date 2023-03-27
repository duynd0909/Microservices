package com.cmc.timesheet.service;

import com.cmc.timesheet.entity.ProjectEntity;
import com.cmc.timesheet.model.request.ProjectInsertRequest;
import com.cmc.timesheet.model.request.ProjectRequest;
import com.cmc.timesheet.model.response.*;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectService extends BaseService<ProjectRequest, ProjectResponse, Integer> {

    ProjectDetailResponse findDetailById(Integer id);

    /**
     *
     * @param name
     * @param pageable
     * @return
     */
    List<ProjectFilterRespone> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     *
     * @param firstDay
     * @param currentDay
     * @return
     */
    List<DashboardResponse> getDataDashboard(Date firstDay , Date currentDay);

    /**
     *
     * @return
     */
    List<ProjectFilterRespone> getAllProject();
    /**
     *
     * @param name
     * @return
     */
    ProjectEntity findByName(String name);

    /**
     *
     * @param name
     * @return
     */
    Map<String,Object> searchProject(String name, Pageable pageable);

    Boolean multipleInsertFormExcel(List<ProjectInsertRequest> List);
}
