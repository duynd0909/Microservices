package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.MdRulesBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MdRulesBlockRepository extends JpaRepository<MdRulesBlockEntity, Integer> {
}
