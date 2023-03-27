package com.cmc.timesheet.cache.hash;

import com.cmc.timesheet.cache.base.BaseCache;
import com.cmc.timesheet.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("master_data")
@ToString
public class MasterDataCache{

    @Id
    @Indexed
    private Integer id;

    private String code;

    private String name;

    @Indexed
    private String type;
}