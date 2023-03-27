package com.cmc.timesheet.controller;

import com.cmc.timesheet.model.request.MdRulesRequest;
import com.cmc.timesheet.model.response.MdRulesResponse;
import com.cmc.timesheet.service.MdRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class MdRulesController {

	@Autowired
	private MdRulesService service;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody MdRulesRequest data) {
		MdRulesResponse entity = service.create(data);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(entity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MdRulesRequest data) {
		MdRulesResponse entity = service.update(id, data);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(entity);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<?> findAll() {
		List<MdRulesResponse> lists = service.findAll();
		return ResponseEntity.ok(lists);
	}

}
