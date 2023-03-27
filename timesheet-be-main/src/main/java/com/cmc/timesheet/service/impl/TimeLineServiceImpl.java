package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.constants.ConstantsTimeLine;
import com.cmc.timesheet.entity.EmployeeEntity;
import com.cmc.timesheet.entity.ProjectEntity;
import com.cmc.timesheet.entity.TimeSheetEntity;
import com.cmc.timesheet.model.request.ListTineLineRequest;
import com.cmc.timesheet.model.request.TimeLineRequestCreateNote;
import com.cmc.timesheet.model.response.*;
import com.cmc.timesheet.repository.EmployeeRepository;
import com.cmc.timesheet.repository.ProjectRepository;
import com.cmc.timesheet.repository.TimeSheetRepository;
import com.cmc.timesheet.service.FileService;
import com.cmc.timesheet.service.TimeLineService;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import com.cmc.timesheet.utils.ObjectUtils;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
public class TimeLineServiceImpl implements TimeLineService {

    @Autowired
    private TimeSheetRepository timeSheetRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FileService fileService;


    @Override
    @Transactional
    public Map<String,Object> getListTimeLine(ListTineLineRequest request, Pageable pageable) {
        List<TimeLineResponse> timeLineResponseList = new ArrayList<>();
        String employeeNameContaining =  "%" + request.getEmployeeName() + "%";
        request.setEmployeeName(employeeNameContaining);
        Page<EmployeeEntity> listEmploy = employeeRepository.findByProjectIdAndByFullNameContaining(request.getProjectId(),request.getEmployeeName() , pageable);
        List<String> notes = new ArrayList<>();
        List<String> noteLeaveUnpaid = new ArrayList<>();
        List<String> noteOTTime = new ArrayList<>();
        List<String> noteLeaveAnnual = new ArrayList<>();
        // lặp list để set data
        for (EmployeeEntity employeeEntity : listEmploy) {
            List<TimeLineEntityResponse> listWorkTime = timeSheetRepository.getTimeLineList(
                    request.getStartDate(),
                    request.getEndDate(),
                    employeeEntity.getId()
            );
            List<WorkdaysResponse> workdaysResponse = new ArrayList<>();
            Optional<ProjectEntity> project = projectRepository.findById(request.getProjectId());
            TimeLineResponse timeLineItem = ObjectMapperUtils.map(employeeEntity, TimeLineResponse.class);
            timeLineItem.setEmployeeId(employeeEntity.getId());
            timeLineItem.setEmployeeName(employeeEntity.getName());
            timeLineItem.setProject(project.get().getName());

            //lặp list workdays để set ngày giờ làm việc vào từng object trong list employee
            for (TimeLineEntityResponse timeSheetEntity : listWorkTime) {
                WorkdaysResponse workday = ObjectMapperUtils.map(timeSheetEntity, WorkdaysResponse.class);
                if (StringUtils.isEmpty(timeSheetEntity.getNotes())) {
                    timeSheetEntity.setNotes("");
                }
                switch (timeSheetEntity.getWorkingTypeCode()) {
                    case ConstantsTimeLine.LEAVE_ANNUAL:
                    case ConstantsTimeLine.ABSENT: {
                        noteLeaveAnnual.add(timeSheetEntity.getNotes());
                        workday.setTimeLineStatus(ConstantsTimeLine.AL);
                        break;
                    }
                    case ConstantsTimeLine.LEAVE_UNPAID: {
                        noteLeaveUnpaid.add(timeSheetEntity.getNotes());
                        break;
                    }
                    case ConstantsTimeLine.COMPENSATORY_LEAVE: {
                        workday.setTimeLineStatus(ConstantsTimeLine.ALH);
                        break;
                    }
                    default: {
                        notes.add(timeSheetEntity.getNotes());
                        workday.setTimeLineStatus(ConstantsTimeLine.NORMAL);
                    }
                }
                if (!ObjectUtils.isNullorEmpty(timeSheetEntity.getOtTo()) && !ObjectUtils.isNullorEmpty(timeSheetEntity.getOtFrom())) {
                    noteOTTime.add(timeSheetEntity.getNotes());
                    workday.setTimeLineStatus(ConstantsTimeLine.OT);
                }
                workday.setStrDateKey(workday.getWorkingDate().toString());
                workdaysResponse.add(workday);
            }

            timeLineItem.setWorkdaysResponseList(workdaysResponse);
            String listNotesToString = String.join(",", notes);
            timeLineItem.setNotes(listNotesToString);
            timeLineItem.setNoteLeaveUnpaid(noteLeaveUnpaid);
            timeLineItem.setNoteOTTime(noteOTTime);
            timeLineItem.setNoteLeaveAnnual(noteLeaveAnnual);
            timeLineResponseList.add(timeLineItem);

        }
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("listEmployee",ObjectMapperUtils.map(listEmploy.getContent(),timeLineResponseList));
        mapResult.put("total",listEmploy.getTotalElements());

        return mapResult;
    }

    @Override
    @Transactional
    public TimeLineResponse createNote(TimeLineRequestCreateNote request) throws IOException {
        timeSheetRepository.save(ObjectMapperUtils.map(request, TimeSheetEntity.class));
        NoteTimeLineEntityResponse noteTimeLineEntityResponse;
        noteTimeLineEntityResponse = timeSheetRepository.getDetailTimeline(request.getId());
        return ObjectMapperUtils.map(noteTimeLineEntityResponse, TimeLineResponse.class);
    }

    @Override
    public NoteTimeLineResponse getDetailTimeLineRecord(String timeSheetId) {
        NoteTimeLineEntityResponse noteTimeLineEntityResponse = timeSheetRepository.getDetailTimeline(timeSheetId);
        NoteTimeLineResponse response = new NoteTimeLineResponse();
        if(!ObjectUtils.isNullorEmpty(noteTimeLineEntityResponse)){
            response  = ObjectMapperUtils.map(noteTimeLineEntityResponse, NoteTimeLineResponse.class);
            response.setStrWorkingDate(noteTimeLineEntityResponse.getWorkingDate().toString());
        }
        return response;
    }


}
