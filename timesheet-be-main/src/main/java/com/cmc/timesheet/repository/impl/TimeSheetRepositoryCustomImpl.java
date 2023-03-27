package com.cmc.timesheet.repository.impl;

import com.cmc.timesheet.model.request.TimeSheetDTO;
import com.cmc.timesheet.model.request.TimeSheetFilterRequest;
import com.cmc.timesheet.model.request.TimeSheetViewDetailRequest;
import com.cmc.timesheet.model.response.TimeSheetResponse;
import com.cmc.timesheet.model.response.TimeSheetViewDetailResponse;
import com.cmc.timesheet.repository.BaseRepository;
import com.cmc.timesheet.repository.TimeSheetRepositoryCustom;
import com.cmc.timesheet.utils.ObjectUtils;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TimeSheetRepositoryCustomImpl extends BaseRepository implements TimeSheetRepositoryCustom {


    @Override
    public List<TimeSheetResponse> searchTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest) {
        Query query = this.buildSearchTimeSheet(false, timeSheetFilterRequest, TimeSheetResponse.class);
        initPaging(query, timeSheetFilterRequest.getPageNumber(), timeSheetFilterRequest.getPageSize());
        return query.getResultList();
    }

    @Override
    public Integer countTimeSheet(TimeSheetFilterRequest timeSheetFilterRequest) {
        Query query = this.buildSearchTimeSheet(true, timeSheetFilterRequest, Integer.class);
        return (int) query.getSingleResult();
    }

    /**
     * @param count
     * @param timeSheetFilterRequest
     * @param clazz
     * @return
     */
    private Query buildSearchTimeSheet(final boolean count, TimeSheetFilterRequest timeSheetFilterRequest, Class clazz) {
        StringBuilder sql = new StringBuilder();
        if (count) {
            sql.append(" SELECT count(distinct emp.id) AS total");
        } else {
            sql.append("SELECT emp.id  employeeId,emp.full_name AS fullName,emp.knox_id AS knoxID,pr.name AS projectName,\n" +
                    "string_agg(\n" +
                    " \tCASE WHEN tmd.code = 'WFH' THEN CAST(ts.working_date AS TEXT) ELSE NULL END,'/n/') as wfhDateList,\n" +
                    "string_agg(\n" +
                    " \tCASE WHEN ts.overtime_from IS NOT NULL AND ts.overtime_to  IS NOT NULL \n" +
                    "\tTHEN concat(ts.working_date,': ', ts.overtime_from,' ~ ',ts.overtime_to ) ELSE NULL END,'/n/')otDateList,\n" +
                    "string_agg(\n" +
                    "\tCASE WHEN tmd.code = 'COMPENSATORY_LEAVE'\n" +
                    "\tTHEN concat(ts.working_date,':',ts.missing_minutes) ELSE NULL END,'/n/')compensatoryDateList,\n" +
                    "string_agg(CASE WHEN tmd.code = 'LATE_COMING' \n" +
                    "\t\t\t\tTHEN concat(ts.working_date,' ',tmd.name,' ',ts.late_coming_minutes,' minutes') \n" +
                    "\t\t\t\tWHEN tmd.code = 'EARLY_LEAVING'  \n" +
                    "\t\t\t\tTHEN concat(ts.working_date,' ',tmd.name,' ',ts.early_leaving_minutes,' minutes') \n" +
                    "\t\t\t\tWHEN tmd.code = 'LATE_COMING_EARLY_LEAVING'\n" +
                    "\t\t\t\tTHEN concat(ts.working_date,' ',\n" +
                    "\t\t\t\t'Late coming ',ts.late_coming_minutes,' minutes',', Early leaving ',ts.early_leaving_minutes,' minutes')\n" +
                    "\t \t\tELSE NULL  END,'/n/')abnormalLeaveList,\n" +
                    "string_agg(\n" +
                    " \tCASE WHEN ts.missing_minutes >= 240 OR tmd.code = 'ABSENT'\n" +
                    "\tTHEN concat(ts.working_date,\n" +
                    "\t\tCASE WHEN ts.missing_minutes >=240 AND ts.missing_minutes < 480 THEN '(0.5)' ELSE '' END)\n" +
                    "\tELSE NULL END,'/n/')dayOffList,\n" +
                    "string_agg(ts.notes,'/n/')notes");
        }
        sql.append(" FROM employee emp \n" +
                "JOIN timesheet ts ON emp.id = ts.employee_id\n" +
                "LEFT JOIN project pr ON pr.id = emp.project_id\n" +
                "LEFT JOIN master_data tmd ON ts.working_type = tmd.id WHERE ts.deleted_at IS NULL ");
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isNullorEmpty(timeSheetFilterRequest.getFromDate())) {
            sql.append(" AND ts.working_date >= :fromDate ");
            params.put("fromDate", timeSheetFilterRequest.getFromDate());
        }
        if (!ObjectUtils.isNullorEmpty(timeSheetFilterRequest.getToDate())) {
            sql.append(" AND ts.working_date <= :toDate ");
            params.put("toDate", timeSheetFilterRequest.getToDate());
        }
        if (!ObjectUtils.isNullorEmpty(timeSheetFilterRequest.getAccount())) {
            sql.append(" AND (emp.ldap LIKE :account OR LOWER(emp.full_name) LIKE :account) ");
            params.put("account", "%" + timeSheetFilterRequest.getAccount().toLowerCase().trim() + "%");
        }
        if (!ObjectUtils.isNullorEmpty(timeSheetFilterRequest.getProjectIds())) {
            sql.append(" AND ( -1 IN :projectIds OR (pr.id IN :projectIds))  ");
            params.put("projectIds", timeSheetFilterRequest.getProjectIds());
        }
        if (!count) {
            sql.append(" GROUP BY emp.id,pr.id");
        }
        Query query = getEntityManager().createNativeQuery(sql.toString(), clazz.getSimpleName());
        initQueryParams(query, params);
        return query;
    }

    private Query buildQuerySelectDetailTimeSheetByUserId(final boolean count, TimeSheetViewDetailRequest request, Class clazz) {
        StringBuilder sql = new StringBuilder();
        if (count) {
            sql.append(" SELECT count(emp) AS total");
        } else {
            sql.append("SELECT  " +
                    "ts.id as timeSheetId," +
                    " emp.id as employeeId," +
                    "emp.full_name AS fullName," +
                    "emp.knox_id AS knoxID," +
                    "pr.name AS projectName,\n" +
                    " \tCASE WHEN tmd.code = 'WFH' THEN CAST(ts.working_date AS TEXT) ELSE NULL END wfhDateList,\n" +
                    "\n" +
                    " \tCASE WHEN ts.overtime_from IS NOT NULL AND ts.overtime_to  IS NOT NULL\n" +
                    "\tTHEN concat(ts.working_date,': ', ts.overtime_from,' ~ ',ts.overtime_to ) ELSE NULL END otDateList,\n" +
                    "\tCASE WHEN tmd.code = 'COMPENSATORY_LEAVE'\n" +
                    "\tTHEN concat(ts.working_date,':',ts.missing_minutes) ELSE NULL END compensatoryDateList,\n" +
                    "    CASE WHEN tmd.code = 'LATE_COMING'\n" +
                    "\t\t\t\tTHEN concat(ts.working_date,' ',tmd.name,' ',ts.late_coming_minutes,' minutes')\n" +
                    "\t\t\t\tWHEN tmd.code = 'EARLY_LEAVING'\n" +
                    "\t\t\t\tTHEN concat(ts.working_date,' ',tmd.name,' ',ts.early_leaving_minutes,' minutes')\n" +
                    "\t\t\t\tWHEN tmd.code = 'LATE_COMING_EARLY_LEAVING'\n" +
                    "\t\t\t\tTHEN concat(ts.working_date,' ',\n" +
                    "\t\t\t\t'Late coming ',ts.late_coming_minutes,' minutes',', Early leaving ',ts.early_leaving_minutes,' minutes')\n" +
                    "\t \t\tELSE NULL END abnormalLeaveList,\n" +
                    "\n" +
                    " \tCASE WHEN ts.missing_minutes >= 240 OR tmd.code = 'ABSENT'\n" +
                    "\tTHEN concat(ts.working_date,\n" +
                    "\t\tCASE WHEN ts.missing_minutes >=240 AND ts.missing_minutes < 480 THEN '(0.5)' ELSE '' END)\n" +
                    "\tELSE NULL END dayOffList,\n" +
                    "ts.notes\n");
        }
        sql.append(" FROM employee emp \n" +
                "JOIN timesheet ts ON emp.id = ts.employee_id\n" +
                "LEFT JOIN project pr ON pr.id = emp.project_id\n" +
                "LEFT JOIN master_data tmd ON ts.working_type = tmd.id WHERE 1=1");
        Map<String, Object> params = new HashMap<>();
        if (!ObjectUtils.isNullorEmpty(request.getStartDate())) {
            sql.append(" AND ts.working_date >= :startDate ");
            params.put("startDate", request.getStartDate());
        }
        if (!ObjectUtils.isNullorEmpty(request.getEndDate())) {
            sql.append(" AND ts.working_date <= :endDate ");
            params.put("endDate", request.getEndDate());
        }
        sql.append(" AND emp.id  = :employeeId");
        sql.append(" AND ts.deleted_at is null");
        params.put("employeeId", request.getEmployeeId());

        Query query = getEntityManager().createNativeQuery(sql.toString(), clazz.getSimpleName());
        initQueryParams(query, params);
        return query;
    }

    @Override
    public List<TimeSheetDTO> getTimeSheetListGroupByUserId(LocalDate fromDate, LocalDate toDate, List<Integer> userIds, Boolean isSendAll) {
        Map<String, Object> params = new HashMap<>();
        List<TimeSheetDTO> result;
        StringBuilder sqlSelect = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        sqlSelect.append("select t.working_date workingDate, to_char(t.working_date, 'DD/MM/YYYY') workingDateStr, e.full_name fullName, e.id userId, e.du du, e.ldap ldap, p.name project, to_char(t.time_in, 'HH24:MI') timeIn, to_char(t.time_out, 'HH24:MI') timeOut, e.email email, t.working_type workingType, " +
                "  m.name exception  ");
        if (Boolean.TRUE.equals(isSendAll)) {
            sql.append(" from timesheet t " +
                    " left join employee e on e.id = t.employee_id " +
                    " left join project p on p.id = e.project_id " +
                    " left join master_data m on m.id = t.working_type " +
                    " where t.working_date between :fromDate and :toDate " +
                    " order by e.id, t.working_date ");
        } else {
            sql.append(" from timesheet t " +
                    " left join employee e on e.id = t.employee_id " +
                    " left join project p on p.id = e.project_id " +
                    " left join master_data m on m.id = t.working_type " +
                    " where e.id in :userIds and t.working_date between :fromDate and :toDate " +
                    " order by e.id, t.working_date ");
            params.put("userIds", userIds);
        }
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);

        Query query = getEntityManager().createNativeQuery(sqlSelect.toString() + sql.toString(), "TimeSheetDTO");
        initQueryParams(query, params);
        result = query.getResultList();
        return result;
    }

    @Override
    public List<TimeSheetViewDetailResponse> getListTimeSheetDetailByUserId(TimeSheetViewDetailRequest request) {
        Query query = this.buildQuerySelectDetailTimeSheetByUserId(false, request, TimeSheetViewDetailResponse.class);
        initPaging(query, request.getPage(), request.getPageSize());
        return query.getResultList();
    }
}
