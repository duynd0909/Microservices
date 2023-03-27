package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.MasterDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterDataEntity, Integer> {
    List<MasterDataEntity> findByType(String type);
    MasterDataEntity findOneByTypeAndCode(String type, String code);
}
