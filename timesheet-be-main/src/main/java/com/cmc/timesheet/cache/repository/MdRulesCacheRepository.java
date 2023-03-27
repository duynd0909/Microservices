package com.cmc.timesheet.cache.repository;

import com.cmc.timesheet.cache.hash.MdRulesCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MdRulesCacheRepository extends CrudRepository<MdRulesCache, Integer> {
    List<MdRulesCache> findAll();
}
