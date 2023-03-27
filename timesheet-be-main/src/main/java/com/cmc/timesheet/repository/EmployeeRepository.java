package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.EmployeeEntity;
import com.cmc.timesheet.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    /**
     * @param accountIds
     * @return
     */
    List<EmployeeEntity> findAllByIdIn(Set<Integer> accountIds);

    List<EmployeeEntity> findByNameContains(String name);


    @Query("select emp  from EmployeeEntity emp  where emp.projectId = :projectId" +
            " and lower(emp.fullName) like lower( :fullName) ")
    Page<EmployeeEntity> findByProjectIdAndByFullNameContaining(@Param("projectId") Integer projectId, @Param("fullName") String fullName,Pageable pageable);

    Page<EmployeeEntity> findByFullNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT lower(emp.ldap) FROM EmployeeEntity emp")
    Set<String> findAllLdap();
}
