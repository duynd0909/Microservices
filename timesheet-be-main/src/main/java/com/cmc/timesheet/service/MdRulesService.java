package com.cmc.timesheet.service;

import com.cmc.timesheet.model.request.MdRulesRequest;
import com.cmc.timesheet.model.response.MdRulesResponse;
import com.cmc.timesheet.model.vo.TimeSheetMdRuleVo;

import java.sql.Time;

public interface MdRulesService extends BaseService<MdRulesRequest, MdRulesResponse, Integer> {

    /**
     *
     * @param timeIn
     * @param timeOut
     * @param ruleId
     * @return
     * @throws Exception
     */
    TimeSheetMdRuleVo getTimesheetMdRuleVo(Time timeIn, Time timeOut, Integer ruleId) throws Exception ;
}
