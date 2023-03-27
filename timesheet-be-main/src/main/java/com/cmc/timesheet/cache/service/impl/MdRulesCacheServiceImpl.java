package com.cmc.timesheet.cache.service.impl;

import com.cmc.timesheet.cache.hash.MdRulesCache;
import com.cmc.timesheet.cache.repository.MdRulesCacheRepository;
import com.cmc.timesheet.cache.service.MdRulesCacheService;
import com.cmc.timesheet.repository.MdRulesRepository;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MdRulesCacheServiceImpl implements MdRulesCacheService {
    @Autowired
    MdRulesRepository repository;

    @Autowired
    MdRulesCacheRepository cacheRepository;

    public void initMdRulesCache() {
        List<MdRulesCache> mdRulesCacheList = ObjectMapperUtils.mapList(repository.findAll(), MdRulesCache.class);
        try {
            cacheRepository.saveAll(mdRulesCacheList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
