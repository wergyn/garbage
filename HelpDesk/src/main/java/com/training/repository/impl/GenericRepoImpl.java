package com.training.repository.impl;

import com.training.repository.GenericRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public abstract class GenericRepoImpl<E, K extends Serializable> implements GenericRepo<E, K> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Class<? extends E> repoType;

    public GenericRepoImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        repoType = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public K save(E entity) {
        return (K) currentSession().save(entity);
    }

    @Override
    public void update(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void remove(E entity) {
        currentSession().delete(entity);
    }

    @Override
    public E findById(K id) {
        return currentSession().get(repoType, id);
    }

    @Override
    public List<E> findAll() {
        return currentSession().createCriteria(repoType).list();
    }
}

