package com.training.repository;

import java.util.List;

public interface GenericRepo<E, K> {
    K save(E entity);

    void update(E entity);

    void remove(E entity);

    E findById(K id);

    List<E> findAll();
}