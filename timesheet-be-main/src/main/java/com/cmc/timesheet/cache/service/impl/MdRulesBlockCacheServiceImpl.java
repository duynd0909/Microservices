package com.cmc.timesheet.cache.service.impl;

import com.cmc.timesheet.cache.hash.MdRulesBlockCache;
import com.cmc.timesheet.cache.repository.MdRulesBlockCacheRepository;
import com.cmc.timesheet.cache.service.MdRulesBlockCacheService;
import com.cmc.timesheet.repository.MdRulesBlockRepository;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MdRulesBlockCacheServiceImpl implements MdRulesBlockCacheService {
    @Autowired
    MdRulesBlockRepository repository;

    @Autowired
    MdRulesBlockCacheRepository cacheRepository;

    public void initMdRulesBlockCache() {
        List<MdRulesBlockCache> mdRulesBlockCacheList = ObjectMapperUtils.mapList(repository.findAll(), MdRulesBlockCache.class);
        try {
            cacheRepository.saveAll(mdRulesBlockCacheList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
