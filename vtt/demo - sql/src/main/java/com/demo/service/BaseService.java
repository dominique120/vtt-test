package com.demo.service;

import java.util.List;

public interface BaseService<K, L, U> {

    void create(K entity);

    void update(K entity);

    void deleteById(L id, U userName);

    List<K> findAll();

    K findById(L id);
}
