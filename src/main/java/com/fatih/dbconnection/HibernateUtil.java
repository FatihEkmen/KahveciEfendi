package com.fatih.dbconnection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * HibernateUtil
 *
 * @author M.Fatih Ekmen
 * @since 1.0.0
 */
public class HibernateUtil {

    public static SessionFactory createSessionFactory() {

        try {
            return new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

}
