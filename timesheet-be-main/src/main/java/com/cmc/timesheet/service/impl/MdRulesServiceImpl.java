package com.cmc.timesheet.service.impl;

import com.cmc.timesheet.cache.hash.MdRulesCache;
import com.cmc.timesheet.cache.repository.MdRulesCacheRepository;
import com.cmc.timesheet.constants.MasterDataConstant;
import com.cmc.timesheet.constants.MdRulesConstant;
import com.cmc.timesheet.entity.MdRulesBlockEntity;
import com.cmc.timesheet.entity.MdRulesEntity;
import com.cmc.timesheet.model.request.MdRulesBlockRequest;
import com.cmc.timesheet.model.request.MdRulesRequest;
import com.cmc.timesheet.model.response.MdRulesResponse;
import com.cmc.timesheet.model.vo.TimeSheetMdRuleVo;
import com.cmc.timesheet.repository.MdRulesBlockRepository;
import com.cmc.timesheet.repository.MdRulesRepository;
import com.cmc.timesheet.service.MasterDataService;
import com.cmc.timesheet.service.MdRulesService;
import com.cmc.timesheet.utils.DateTimeUtils;
import com.cmc.timesheet.utils.ObjectMapperUtils;
import com.cmc.timesheet.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.OffsetTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class MdRulesServiceImpl implements MdRulesService {

    @Autowired
    MdRulesRepository mdRulesRepository;

    @Autowired
    MdRulesCacheRepository mdRulesCacheRepository;
    
    @Autowired
    MdRulesBlockRepository mdRulesBlockRepository;

    @Autowired
    MasterDataService masterDataService;

    @Override
    @Transactional
    public MdRulesResponse create(MdRulesRequest request) {
        MdRulesEntity mdRulesEntity = ObjectMapperUtils.map(request, MdRulesEntity.class);
        MdRulesBlockRequest mdRulesBlockRequest = request.getMdRulesBlockRequest();

        if (ObjectUtils.isNotNullorEmpty(mdRulesBlockRequest)) {
            MdRulesBlockEntity rulesBlockEntity = findOneMdRulesBlock(mdRulesBlockRequest);
            mdRulesEntity.setBlockId(rulesBlockEntity.getId());
        }
        mdRulesRepository.save(mdRulesEntity);
        mdRulesCacheRepository.save(ObjectMapperUtils.map(mdRulesEntity, MdRulesCache.class));
        return ObjectMapperUtils.map(mdRulesEntity, MdRulesResponse.class);
    }

    @Override
    @Transactional
    public MdRulesResponse update(Integer id, MdRulesRequest request) {
        MdRulesEntity mdRulesEntity = ObjectMapperUtils.map(request, MdRulesEntity.class);
        MdRulesBlockRequest mdRulesBlockRequest = request.getMdRulesBlockRequest();

        if (ObjectUtils.isNotNullorEmpty(mdRulesBlockRequest)) {
            MdRulesBlockEntity rulesBlockEntity = findOneMdRulesBlock(mdRulesBlockRequest);
            mdRulesEntity.setBlockId(rulesBlockEntity.getId());
        }
        mdRulesRepository.save(mdRulesEntity);
        mdRulesCacheRepository.save(ObjectMapperUtils.map(mdRulesEntity, MdRulesCache.class));
        return ObjectMapperUtils.map(mdRulesEntity, MdRulesResponse.class);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        mdRulesRepository.deleteById(id);
        mdRulesCacheRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<MdRulesResponse> findAll() {
        List<MdRulesCache> cachedResult = mdRulesCacheRepository.findAll();
        if (cachedResult.size() > 0) return ObjectMapperUtils.mapList(cachedResult, MdRulesResponse.class);

        List<MdRulesEntity> pagedResult = mdRulesRepository.findAll();
        List<MdRulesResponse> mappedResult = ObjectMapperUtils.mapList(pagedResult, MdRulesResponse.class);
//        mappedResult.forEach(item -> {
//            if (item.getRuleType() == 2) {
//                item.setMdRulesBlockResponse(
//                    ObjectMapperUtils.map(
//                        mdRulesBlockRepository.getReferenceById(item.getBlockId()),
//                        MdRulesBlockResponse.class
//                    )
//                );
//            }
//        });
        return mappedResult;
    }

    @Override
    public MdRulesResponse findById(Integer id) {
        MdRulesCache cachedEntity = mdRulesCacheRepository.findById(id).orElse(null);
        if (ObjectUtils.isNotNullorEmpty(cachedEntity)) return ObjectMapperUtils.map(cachedEntity, MdRulesResponse.class);
        MdRulesEntity entity = mdRulesRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return ObjectMapperUtils.map(entity, MdRulesResponse.class);
    }

    public Integer getWorkingType(Time timeIn, Time timeOut, Integer ruleId) throws Exception {
        MdRulesEntity entity = mdRulesRepository.findById(ruleId).orElse(null);

        if (ObjectUtils.isNullorEmpty(entity)) throw new Exception(MdRulesConstant.MSG_RULE_EMPTY);

        OffsetTime workingTimeIn = entity.getWorkingTimeIn();
        OffsetTime workingTimeOut = entity.getWorkingTimeOut();
        if (ObjectUtils.isNullorEmpty(timeIn) || ObjectUtils.isNullorEmpty(timeOut))
            return masterDataService.getMasterDataId(MasterDataConstant.WORKING_TYPE, "ABSENT");
        Integer blockId = entity.getBlockId();

        int lateMinute  =   ObjectUtils.isNullorEmpty(blockId) ?
                            getTimeLate(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn) :
                            getTimeLateByBlock(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn, blockId);

        int earlyMinute = getTimeEarly(DateTimeUtils.convertTimeToOffsetTime(timeOut), workingTimeOut);
        if (lateMinute > 0 && earlyMinute > 0)
            return masterDataService.getMasterDataId(MasterDataConstant.WORKING_TYPE, "LATE_COMING_EARLY_LEAVING");
        else if (lateMinute > 0)
            return masterDataService.getMasterDataId(MasterDataConstant.WORKING_TYPE, "LATE_COMING");
        else if (earlyMinute > 0)
            return masterDataService.getMasterDataId(MasterDataConstant.WORKING_TYPE, "EARLY_LEAVING");
        else
            return masterDataService.getMasterDataId(MasterDataConstant.WORKING_TYPE, "NORMAL");
    }

    private Integer getTimeLate(OffsetTime timeIn, OffsetTime workingTimeIn) throws Exception {
        if (ObjectUtils.isNullorEmpty(timeIn)) throw new Exception(MdRulesConstant.MSG_INVALID_CONVERT_TIME);
        int diffMinute = Long.valueOf(workingTimeIn.until(timeIn, ChronoUnit.MINUTES)).intValue();
        return Math.max(diffMinute, 0);
    }

    private Integer getTimeLateByBlock(OffsetTime timeIn, OffsetTime workingTimeIn, Integer blockId) throws Exception {
        MdRulesBlockEntity entity = mdRulesBlockRepository.findById(blockId).orElse(null);

        if (ObjectUtils.isNullorEmpty(entity)) throw new Exception(MdRulesConstant.MSG_RULE_BLOCK_EMPTY);

        Integer blockMinute = entity.getBlockMinute();
        Integer startMinute = entity.getStartMinute();
        Integer calcMinute = entity.getCalcMinute();

        return ((getTimeLate(timeIn, workingTimeIn) - startMinute) / blockMinute + 1) * calcMinute;
    }

    private Integer getTimeEarly(OffsetTime timeOut, OffsetTime workingTimeOut) throws Exception {
        if (ObjectUtils.isNullorEmpty(timeOut)) throw new Exception(MdRulesConstant.MSG_INVALID_CONVERT_TIME);
        int diffMinute = Long.valueOf(timeOut.until(workingTimeOut, ChronoUnit.MINUTES)).intValue();
        return Math.max(diffMinute, 0);
    }

    public Integer getMissingMinute(Time timeIn, Time timeOut, Integer ruleId) throws Exception {
        MdRulesEntity entity = mdRulesRepository.findById(ruleId).orElse(null);

        if (ObjectUtils.isNullorEmpty(entity))
            throw new Exception(MdRulesConstant.MSG_RULE_EMPTY);
        if (ObjectUtils.isNullorEmpty(timeIn) || ObjectUtils.isNullorEmpty(timeOut))
            throw new Exception(MdRulesConstant.MSG_INVALID_TIME_IN_OUT);

        OffsetTime workingTimeIn = entity.getWorkingTimeIn();
        OffsetTime workingTimeOut = entity.getWorkingTimeOut();
        Integer blockId = entity.getBlockId();

        return (ObjectUtils.isNullorEmpty(blockId)) ?
            getTimeLate(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn)
            + getTimeEarly(DateTimeUtils.convertTimeToOffsetTime(timeOut), workingTimeOut)
                :
            getTimeLateByBlock(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn, blockId)
            + getTimeEarly(DateTimeUtils.convertTimeToOffsetTime(timeOut), workingTimeOut);
    }

    /**
     *
     * @param timeIn
     * @param timeOut
     * @param ruleId
     * @return
     * @throws Exception
     */
    @Override
    public TimeSheetMdRuleVo getTimesheetMdRuleVo(Time timeIn, Time timeOut, Integer ruleId) throws Exception {

        MdRulesEntity entity = mdRulesRepository.findById(ruleId).orElse(null);

        if (ObjectUtils.isNullorEmpty(entity))
            throw new Exception(MdRulesConstant.MSG_RULE_EMPTY);

        OffsetTime workingTimeIn = entity.getWorkingTimeIn();
        OffsetTime workingTimeOut = entity.getWorkingTimeOut();
        Integer blockId = entity.getBlockId();

        OffsetTime offsetTimeIn = DateTimeUtils.convertTimeToOffsetTime(timeIn);
        OffsetTime offsetTimeOut = DateTimeUtils.convertTimeToOffsetTime(timeOut);

        if (ObjectUtils.isNullorEmpty(offsetTimeIn) && ObjectUtils.isNullorEmpty(offsetTimeOut)) {
            TimeSheetMdRuleVo resultVo = new TimeSheetMdRuleVo();
            resultVo.setLateMinute(480);
            resultVo.setEarlyMinute(0);
            resultVo.setMissingMinute(480);
            resultVo.setWorkingType(getWorkingType(timeIn, timeOut,entity.getId()));

            return resultVo;
        } else if (ObjectUtils.isNullorEmpty(offsetTimeOut)) {
            Integer lateMinute  =   ObjectUtils.isNullorEmpty(blockId) ?
                    getTimeLate(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn) :
                    getTimeLateByBlock(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn, blockId);

            TimeSheetMdRuleVo resultVo = new TimeSheetMdRuleVo();
            resultVo.setLateMinute(lateMinute);
            resultVo.setEarlyMinute(0);
            resultVo.setMissingMinute(lateMinute);
            resultVo.setWorkingType(getWorkingType(timeIn, timeOut, entity.getId()));

            return resultVo;
        } else {
            Integer missingMinute = getMissingMinute(timeIn, timeOut, entity.getId());
            Integer lateMinute  =   ObjectUtils.isNullorEmpty(blockId) ?
                    getTimeLate(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn) :
                    getTimeLateByBlock(DateTimeUtils.convertTimeToOffsetTime(timeIn), workingTimeIn, blockId);
            Integer earlyMinute = getTimeLate(DateTimeUtils.convertTimeToOffsetTime(timeOut), workingTimeOut);

            TimeSheetMdRuleVo resultVo = new TimeSheetMdRuleVo();
            resultVo.setLateMinute(lateMinute);
            resultVo.setEarlyMinute(earlyMinute);
            resultVo.setMissingMinute(missingMinute);
            resultVo.setWorkingType(getWorkingType(timeIn, timeOut,entity.getId()));

            return resultVo;
        }
    }

    /**
     *
     * @param mdRulesBlockRequest
     * @return rulesBlockEntity
     * Find record in Md Rules Block
     *      If record is found, return the record
     *      If record is not existed, create new and return the record
     */
    private MdRulesBlockEntity findOneMdRulesBlock(MdRulesBlockRequest mdRulesBlockRequest) {
        MdRulesBlockEntity mdRulesBlock = ObjectMapperUtils.map(mdRulesBlockRequest, MdRulesBlockEntity.class);
        Example<MdRulesBlockEntity> example = Example.of(mdRulesBlock);
        Optional<MdRulesBlockEntity> optional = mdRulesBlockRepository.findOne(example);
        MdRulesBlockEntity rulesBlockEntity = optional.orElse(null);

        if (ObjectUtils.isNullorEmpty(rulesBlockEntity)) {
            rulesBlockEntity = mdRulesBlockRepository.save(mdRulesBlock);
        }
        return rulesBlockEntity;
    }
}