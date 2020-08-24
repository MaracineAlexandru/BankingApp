package com.sda.dao;

import com.sda.model.UserAccount;
import com.sda.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

public class UserAccountDao extends GenericDao<UserAccount> {

    public boolean isUserNameUnique(String username){
        Session session= HibernateUtil.getSessionFactory().openSession();

        String select = "from UserAccount u where u.username= :parameter";

        Query query = session.createQuery(select);

        query.setParameter("parameter",username);

        List list = query.list();
        session.close();
        /*if(list.isEmpty()){
            return true;
        } else {
            return false;
        }*/

        return list.isEmpty();
    }

    public Optional<UserAccount> findByUsername(String username){
        Session session= HibernateUtil.getSessionFactory().openSession();

        String select = "from UserAccount u where u.username =:parametruDeInlocuit";

        Query query= session.createQuery(select);
        query.setParameter("parametruDeInlocuit",username);

        List<UserAccount> userAccountList = query.list();
        session.close();

        if(userAccountList.isEmpty()){
            return  Optional.empty();
        }
        return Optional.of(userAccountList.get(0));


    }
}
