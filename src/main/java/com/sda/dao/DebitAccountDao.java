package com.sda.dao;

import com.sda.model.DebitAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DebitAccountDao extends GenericDao<DebitAccount> {
    public void updateEntity(DebitAccount entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
    }
}
