package com.cmc.timesheet.model.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PagingQuery {

    public static final Integer DEFAULT_PAGE_NUMBER = 0;

    public static final Integer DEFAULT_PAGE_SIZE  = 20;

    private Integer pageNumber;

    private Integer pageSize;

    /**
     * get Page Query from param
     * @return Page query
     */
//    public Pageable getPageQuery() {
//        return new Pageable.ofSize(this.pageNumber, this.pageSize);
//    }
}
