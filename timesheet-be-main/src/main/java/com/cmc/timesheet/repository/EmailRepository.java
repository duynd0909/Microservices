package com.cmc.timesheet.repository;

import com.cmc.timesheet.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {
    @Query(value = "select count(1) from email where user_id = ?1 and month = ?2 and year = ?3 and is_sent = true ", nativeQuery = true)
    Integer countByUserIdAndMonthAndIsSentIsTrue(Integer userId, Integer month, Integer year);
}
