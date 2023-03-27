package com.cmc.timesheet.service;

import com.cmc.timesheet.entity.EmployeeEntity;
import com.cmc.timesheet.model.request.EmployeeInsertRequest;
import com.cmc.timesheet.model.request.EmployeeRequest;
import com.cmc.timesheet.model.request.MdRulesRequest;
import com.cmc.timesheet.model.response.EmployeeDetailResponse;
import com.cmc.timesheet.model.response.EmployeeResponse;
import com.cmc.timesheet.model.response.MdRulesResponse;
import com.cmc.timesheet.model.response.ProjectFilterRespone;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EmployeeService extends BaseService<EmployeeRequest, EmployeeResponse, Integer> {

    EmployeeDetailResponse findDetailById(Integer id);


    /**
     * @param employeeEntityList
     * @return
     */
    Boolean multipleInsertEmployee(List<EmployeeEntity> employeeEntityList);

    /**
     * @param idList
     * @return
     */
    List<EmployeeEntity> findAllByIdIn(Set<Integer> idList);

    List<EmployeeResponse> findByNameContains(String name);

    Map<String,Object> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Boolean multipleInsertFromExelData(List<EmployeeInsertRequest> list);
}
