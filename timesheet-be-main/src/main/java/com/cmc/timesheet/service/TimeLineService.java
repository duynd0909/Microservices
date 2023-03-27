package com.cmc.timesheet.service;

import com.cmc.timesheet.model.request.ListTineLineRequest;
import com.cmc.timesheet.model.request.TimeLineRequestCreateNote;
import com.cmc.timesheet.model.response.NoteTimeLineResponse;
import com.cmc.timesheet.model.response.TimeLineResponse;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TimeLineService {
    /**
     * Get page list timeline to table
     *
     * @param request
     * @param pageable
     * @return
     */
    Map<String,Object> getListTimeLine(ListTineLineRequest request, Pageable pageable);

    /**
     * create note to timeline
     *
     * @param request
     * @return
     */

    TimeLineResponse createNote(TimeLineRequestCreateNote request) throws IOException;

    NoteTimeLineResponse getDetailTimeLineRecord(String timeSheetId);


}
