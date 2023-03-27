package com.cmc.timesheet.cache;

import com.cmc.timesheet.cache.service.MasterDataCacheService;
import com.cmc.timesheet.cache.service.MdRulesBlockCacheService;
import com.cmc.timesheet.cache.service.MdRulesCacheService;
import com.cmc.timesheet.cache.utils.RedisUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitCache {

    @Autowired
    private MasterDataCacheService masterDataCacheService;

    @Autowired
    private MdRulesCacheService mdRulesCacheService;

    @Autowired
    private MdRulesBlockCacheService mdRulesBlockCacheService;
    @Autowired
    private RedisUtils redisUtils;

    @PostConstruct
    public void init() {
        redisUtils.clearAll();
        masterDataCacheService.initMasterDataCache();
        masterDataCacheService.findAll();
        mdRulesCacheService.initMdRulesCache();
        mdRulesBlockCacheService.initMdRulesBlockCache();
    }
}
