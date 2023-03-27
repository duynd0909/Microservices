package com.cmc.timesheet.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private Integer month;
    private Integer year;
    private Boolean isSendAll;
    private List<Integer> userIds;
}
