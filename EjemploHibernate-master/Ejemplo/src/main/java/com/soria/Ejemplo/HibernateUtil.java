package com.soria.Ejemplo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = obtenerSessionFactory();

	private static SessionFactory obtenerSessionFactory() {
		SessionFactory sf = null;
		try {
			return new Configuration().configure().buildSessionFactory();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sf;
	}

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
	
}
