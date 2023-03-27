package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {


    List<FileEntity> findByTimeSheetId(@Param("timeSheetId") String timeSheetId);
}
