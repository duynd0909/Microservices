package com.cmc.timesheet.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MasterDataResponse {

    private Integer id;
    private String code;

    private String name;
}
