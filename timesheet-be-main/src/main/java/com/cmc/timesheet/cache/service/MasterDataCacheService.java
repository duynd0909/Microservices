package com.cmc.timesheet.cache.service;

import com.cmc.timesheet.cache.hash.MasterDataCache;
import com.cmc.timesheet.entity.MasterDataEntity;

import java.util.List;

public interface MasterDataCacheService {
    /**
     * Fina all master data cache
     * @return
     */
    List<MasterDataCache> findAll();

    /**
     * init data to hash table
     */
    void initMasterDataCache();

    /**
     *
     * @param listMsEntities
     * @return
     */
    List<MasterDataCache> save(final List<MasterDataEntity> listMsEntities);
}
