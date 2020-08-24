package com.sda.util;


import com.sda.model.DebitAccount;
import com.sda.model.UserAccount;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;
public class HibernateUtil {
    private static SessionFactory sessionFactory = null;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            instantiateSessionFactory();
        }
        return sessionFactory;
    }
    public static void instantiateSessionFactory() {
        /*START BOILER PLATE CODE */
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/banking_app?useSSL=false");
        settings.put(Environment.USER, "root");
        settings.put(Environment.PASS, "ParolaMySQL");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL55Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        configuration.setProperties(settings);

        configuration.addAnnotatedClass(UserAccount.class);
        configuration.addAnnotatedClass(DebitAccount.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        /*END BOILER PLATE CODE*/
    }
}
