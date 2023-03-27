package com.cmc.timesheet.cache.repository;

import com.cmc.timesheet.cache.hash.MdRulesBlockCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MdRulesBlockCacheRepository extends CrudRepository<MdRulesBlockCache, Integer> {
}
