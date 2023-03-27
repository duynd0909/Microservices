package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.MdRulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MdRulesRepository extends JpaRepository<MdRulesEntity, Integer> {
  
}
