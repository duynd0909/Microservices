package com.cmc.timesheet.cache.repository;

import com.cmc.timesheet.cache.hash.MasterDataCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataCacheRepository  extends CrudRepository<MasterDataCache, Integer>,
        QueryByExampleExecutor<MasterDataCache> {
    List<MasterDataCache> findByType(String type);
    MasterDataCache findOneByTypeAndCode(String type, String code);

}
