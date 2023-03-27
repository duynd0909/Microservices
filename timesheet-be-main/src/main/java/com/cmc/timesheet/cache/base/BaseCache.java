package com.cmc.timesheet.cache.base;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
@Getter
@Setter
@ToString
public class BaseCache {

    private String createdAt;

    private String deletedAt;
}
