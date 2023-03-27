package com.cmc.timesheet.controller;

import com.cmc.timesheet.constants.TimeSheetConstant;
import com.cmc.timesheet.model.query.EmployeeQuery;
import com.cmc.timesheet.model.request.EmployeeInsertRequest;
import com.cmc.timesheet.model.request.EmployeeRequest;
import com.cmc.timesheet.model.request.TimeSheetInsertRequest;
import com.cmc.timesheet.model.response.EmployeeDetailResponse;
import com.cmc.timesheet.model.response.EmployeeResponse;
import com.cmc.timesheet.model.response.ProjectFilterRespone;
import com.cmc.timesheet.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;


	@GetMapping("/{id}")
	public ResponseEntity<?> findByID(@PathVariable Integer id) {
		EmployeeDetailResponse res = service.findDetailById(id);
		if (res == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(res);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody EmployeeRequest data) {
		EmployeeResponse entity = service.create(data);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody EmployeeRequest data) {
		EmployeeResponse entity = service.update(id, data);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(entity);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAll(EmployeeQuery query) {
		List<EmployeeResponse> lists = service.findAll();
		return ResponseEntity.ok(lists);
	}

	@RequestMapping(value = "/find-employee-by-name", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> findProjectByName(
			@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Pageable paging = PageRequest.of(page, size);
		Map<String,Object> filterResponeList = service.findByNameContainingIgnoreCase(name, paging);
		return ResponseEntity.ok().body(filterResponeList);
	}

	@RequestMapping(value = "/multiple-insert", method = RequestMethod.POST)
	public ResponseEntity<String> multipleInsertFromExelData(@RequestBody List<EmployeeInsertRequest> list) {
		Boolean result = service.multipleInsertFromExelData(list);

		return result ? ResponseEntity.ok().body(TimeSheetConstant.STATUS_INSERT_SUCCESS)
				: ResponseEntity.badRequest().body(TimeSheetConstant.STATUS_INSERT_FAILED);
	}
}
