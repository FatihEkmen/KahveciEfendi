package com.fatih.core.dao;

import com.fatih.dbconnection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * BaseDao
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseDao<T> {

    private SessionFactory sessionFactory;

    private Session session;
    private Transaction transaction;

    private final Class<T> clazz;

    public BaseDao(Class<T> clazz) {
        this.clazz = clazz;
        sessionFactory = HibernateUtil.createSessionFactory();
        getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T merge(T target) {
        transaction = getCurrentSession().beginTransaction();
        target = (T) getCurrentSession().merge(target);
        transaction.commit();
        return target;
    }

    public void mergeAll(List<T> targetList) {
        transaction = getCurrentSession().beginTransaction();
        for (T t : targetList) {
            getCurrentSession().merge(t);
        }
        transaction.commit();
    }

    public void delete(T target) {
        transaction = getCurrentSession().beginTransaction();
        getCurrentSession().delete(target);
        transaction.commit();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<T> findAll() {
        return getCurrentSession().createCriteria(clazz).list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<T> findAllOrdered(Order order) {
        return getCurrentSession().createCriteria(clazz).addOrder(order).list();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public T findById(Serializable id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    protected Session getCurrentSession() {
        if (session == null){
            session = sessionFactory.openSession();
        }
        return session;
    }

}
