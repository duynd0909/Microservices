package com.cmc.timesheet.controller;


import com.cmc.timesheet.constants.ConstantsTimeLine;
import com.cmc.timesheet.model.request.ListTineLineRequest;
import com.cmc.timesheet.model.request.TimeLineRequestCreateNote;
import com.cmc.timesheet.model.response.NoteTimeLineResponse;
import com.cmc.timesheet.model.response.TimeLineResponse;
import com.cmc.timesheet.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping(ConstantsTimeLine.REQUEST_MAPPING + ConstantsTimeLine.TIME_LINE_MAPPING)
public class TimeLineController {

    Logger logger
            = Logger.getLogger(
            TimeLineController.class.getName());

    @Autowired
    private TimeLineService timeLineService;

    @GetMapping(ConstantsTimeLine.GET_LIST_TIME_LINE)
    public ResponseEntity<?> getListTimeLine(@ModelAttribute ListTineLineRequest request) {
        if (request.getProjectId() == null) {
            return ResponseEntity.badRequest().body(ConstantsTimeLine.NO_PROJECT);
        } else if (request.getStartDate() == null || request.getEndDate() == null) {
            return ResponseEntity.badRequest().body(ConstantsTimeLine.NO_DATE);
        } else {
            Pageable paging = PageRequest.of(request.getPage(), request.getPageSize());
            return ResponseEntity.ok(timeLineService.getListTimeLine(request, paging));
        }

    }

    @PostMapping(value = ConstantsTimeLine.ADD_NOTE_TIME_LINE)
    public ResponseEntity<?> create(@RequestBody TimeLineRequestCreateNote data) throws IOException {
        TimeLineResponse response = timeLineService.createNote(data);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(ConstantsTimeLine.GET_DETAIL_NOTE_TIME_LINE)
    public ResponseEntity<?> getNoteTimeLineDetail(@RequestParam String timeSheetId) throws IOException {
        NoteTimeLineResponse entity = timeLineService.getDetailTimeLineRecord(timeSheetId);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(entity);
    }
}
