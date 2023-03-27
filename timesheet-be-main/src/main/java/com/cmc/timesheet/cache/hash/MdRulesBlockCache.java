package com.cmc.timesheet.cache.hash;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("md_rules_block")
@ToString
public class MdRulesBlockCache {

    @Id
    @Indexed
    private Integer id;

    private Integer startMinute;

    private Integer blockMinute;

    private Integer calcMinute;
}
