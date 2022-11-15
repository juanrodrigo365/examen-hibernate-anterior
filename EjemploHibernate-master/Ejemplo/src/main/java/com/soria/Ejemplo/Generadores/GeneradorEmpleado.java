package com.soria.Ejemplo.Generadores;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GeneradorEmpleado implements IdentifierGenerator{

	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// acá se pueden usar multiples formas de generación
		return System.currentTimeMillis();
	}

}
