package com.cmc.timesheet.service;

import com.cmc.timesheet.entity.MasterDataEntity;

import java.util.List;

public interface MasterDataService {

    public MasterDataEntity getMasterData(String type, String code);

    public Integer getMasterDataId(String type, String code) throws Exception;

    public String getMasterDataName(String type, String code) throws Exception;

    public List<MasterDataEntity> getListMasterDataByType(String type);
}
