package com.cmc.timesheet.controller;


import com.cmc.timesheet.entity.MasterDataEntity;
import com.cmc.timesheet.model.response.MasterDataResponse;
import com.cmc.timesheet.service.impl.MasterDataServiceImpl;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common-code")
public class MasterDataController {

    @Autowired
    private MasterDataServiceImpl masterDataService;

    @GetMapping("/get-master-data")
    public ResponseEntity<List<MasterDataResponse>> getListMasterData(@RequestParam("type") String type) {
        List<MasterDataEntity> masterDataEntityList = masterDataService.getListMasterDataByType(type);

        return ResponseEntity.ok(ObjectMapperUtils.mapList(masterDataEntityList, MasterDataResponse.class));
    }
}
