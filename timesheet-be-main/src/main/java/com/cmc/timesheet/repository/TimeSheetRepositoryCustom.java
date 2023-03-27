package com.cmc.timesheet.repository;

import com.cmc.timesheet.model.request.TimeSheetFilterRequest;
import com.cmc.timesheet.model.request.TimeSheetViewDetailRequest;
import com.cmc.timesheet.model.response.TimeSheetResponse;
import com.cmc.timesheet.model.request.TimeSheetDTO;
import com.cmc.timesheet.model.request.TimeSheetInsertRequest;
import com.cmc.timesheet.model.response.TimeSheetViewDetailResponse;

import java.time.LocalDate;
import java.util.List;

public interface TimeSheetRepositoryCustom {
    /**
     *
     * @param timeSheetFilterRequest
     * @return
     */
    List<TimeSheetResponse> searchTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest);

    /**
     *
     * @param timeSheetFilterRequest
     * @return
     */
    Integer countTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest);

    List<TimeSheetDTO> getTimeSheetListGroupByUserId(LocalDate fromDate, LocalDate toDate, List<Integer> userIds, Boolean isSendAll);

    /**
     * method to select timeSheet List when view detail
     * @param request
     * @return
     */
    List<TimeSheetViewDetailResponse> getListTimeSheetDetailByUserId(TimeSheetViewDetailRequest request);
}
