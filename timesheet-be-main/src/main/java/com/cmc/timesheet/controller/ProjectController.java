package com.cmc.timesheet.controller;

import com.cmc.timesheet.constants.TimeSheetConstant;
import com.cmc.timesheet.model.request.ProjectInsertRequest;
import com.cmc.timesheet.model.request.ProjectRequest;
import com.cmc.timesheet.model.response.ProjectDetailResponse;
import com.cmc.timesheet.model.response.ProjectFilterRespone;
import com.cmc.timesheet.model.response.ProjectManagerRespone;
import com.cmc.timesheet.model.response.ProjectResponse;
import com.cmc.timesheet.service.ProjectService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {
    /**
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Autowired
    private ProjectService projectService;


    @GetMapping("/{id}")
    public ResponseEntity<?> findByID(@PathVariable Integer id) {
        ProjectDetailResponse res = projectService.findDetailById(id);
        if (res == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody ProjectRequest data) {
        ProjectResponse entity = projectService.create(data);
        if (ObjectUtils.allNull(entity)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(entity);
    }

    @RequestMapping(value = "/multiple-insert", method = RequestMethod.POST)
    public  ResponseEntity<String> multipleInsertFormExcel(@RequestBody List<ProjectInsertRequest> List){
        Boolean result = projectService.multipleInsertFormExcel(List);
        return result ? ResponseEntity.ok().body(TimeSheetConstant.STATUS_INSERT_SUCCESS)
                : ResponseEntity.badRequest().body(TimeSheetConstant.STATUS_INSERT_FAILED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ProjectRequest data) {
        ProjectResponse entity = projectService.update(id, data);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        projectService.delete(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> findAll() {
        List<ProjectResponse> lists = projectService.findAll();
        return ResponseEntity.ok(lists);
    }


    @RequestMapping(value = "/find-project-by-name", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectFilterRespone>> findProjectByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable paging = PageRequest.of(page, size);
        List<ProjectFilterRespone> filterResponeList = projectService.findByNameContainingIgnoreCase(name, paging);
        return ResponseEntity.ok().body(filterResponeList);
    }
    @GetMapping("/get-all-projects")
    public ResponseEntity<List<ProjectFilterRespone>> getAllProject() {
        List<ProjectFilterRespone> filterResponeList = projectService.getAllProject();
        return ResponseEntity.ok().body(filterResponeList);
    }

    @RequestMapping(value = "/search-project", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> searchProject(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){
        Pageable paging = PageRequest.of(page, size);
        Map<String,Object> filterResponeList = projectService.searchProject(name,paging);
        return ResponseEntity.ok().body(filterResponeList);
    }
}
