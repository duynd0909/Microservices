package com.cmc.timesheet.cache.service.impl;

import com.cmc.timesheet.cache.hash.MasterDataCache;
import com.cmc.timesheet.cache.repository.MasterDataCacheRepository;
import com.cmc.timesheet.cache.service.MasterDataCacheService;
import com.cmc.timesheet.entity.MasterDataEntity;
import com.cmc.timesheet.repository.MasterDataRepository;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MasterDataCacheServiceImpl implements MasterDataCacheService {
    @Autowired
    private MasterDataCacheRepository masterDataCacheRepository;

    @Autowired
    private MasterDataRepository masterDataRepository;

    private static Map<Integer, MasterDataCache> localCache = new HashMap<>();

    @Override
    public List<MasterDataCache> findAll() {
        return (List<MasterDataCache>) masterDataCacheRepository.findAll();
    }

    @Override
    public List<MasterDataCache> save(final List<MasterDataEntity> listMsEntities) {
        List<MasterDataCache> listResult = new ArrayList<>();
        try {
            listResult = StreamSupport.stream(masterDataCacheRepository.saveAll(
                                    ObjectMapperUtils.mapList(listMsEntities, MasterDataCache.class))
                            .spliterator(), false)
                    .collect(Collectors.toList());
            localCache = listResult.stream().collect(
                    Collectors.toMap(MasterDataCache::getId, item -> item));
            return listResult;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void initMasterDataCache() {
        List<MasterDataEntity> listMsData = masterDataRepository.findAll();
        save(listMsData);
    }
}
