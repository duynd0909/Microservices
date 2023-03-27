package com.cmc.timesheet.service;

import com.cmc.timesheet.model.request.TimeSheetFilterRequest;
import com.cmc.timesheet.model.request.TimeSheetInsertRequest;
import com.cmc.timesheet.model.request.TimeSheetViewDetailRequest;
import com.cmc.timesheet.model.response.TimeSheetResponse;
import com.cmc.timesheet.model.response.TimeSheetViewDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TimeSheetService {

    /**
     *
     * @param listTimeSheet
     * @return
     */
    Boolean multipleInsertFromExelData(List<TimeSheetInsertRequest> listTimeSheet);

    /**
     *
     * @param timeSheetFilterRequest
     * @return
     */
    List<TimeSheetResponse> searchTimeSheet (TimeSheetFilterRequest timeSheetFilterRequest);

    /**
     *
     * @param timeSheetFilterRequest
     * @return
     */
    Integer countTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest);

    List<TimeSheetViewDetailResponse> getListTimeSheetByEmployeeId (TimeSheetViewDetailRequest request);

    void   deleteRecordViewDetail(String timeSheetId);

}
