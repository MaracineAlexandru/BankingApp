package com.sda.dao;

import com.sda.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
public class GenericDao<T> {
    protected SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public void saveEntity(T... entityArray) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (T entity : entityArray) {
            session.save(entity);
        }
        transaction.commit();
        session.close();
    }
    public T findById(Class<T> entityClass, int id) {
        Session session = sessionFactory.openSession();
        T elementById = session.find(entityClass, id);
        session.close();
        return elementById;
    }
}
