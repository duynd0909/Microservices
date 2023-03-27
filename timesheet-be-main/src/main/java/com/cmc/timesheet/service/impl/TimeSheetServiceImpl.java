package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.constants.EmployeeEntityConstant;
import com.cmc.timesheet.constants.TimeSheetConstant;
import com.cmc.timesheet.constants.WorkingTypeEnum;
import com.cmc.timesheet.entity.EmployeeEntity;
import com.cmc.timesheet.entity.ProjectEntity;
import com.cmc.timesheet.entity.TimeSheetEntity;
import com.cmc.timesheet.model.request.ProjectRequest;
import com.cmc.timesheet.model.request.TimeSheetFilterRequest;
import com.cmc.timesheet.model.request.TimeSheetInsertRequest;
import com.cmc.timesheet.model.response.*;
import com.cmc.timesheet.model.request.TimeSheetViewDetailRequest;
import com.cmc.timesheet.model.response.TimeSheetExistedResponse;
import com.cmc.timesheet.model.response.TimeSheetResponse;
import com.cmc.timesheet.model.response.TimeSheetViewDetailResponse;
import com.cmc.timesheet.model.vo.TimeSheetMdRuleVo;
import com.cmc.timesheet.repository.TimeSheetRepository;
import com.cmc.timesheet.service.EmployeeService;
import com.cmc.timesheet.service.MdRulesService;
import com.cmc.timesheet.service.ProjectService;
import com.cmc.timesheet.service.TimeSheetService;
import com.cmc.timesheet.utils.DateTimeUtils;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import com.cmc.timesheet.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MdRulesService mdRulesService;

    @Autowired
    private ProjectService projectService;

    /**
     * Insert to table timesheet by excel data
     *
     * @param listTimeSheet
     * @return
     */
    @Override
    public Boolean multipleInsertFromExelData(List<TimeSheetInsertRequest> listTimeSheet) {
        try {
            List<TimeSheetExistedResponse> timeSheetExistedList = timeSheetRepository.findAllExistedTimeSheet();
            List<TimeSheetEntity> timeSheetInsertList = new ArrayList<>();
            List<EmployeeEntity> employeeEntityList = new ArrayList<>();
            List<EmployeeEntity> listExistedEmployee = employeeService.findAllByIdIn(listTimeSheet.
                    stream().map(item -> item.getUserId()).collect(Collectors.toSet()));
            List<ProjectFilterRespone> listProject = projectService.getAllProject();
            List<MdRulesResponse> listMdRules = mdRulesService.findAll();
            listTimeSheet.forEach(timeSheetForm -> {
                if(ObjectUtils.isNullorEmpty(timeSheetForm.getProject()) || timeSheetForm.getProject().equalsIgnoreCase("#N/A")){
                    return;
                }
                // Check if employee was absent or not then perform insert to database
                if (!checkExistedTimeSheetByDateAndEmployeeId
                        (timeSheetExistedList, timeSheetForm.getWorkingDate(), timeSheetForm.getUserId())) {
                    TimeSheetEntity timeSheetEntity = TimeSheetEntity.builder()
                            .workingDate(timeSheetForm.getWorkingDate())
                            .workingType(WorkingTypeEnum.LATE_COMING.getCode())
                            .timeIn(DateTimeUtils.
                                    convertStringToTime(timeSheetForm.getTimeIn(), TimeSheetConstant.TIME_FORMAT_REGEX))
                            .timeOut(DateTimeUtils.
                                    convertStringToTime(timeSheetForm.getTimeOut(), TimeSheetConstant.TIME_FORMAT_REGEX))
                            .employeeId(timeSheetForm.getUserId())
                            .regularHours(DateTimeUtils.
                                    convertStringToTime(timeSheetForm.getTimeOut(), TimeSheetConstant.TIME_FORMAT_REGEX))
//                            .overTimeHours(DateTimeUtils.
//                                    convertStringToTime(timeSheetForm.getOverTimeHours(), TimeSheetConstant.TIME_FORMAT_REGEX))
                            .build();
                    try {
                        EmployeeEntity employeeEntity = createNonExistedEmployee(listExistedEmployee, timeSheetForm);
                        if (!ObjectUtils.isNullorEmpty(employeeEntity)) {
                            ProjectFilterRespone createdProject = createNonExistedProject
                                    (timeSheetForm.getProject(), listProject, listMdRules);
                            employeeEntity.setProjectId(createdProject.getId());
                            employeeEntityList.add(employeeEntity);
                            listProject.add(createdProject);
                        }
                        Integer ruleId  = detectRuleByEmployeeAndProject
                                (listExistedEmployee,listProject,timeSheetForm.getProject(),timeSheetForm.getUserId());
                        TimeSheetMdRuleVo timeSheetMdRuleVo = mdRulesService.getTimesheetMdRuleVo(timeSheetEntity.getTimeIn(),
                                timeSheetEntity.getTimeOut(), ruleId);
                        timeSheetEntity.setMissingMinutes(timeSheetMdRuleVo.getMissingMinute());
                        timeSheetEntity.setEarlyLeavingMinutes(timeSheetMdRuleVo.getEarlyMinute());
                        timeSheetEntity.setLateComingMinutes(timeSheetMdRuleVo.getLateMinute());
                        timeSheetEntity.setWorkingType(timeSheetMdRuleVo.getWorkingType());


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    timeSheetInsertList.add(timeSheetEntity);
                }
            });
            employeeService.multipleInsertEmployee(employeeEntityList);
            timeSheetRepository.saveAll(timeSheetInsertList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<TimeSheetResponse> searchTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest) {
        return timeSheetRepository.searchTimeSheet(timeSheetFilterRequest);
    }

    @Override
    public Integer countTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest) {
        return timeSheetRepository.countTimeSheet(timeSheetFilterRequest);
    }

    /**
     * get List TimeSheet when view detail
     *
     * @param request
     * @return
     */
    @Override
    public List<TimeSheetViewDetailResponse> getListTimeSheetByEmployeeId(TimeSheetViewDetailRequest request) {
        return timeSheetRepository.getListTimeSheetDetailByUserId(request);
    }

    @Override
    public void deleteRecordViewDetail(String timeSheetId) {
        timeSheetRepository.deleteById(timeSheetId);
    }

    /**
     * Check if employee existed then create or do nothing
     *
     * @param listExistedEmployee
     * @param timeSheetForm
     * @return
     */
    private EmployeeEntity createNonExistedEmployee(List<EmployeeEntity> listExistedEmployee, TimeSheetInsertRequest timeSheetForm) {
        return !listExistedEmployee.stream().map(item -> item.getId()).
                collect(Collectors.toSet()).contains(timeSheetForm.getUserId())
                ? EmployeeEntity.builder()
                .id(timeSheetForm.getUserId())
                .name(timeSheetForm.getFullName())
                .fullName(timeSheetForm.getFullName())
                .ldap(timeSheetForm.getLdap())
                .knoxId(timeSheetForm.getKnoxId())
                .email(timeSheetForm.getLdap()
                        .concat(EmployeeEntityConstant.MAIL_EXTENSION))
                .du(timeSheetForm.getDu())
                .createdAt(new Date(System.currentTimeMillis())).build()
                : null;
    }

    /**
     * @param timeSheetExistedList
     * @param workingDate
     * @param employeeId
     * @return
     */
    private Boolean checkExistedTimeSheetByDateAndEmployeeId(
            List<TimeSheetExistedResponse> timeSheetExistedList, Date workingDate, Integer employeeId) {
        Boolean result = false;
        try {
            TimeSheetExistedResponse timeSheetExistedResponse = timeSheetExistedList.stream().
                    filter(timesheet ->
                            timesheet.getEmployeeId().equals(employeeId)
                                    && timesheet.getWorkingDate().equals(workingDate)).findFirst().get();
            if (!ObjectUtils.isNullorEmpty(timeSheetExistedResponse)) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * @param projectName
     * @param listProject
     * @param listMdRule
     * @return
     */
    private ProjectFilterRespone createNonExistedProject(String projectName, List<ProjectFilterRespone>
            listProject, List<MdRulesResponse> listMdRule) {
        ProjectEntity projectEntity = projectService.findByName(projectName);
        ProjectFilterRespone resultProject = null;
        if (!ObjectUtils.isNullorEmpty(projectEntity)) {
            resultProject = ObjectMapperUtils.map(projectEntity,ProjectFilterRespone.class);
        } else {
            ProjectResponse savedProject = projectService.create(ProjectRequest.builder().
                    name(projectName).
                    ruleId(listMdRule.get(0).getId()).
                    build());
            resultProject = ObjectMapperUtils.map(savedProject,ProjectFilterRespone.class);
        }
        return resultProject;
    }
    private Integer detectRuleByEmployeeAndProject(List<EmployeeEntity> listEmployee,List<ProjectFilterRespone> listProject
            ,String projectName,Integer employeeId){
        try {
            EmployeeEntity existedEmployee = listEmployee.stream()
                    .filter(item-> item.getId().intValue() == employeeId.intValue()).findFirst().orElse(new EmployeeEntity());
            ProjectFilterRespone existedProject = listProject.stream().
                    filter(item->item.getName().equals(projectName)).findFirst().orElse(new ProjectFilterRespone());
            if(!ObjectUtils.isNullorEmpty(existedEmployee.getRuleId())){
                return existedEmployee.getRuleId();
            }else{
                return existedProject.getRuleId();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
