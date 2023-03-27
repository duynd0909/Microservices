package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.cache.hash.MasterDataCache;
import com.cmc.timesheet.cache.repository.MasterDataCacheRepository;
import com.cmc.timesheet.entity.MasterDataEntity;
import com.cmc.timesheet.model.response.MasterDataResponse;
import com.cmc.timesheet.repository.MasterDataRepository;
import com.cmc.timesheet.service.MasterDataService;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import com.cmc.timesheet.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    MasterDataRepository masterDataRepository;

    @Autowired
    MasterDataCacheRepository masterDataCacheRepository;

    public MasterDataEntity getMasterData(String type, String code) {
        try {
            MasterDataCache masterDataCache = masterDataCacheRepository.findOneByTypeAndCode(type, code);
            if (ObjectUtils.isNotNullorEmpty(masterDataCache))
                return ObjectMapperUtils.map(masterDataCache, MasterDataEntity.class);
            else
                return masterDataRepository.findOneByTypeAndCode(type, code);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MasterDataEntity> getListMasterDataByType(String type) {
        List<MasterDataCache> masterDataCacheList = masterDataCacheRepository.findByType(type);

        if (masterDataCacheList.size() > 0)
            return ObjectMapperUtils.mapList(masterDataCacheList, MasterDataEntity.class);
        else
            return masterDataRepository.findByType(type);
    }

    public Integer getMasterDataId(String type, String code) throws Exception{
        MasterDataEntity entity = getMasterData(type, code);
        if (ObjectUtils.isNullorEmpty(entity)) throw new Exception("Please contact administrator");
        return entity.getId();
    }

    public String getMasterDataName(String type, String code) throws Exception{
        MasterDataEntity entity = getMasterData(type, code);
        if (ObjectUtils.isNullorEmpty(entity)) throw new Exception("Please contact administrator");
        return entity.getName();
    }

    public List<MasterDataResponse> getListMasterData() {
        List<MasterDataEntity> list = masterDataRepository.findAll();
        List<MasterDataResponse> masterDataResponse = ObjectMapperUtils.mapList(list,MasterDataResponse.class);
        return masterDataResponse;
    }
}
