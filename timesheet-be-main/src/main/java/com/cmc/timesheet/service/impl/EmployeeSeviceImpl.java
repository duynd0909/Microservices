package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.constants.TimeSheetConstant;
import com.cmc.timesheet.constants.WorkingTypeEnum;
import com.cmc.timesheet.entity.EmployeeEntity;
import com.cmc.timesheet.entity.ProjectEntity;
import com.cmc.timesheet.entity.TimeSheetEntity;
import com.cmc.timesheet.model.request.EmployeeInsertRequest;
import com.cmc.timesheet.model.request.EmployeeRequest;
import com.cmc.timesheet.model.response.EmployeeDetailResponse;
import com.cmc.timesheet.model.response.EmployeeResponse;
import com.cmc.timesheet.model.request.ProjectRequest;
import com.cmc.timesheet.model.response.*;
import com.cmc.timesheet.model.vo.TimeSheetMdRuleVo;
import com.cmc.timesheet.repository.EmployeeRepository;
import com.cmc.timesheet.service.EmployeeService;
import com.cmc.timesheet.service.MdRulesService;
import com.cmc.timesheet.service.ProjectService;
import com.cmc.timesheet.utils.DateTimeUtils;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import com.cmc.timesheet.utils.ObjectUtils;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeSeviceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MdRulesService mdRulesService;
    @Override
    @Transactional
    public EmployeeResponse create(EmployeeRequest request) {
        EmployeeEntity e = ObjectMapperUtils.map(request, EmployeeEntity.class);
        e.setLeaveRemaining(2);
        EmployeeEntity res = repository.save(e);
        EmployeeEntity entity = repository.save(ObjectMapperUtils.map(request, EmployeeEntity.class));
        return ObjectMapperUtils.map(res, EmployeeResponse.class);
    }

    @Override
    @Transactional
    public EmployeeResponse update(Integer id, EmployeeRequest request) {
        EmployeeEntity entity = repository.save(ObjectMapperUtils.map(request, EmployeeEntity.class));
        return ObjectMapperUtils.map(entity, EmployeeResponse.class);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<EmployeeResponse> findAll() {
        List<EmployeeEntity> pagedResult = repository.findAll();
        return ObjectMapperUtils.mapList(pagedResult, EmployeeResponse.class);
    }

    @Override
    public EmployeeDetailResponse findById(Integer id) {
        return null;
    }

    @Override
    public EmployeeDetailResponse findDetailById(Integer id) {
        EmployeeEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return ObjectMapperUtils.map(entity, EmployeeDetailResponse.class);
    }

    @Override
    public Boolean multipleInsertEmployee(List<EmployeeEntity> employeeEntityList) {
        try {
            repository.saveAll(employeeEntityList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<EmployeeEntity> findAllByIdIn(Set<Integer> idList) {
        try {
            return repository.findAllByIdIn(idList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EmployeeResponse> findByNameContains(String name) {
        List<EmployeeEntity> res = repository.findByNameContains(name);
        return ObjectMapperUtils.mapList(res, EmployeeResponse.class);
    }

    @Override
    public Map<String,Object> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        Map<String,Object> mapResult = new HashMap<>();
        Type listType = new TypeToken<List<EmployeeResponse>>(){}.getType();
        Page<EmployeeEntity> pageEmployees = repository.
                findByFullNameContainingIgnoreCase(name.trim(), pageable);
        if(!ObjectUtils.isNullorEmpty(pageEmployees.getContent())){
            mapResult.put("listEmployee",ObjectMapperUtils.map(pageEmployees.getContent(),listType));
            mapResult.put("total",pageEmployees.getTotalElements());
        }
        return mapResult;
    }

    @Override
    public Boolean multipleInsertFromExelData(List<EmployeeInsertRequest> listEmployees) {
        try {
            List<ProjectFilterRespone> listProject = projectService.getAllProject();
            List<MdRulesResponse> listMdRules = mdRulesService.findAll();
            List<EmployeeEntity> employeeEntityList = new ArrayList<>();
            Set<String> ldapSets = repository.findAllLdap();
            listEmployees.forEach(item -> {
                if (!ldapSets.contains(item.getLdap().trim().toLowerCase())) {
                    EmployeeEntity employeeEntity = new EmployeeEntity();
                    employeeEntity.setId(item.getId());
                    employeeEntity.setName(item.getName());
                    employeeEntity.setFullName(item.getName());
                    employeeEntity.setLdap(item.getLdap());
                    employeeEntity.setDu(item.getDu());
                    employeeEntity.setEmail(item.getEmail());
                    employeeEntity.setProjectId(createNonExistedProject
                            (item.getProjectName(), listProject, listMdRules));
                    employeeEntityList.add(employeeEntity);
                }
            });
            if(employeeEntityList.size() > 0)
                repository.saveAll(employeeEntityList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    private Integer createNonExistedProject(String projectName, List<ProjectFilterRespone>
            listProject, List<MdRulesResponse> listMdRule) {
        ProjectEntity projectEntity = projectService.findByName(projectName);
        Integer projectId = null;
        if (!ObjectUtils.isNullorEmpty(projectEntity)) {
            projectId = projectEntity.getId();
        } else {
            ProjectResponse savedProject = projectService.create(ProjectRequest.builder().
                    name(projectName).
                    ruleId(listMdRule.get(0).getId()).
                    build());
            projectId = savedProject.getId();
        }
        return projectId;
    }
}
