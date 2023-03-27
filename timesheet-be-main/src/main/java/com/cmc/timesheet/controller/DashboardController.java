package com.cmc.timesheet.controller;

import com.cmc.timesheet.model.request.DashboardRequest;
import com.cmc.timesheet.model.request.ProjectRequest;
import com.cmc.timesheet.model.response.*;
import com.cmc.timesheet.service.ProjectService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	/**
	 *
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<DashboardResponse>> findAll(
			@RequestParam Date firstDay ,
			@RequestParam Date currentDay) {
		List<DashboardResponse> lists = projectService.getDataDashboard(firstDay, currentDay);
		return ResponseEntity.ok().body(lists);
	}
}
