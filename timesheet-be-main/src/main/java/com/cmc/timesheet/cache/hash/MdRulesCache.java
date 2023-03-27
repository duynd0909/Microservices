package com.cmc.timesheet.cache.hash;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("md_rules")
@ToString
public class MdRulesCache {
    @Id
    @Indexed
    private Integer id;

    private String name;

    private String description;

    private String workingTimeIn;

    private String workingTimeOut;

    private Integer lateLimit;

    private Integer minuteLimit;

    private Integer totalMinuteLimit;

    private Integer ruleType;

    private Integer blockId;

}
