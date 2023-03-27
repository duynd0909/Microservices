package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.constants.MasterDataConstant;
import com.cmc.timesheet.entity.ProjectEntity;
import com.cmc.timesheet.model.request.ProjectInsertRequest;
import com.cmc.timesheet.model.request.ProjectRequest;
import com.cmc.timesheet.model.response.*;
import com.cmc.timesheet.repository.ProjectRepository;
import com.cmc.timesheet.service.MdRulesService;
import com.cmc.timesheet.utils.ObjectUtils;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.cmc.timesheet.service.ProjectService;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private List<String> listWorkingType = Arrays.asList(MasterDataConstant.LATE_COMING,
            MasterDataConstant.EARLY_LEAVING, MasterDataConstant.LATE_COMING_EARLY_LEAVING);

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    private MdRulesService mdRulesService;



    @Override
    @Transactional
    public ProjectResponse create(ProjectRequest request) {
        ProjectEntity entity = projectRepository.save(ObjectMapperUtils.map(request, ProjectEntity.class));
        return ObjectMapperUtils.map(entity, ProjectResponse.class);
    }

    @Override
    @Transactional
    public ProjectResponse update(Integer id, ProjectRequest request) {

        ProjectEntity entity = projectRepository.save(ObjectMapperUtils.map(request, ProjectEntity.class));
        return ObjectMapperUtils.map(entity, ProjectResponse.class);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
    
    @Override
    public List<ProjectResponse> findAll() {
        List<ProjectEntity> projectEntities = projectRepository.findAll();
        return ObjectMapperUtils.mapList(projectEntities, ProjectResponse.class);
    }
    @Override
    public ProjectDetailResponse findById(Integer id) {
        return null;
    }

    @Override
    public ProjectDetailResponse findDetailById(Integer id) {
        ProjectEntity entity = projectRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return ObjectMapperUtils.map(entity, ProjectDetailResponse.class);
    }

    @Override
    public List<ProjectFilterRespone> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        List<ProjectEntity> projectEntities = projectRepository.
                findByNameContainingIgnoreCase(name.toLowerCase().trim(), pageable);
        return ObjectMapperUtils.mapList(projectEntities, ProjectFilterRespone.class);

    }

    @Override
    public List<DashboardResponse> getDataDashboard(Date firstDay, Date currentDay) {
        List<DashboardResponse> responses = projectRepository.getDataDashboard(listWorkingType, firstDay, currentDay);
        return responses;
    }

    @Override
    public List<ProjectFilterRespone> getAllProject() {
        List<ProjectEntity> projectEntities = projectRepository.findAll();
        return ObjectMapperUtils.mapList(projectEntities, ProjectFilterRespone.class);
    }

    @Override
    public Map<String,Object> searchProject(String name, Pageable pageable) {
        Map<String,Object> mapResult = new HashMap<>();
        Type listType = new TypeToken<List<ProjectManagerRespone>>(){}.getType();
        Page<ProjectEntity> pageProject = projectRepository.searchProjectByName("%" + name.trim() + "%" , pageable);
        if(!ObjectUtils.isNullorEmpty(pageProject.getContent())){
            mapResult.put("data",ObjectMapperUtils.map(pageProject.getContent(),listType));
            mapResult.put("total",pageProject.getTotalElements());
        }
        return mapResult;
    }

    @Override
    public ProjectEntity findByName(String name) {
        try {
            return projectRepository.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean multipleInsertFormExcel(List<ProjectInsertRequest> list) {
        try {
            List<MdRulesResponse> listMdRules = mdRulesService.findAll();
            List<ProjectFilterRespone> listProject = getAllProject();
            List<String> listProjectName = listProject.stream().map(item -> item.getName()).collect(Collectors.toList());

            List<ProjectEntity> projectInsertList = new ArrayList<>();
            list.forEach(item -> {
                if (!listProjectName.contains(item.getName())) {
                    ProjectEntity projectEntity = new ProjectEntity();
                    projectEntity.setName(item.getName());
                    projectEntity.setRuleId(listMdRules.get(0).getId());
                    projectInsertList.add(projectEntity);
                }
            });
            if (projectInsertList.size() > 0)
                projectRepository.saveAll(projectInsertList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
}
