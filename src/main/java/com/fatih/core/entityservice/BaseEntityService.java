package com.fatih.core.entityservice;

import com.fatih.core.dao.BaseDao;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * BaseEntityService
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public abstract class BaseEntityService<E, D extends BaseDao<E>> {

    private Class<D> daoClazz;

    @Autowired
    D dao;

    protected BaseEntityService(Class<D> daoClazz) {
        this.daoClazz = daoClazz;
    }

    public D getDao() {
        return dao;
    }

    public E findById(long id) {
        return dao.findById(id);
    }

    public List<E> findAll() {
        return dao.findAll();
    }

    public List<E> findAllOrdered(Order order) {
        return dao.findAllOrdered(order);
    }

    public E save(E e) {
        return dao.merge(e);
    }

    public void delete(E e) {
        dao.delete(e);
    }

}
