package com.cmc.timesheet.service;

import java.util.List;

public interface BaseService<S, T, ID> {
    T create(S request);

    T update(ID id, S request);

    void delete(ID id);

    List<T> findAll();

    T findById(ID id);
}
