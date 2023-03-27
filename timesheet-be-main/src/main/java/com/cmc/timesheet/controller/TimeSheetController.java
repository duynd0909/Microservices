package com.cmc.timesheet.controller;

import com.cmc.timesheet.constants.TimeSheetConstant;
import com.cmc.timesheet.model.request.TimeSheetFilterRequest;
import com.cmc.timesheet.model.request.TimeSheetInsertRequest;
import com.cmc.timesheet.model.request.TimeSheetViewDetailRequest;
import com.cmc.timesheet.model.response.TimeSheetResponse;
import com.cmc.timesheet.model.response.TimeSheetViewDetailResponse;
import com.cmc.timesheet.service.TimeSheetService;
import com.cmc.timesheet.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    /**
     * @param list
     * @return
     */
    @RequestMapping(value = "/multiple-insert", method = RequestMethod.POST)
    public ResponseEntity<String> multipleInsertFromExelData(@RequestBody List<TimeSheetInsertRequest> list) {
        Boolean result = timeSheetService.multipleInsertFromExelData(list);

        return result ? ResponseEntity.ok().body(TimeSheetConstant.STATUS_INSERT_SUCCESS)
                : ResponseEntity.badRequest().body(TimeSheetConstant.STATUS_INSERT_FAILED);
    }

    /**
     * @param timeSheetFilterRequest
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchTimeSheet(@RequestBody TimeSheetFilterRequest timeSheetFilterRequest) {
        Map<String, Object> mapResult = new HashMap<>();
        List<TimeSheetResponse> timeSheetResponses = timeSheetService.searchTimeSheet(timeSheetFilterRequest);
        Integer totalTimeSheet = timeSheetService.countTimeSheet(timeSheetFilterRequest);

        if (!ObjectUtils.isNullorEmpty(timeSheetResponses)) {
            mapResult.put("listTimeSheet", timeSheetResponses);
            mapResult.put("total", totalTimeSheet);
            return ResponseEntity.ok(mapResult);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * api to get list timesheet by employeeId when view detail;
     *
     * @param request
     * @return
     */
    @GetMapping("/view-detail")
    public ResponseEntity<?> getTimeSheetListByEmployeeId(@ModelAttribute TimeSheetViewDetailRequest request) {
        if (request.getEmployeeId() == null) {
           return ResponseEntity.badRequest().body(TimeSheetConstant.VIEW_DETAIL_NOT_ID);
        }else{
            List<TimeSheetViewDetailResponse> responses;
            responses = timeSheetService.getListTimeSheetByEmployeeId(request);
                return ResponseEntity.ok(responses);
        }

    }

    @PutMapping("/delete-record")
    public ResponseEntity<?> deleteRecord(@RequestParam String timeSheetId) {
        timeSheetService.deleteRecordViewDetail(timeSheetId);
        return ResponseEntity.ok().body(TimeSheetConstant.DELETE_RECORD_TIMESHEET_SUCCESS);

    }
}
