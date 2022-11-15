package com.soria.Ejemplo;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.soria.Ejemplo.Modelo.Empleado;
import com.soria.Ejemplo.Modelo.EmpleadoDetalles;
import com.soria.Ejemplo.Modelo.EmpleadoSimple;
import com.soria.Ejemplo.Modelo.EmpleadoVentaJoin;
import com.soria.Ejemplo.Modelo.FuncionesGroupBy;
import com.soria.Ejemplo.Modelo.Operador;
import com.soria.Ejemplo.Modelo.Producto;
import com.soria.Ejemplo.Modelo.Revision;
import com.soria.Ejemplo.Modelo.Tecnico;
import com.soria.Ejemplo.Modelo.TecnicoOperadorEncargadoJoin;
import com.soria.Ejemplo.Modelo.Vehiculo;
import com.soria.Ejemplo.Modelo.Venta;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException
    {
    	System.out.println("\nPregunta: a) mostrar los 3 vehículos más nuevos\n");
    	vehiculosMasNuevos("ASC", 3);
    	System.out.println("\nPregunta b) mostrar a todos los tecnicos ordenados descendentemente por su contacto (es su número de contacto - teléfono)\n");
    	tecnicosOrdenadosDesc("ASC");
    	System.out.println("\nPregunta c) mostrar todos los registro de revision de la siguiente manera:\n");
    	revisionJoin();
    	
    	//registroOperador();
    	//lecturaOperador();
    	//getOperadorRevision();
    	
    	
        //ejemploRegistro();
        //ejemploLecturaEmpleados();
        //ejemploLecturaProyeccion();
        //ejemploWhere();
        //ejemploOrderBy();
    	//ejemploOrderByCompuesto();
    	//ejemploOrderByCompuesto2();
    	//ejemploOrderByCompuesto3();
    	//ejemploGroupBy();
    	//ejemploParametros(90);
    	//ejemploUpdate(2, "Anna");
    	//ejemploDelete(92);
    	//ejemploPaginacion();
    	
    	//ejemploRelacion();
    	//ejemploJoin();
    	//ejemploFunciones();
    	//ejemploFuncionesConParametros(5, 77);
    	//ejemploCriteriaQuery();
    	//ejemploCriteriaQueryConCondiciones(60);
    	//ejemploCriteriaQueryBetween(5, 90);
    	//ejemploCriteriaQueryLike();
    	//ejemploCriteriaQueryOrderBy("ASC");
    	//ejemploCriteriaQueryOrderByLimit("DESC", 3);
    	//ejemploCriteriaQueryProyeccion();
    	//ejemploCriteriaQueryInnerJoin();
    	//ejemploCriteriaQueryGroupBy();
    	//ejemploCriteriaQueryFuncionesAgregacion();
    	
    	//ejemploAlcanceDatosUnoMuchos();
    	//ejemploAlcanceDatosMuchosUno();
    	//ejemploAlcanceDatosUnoUno();
    	//ejemploAlcanceDatosMuchosMuchos();
    	//ejemploAlcanceDatosInsertUnoUno();
    }
    
    private static void vehiculosMasNuevos(String tipo, int maximo) {
		//SELECT * FROM tecnico ORDER BY contacto;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Vehiculo> consulta = builder.createQuery(Vehiculo.class);
    		Root<Vehiculo> raiz = consulta.from(Vehiculo.class);
    		if(tipo.equals("DESC"))
    			consulta.select(raiz).orderBy(builder.desc(raiz.get("odometro")));
    		else
    			consulta.select(raiz).orderBy(builder.asc(raiz.get("odometro")));
    		
    		List<Vehiculo> emps = sesion.createQuery(consulta).setMaxResults(maximo).getResultList();
    		for(Vehiculo ve : emps) {
    			System.out.println("Odometro: " + ve.getOdometro() + ", Placa: "+ ve.getPlaca() + ", Modelo: " + ve.getModelo() + ", Tipo: " + ve.getTipo());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
    
    private static void tecnicosOrdenadosDesc(String tipo) {
		//SELECT * FROM tecnico ORDER BY contacto;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Tecnico> consulta = builder.createQuery(Tecnico.class);
    		Root<Tecnico> raiz1 = consulta.from(Tecnico.class);
    		if(tipo.equals("DESC"))
    			consulta.select(raiz1).orderBy(builder.desc(raiz1.get("contacto")));
    		else
    			consulta.select(raiz1).orderBy(builder.asc(raiz1.get("contacto")));
    		
    		List<Tecnico> emps = sesion.createQuery(consulta).getResultList();
    		for(Tecnico te : emps) {
    			System.out.println("Contacto: " + te.getContacto() + "; ID: "+ te.getId() +"; Nombre: " + te.getNombre() + ", Apellido: " + te.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
    
    private static void revisionJoin() {
		// SELECT t.id, t.nombre nombre_tecnico, t.apellido apellido_tecnico, v.placa, v.tipo, o.nombre nombre_operador, o.apellido apellido_operador, r.fecha_revision
    	//FROM revision r
    	//INNER JOIN tecnico t ON t.id = r.encargado
    	//INNER JOIN vehiculo v On v.placa = r.vehiculo
    	//INNER JOIN operador o on o.id = r.chofer
    	
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		String hql = "SELECT new com.soria.Ejemplo.Modelo.TecnicoOperadorEncargadoJoin(T.idtecnico,T.nom_tec,T.ap_tec,V.placa,V.tipo,O.nom_ope,O.ap_ope,R.fecha) "
    				+ "FROM Revision R, Tecnico T, Operador O, Vehiculo V"
    				+ "WHERE R.encargado = T, R.vehiculo = V, R.operador = O";
    		Query query = sesion.createQuery(hql);
    		List<TecnicoOperadorEncargadoJoin>  tec = query.getResultList();
    		System.out.println("Exito");
    		for(TecnicoOperadorEncargadoJoin em : tec) {
    			System.out.println("ID: " + em.getIdtecnico() + ", Nombre Tecnico: " + em.getNom_tec() + ", Apellido Tecnico: " + em.getAp_tec() + ", Placa: " + em.getPlaca()
    			 + ", Tipo: " + em.getTipo() + ", Nombre operador: " + em.getNom_ope() + ", Apellido Operador: " + em.getAp_ope() + ", Fecha Rev: " + em.getFecha());
    		}
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
    
    private static void registroOperador() throws ParseException {
    	Operador op = new Operador();
    	op.setNombre("Sarah");
    	op.setApellido("Jessika");
    	op.setCategoria_licencia("B");
    	Date f1 = new SimpleDateFormat("yyyy-mm-dd").parse("2024-05-11"); 
    	op.setFecha_vencimiento(f1);
    	Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		//Insertar un nuevo operador a la DB
    		int numero = (Integer)sesion.save(op);
    		//Realizamos el commit
    		tx.commit();
    		System.out.println( "Se registró con éxito, id:" + numero );
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
    
    private static void lecturaOperador() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		//List empleados = sesion.createQuery("FROM Empleado").list();//HQL
    		//List empleados = sesion.createQuery("FROM Empleado AS E").list();//Alias con AS
    		//List empleados = sesion.createQuery("FROM Empleado E").list();//Alias omitiendo AS
    		String hql = "FROM com.soria.Ejemplo.Modelo.Operador";
    		Query consulta = sesion.createQuery(hql);
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Operador op = (Operador)iterador.next();
    			System.out.println( "id:" +op.getId() );
    			System.out.println( "nombre:" + op.getNombre() );
    			System.out.println( "apellido:" + op.getApellido() );
    			System.out.println( "categoria_licencia:" + op.getCategoria_licencia() );
    			System.out.println( "FEcha Vencimiento:" + op.getFecha_vencimiento() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
    
    private static void getOperadorRevision() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		String hql = "FROM Operador WHERE id = 1";
    		Query query = sesion.createQuery(hql);
    		Operador op = (Operador) query.uniqueResult();
    		System.out.println("Exito recuperando al operador");
    		System.out.println(op.getNombre());
    		for(Iterator iterador = op.getRevisiones().iterator(); iterador.hasNext();) {
    			Revision r = (Revision) iterador.next();
    			System.out.println("Cantidad: " + r.getFecha_revision() + ", Chofer: " + r.getChofer());
    		}
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
    
    
    
    
    
    
    
    
    

	private static void ejemploLecturaEmpleados() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		//List empleados = sesion.createQuery("FROM Empleado").list();//HQL
    		//List empleados = sesion.createQuery("FROM Empleado AS E").list();//Alias con AS
    		//List empleados = sesion.createQuery("FROM Empleado E").list();//Alias omitiendo AS
    		String hql = "FROM com.soria.Ejemplo.Modelo.Empleado";
    		Query consulta = sesion.createQuery(hql);
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() );
    			System.out.println( "nombre:" + emp.getNombre() );
    			System.out.println( "apellido:" + emp.getApellido() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}

	private static void ejemploRegistro() {
		Empleado emp = new Empleado();
    	emp.setId(920);
    	emp.setNombre("Ricardo");
    	emp.setApellido("Sanchez");
    	
    	Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		//Insertar un nuevo empleado  a la DDBB
    		int numero = (Integer)sesion.save(emp);
    		//Realizamos el commit
    		tx.commit();
    		System.out.println( "Se registró con éxito, id:" + numero );
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	
	private static void ejemploLecturaProyeccion() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"SELECT new com.soria.Ejemplo.Modelo.EmpleadoSimple(E.id, E.apellido) FROM Empleado E");
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			EmpleadoSimple emp = (EmpleadoSimple)iterador.next();
    			System.out.println( "id:" + emp.getCodigo() );
    			System.out.println( "apellido:" + emp.getApellido() );
    			System.out.println( "------------------------------");
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploWhere() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"FROM Empleado E WHERE E.id < 50");
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() );
    			System.out.println( "apellido:" + emp.getApellido() );
    			System.out.println( "------------------------------");
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploOrderBy() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"FROM Empleado E ORDER BY E.apellido");
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() + ", apellido:" + emp.getApellido() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploOrderByCompuesto() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"FROM Empleado E ORDER BY E.apellido DESC, E.nombre ASC, E.id DESC");
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() + ",\t nombre:" + emp.getNombre() + ",\t apellido:" + emp.getApellido() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploOrderByCompuesto2() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"FROM Empleado E WHERE id < 50 ORDER BY E.apellido, E.id DESC");
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() + ",\t nombre:" + emp.getNombre() + ",\t apellido:" + emp.getApellido() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploOrderByCompuesto3() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"FROM Empleado E WHERE id < 50 ORDER BY E.apellido, E.id DESC");
    		List empleados = consulta.list();
    		
    		for(Iterator iterador = empleados.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() + ",\t nombre:" + emp.getNombre() + ",\t apellido:" + emp.getApellido() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploGroupBy() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"SELECT SUM(id) FROM Empleado E GROUP BY E.apellido");
    		List valores = consulta.list();
    		
    		for(Iterator iterador = valores.iterator(); iterador.hasNext();) {
    			long emp = (Long) iterador.next();
    			System.out.println( "sum:" + emp );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploParametros(int valor) {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"FROM Empleado E WHERE E.id < :un_id");
    		consulta.setParameter("un_id", valor);
    		List valores = consulta.list();
    		
    		for(Iterator iterador = valores.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println( "id:" + emp.getId() + ",\t nombre:" + emp.getNombre() + ",\t apellido:" + emp.getApellido() );
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploUpdate(int id, String nombre) {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Empleado emp = sesion.get(Empleado.class, id);
    		emp.setNombre(nombre);
    		sesion.update(emp);
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	
	private static void ejemploDelete(int id) {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Empleado emp = (Empleado)sesion.get(Empleado.class, id);
    		sesion.delete(emp);
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	
	private static void ejemploCUD_HQL() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"INSERT INTO Empleado(id, nombre, apellido) SELECT un_id, un_nombre, un_apellido FROM otro_objeto");
    		int respuesta = consulta.executeUpdate();
    		
    		Query consulta2 = sesion.createQuery(
        			"UPDATE Empleado SET apellido = :ap WHERE id = :e_id");
    		consulta2.setParameter("ap", "Rocha");
    		consulta2.setParameter("e_id", 77);
        	int respuesta2 = consulta2.executeUpdate();
        	
        	Query consulta3 = sesion.createQuery(
        			"DELETE FROM Empleado WHERE id = :e_id");
        	consulta3.setParameter("e_id", 90);
        	int respuesta3 = consulta3.executeUpdate();
        		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	
	private static void ejemplo_funciones_agregacion_HQL() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		Query consulta = sesion.createQuery(
    			"SELECT SUM(id) FROM Empleado");
    		Long resultado1 = (Long)consulta.getResultList().get(0);
    		
    		Query consulta2 = sesion.createQuery(
        		"SELECT AVG(id) FROM Empleado");
    		Double resultado2 = (Double)consulta2.getResultList().get(0);
        	
    		Query consulta3 = sesion.createQuery(
        		"SELECT MAX(id) FROM Empleado");
        	Query consulta4 = sesion.createQuery(
            	"SELECT MIN(id) FROM Empleado");
            BigDecimal resultado34 = (BigDecimal)consulta4.getResultList().get(0);
            	
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploPaginacion() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		String hql = "FROM Empleado";
    		Query query = sesion.createQuery(hql);
    		query.setFirstResult(2);
    		query.setMaxResults(4);
    		List resultado = query.list();
    		System.out.println("Exito");
    		for(Iterator iterador = resultado.iterator(); iterador.hasNext();) {
    			Empleado emp = (Empleado)iterador.next();
    			System.out.println("Empleado " + emp.getApellido() + " id: " + emp.getId());
    		}
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	
	private static void ejemploRelacion() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		String hql = "FROM Empleado WHERE id = 5";
    		Query query = sesion.createQuery(hql);
    		Empleado emp = (Empleado) query.uniqueResult();
    		System.out.println("Exito recuperando al empleado");
    		System.out.println(emp.getNombre());
    		for(Iterator iterador = emp.getVentas().iterator(); iterador.hasNext();) {
    			Venta v = (Venta) iterador.next();
    			System.out.println("Cantidad: " + v.getCantidad() + ", Prod: " + v.getIdprod());
    		}
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	
	private static void ejemploJoin() {
		// SELECT nombre, idprod, cantidad FROM venta v INNER JOIN empleado e ON e.id = v.idemp;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		String hql = "SELECT new com.soria.Ejemplo.Modelo.EmpleadoVentaJoin(E.nombre, V.idprod, V.cantidad) "
    				+ "FROM Venta V, Empleado E  WHERE V.empleado = E";
    		Query query = sesion.createQuery(hql);
    		List<EmpleadoVentaJoin>  empv= query.getResultList();
    		System.out.println("Exito");
    		for(EmpleadoVentaJoin em : empv) {
    			System.out.println("Nombre: " + em.getNombre() + ", Prod: " + em.getIdprod() + ", cantidad: " + em.getCantidad());
    		}
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
	}
	private static void ejemploFunciones() {
		//Procedimientos almacenados en MySQL
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		//Query query = sesion.createNativeQuery("{CALL prueba()}", Empleado.class);//MySQL
    		Query query = sesion.createNativeQuery("SELECT * FROM prueba()", Empleado.class);
    		List<Empleado> emps = query.getResultList();
    		for(Empleado em : emps) {
    			System.out.println("Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploFuncionesConParametros(int min, int max) {
		/*
PostgreSQL:
empresay=# CREATE OR REPLACE FUNCTION listar(menor INTEGER, mayor INTEGER)
empresay-# RETURNS TABLE (id INT, nombre VARCHAR, apellido VARCHAR)
empresay-# AS $$
empresay$# SELECT * FROM empleado WHERE id >= menor AND id <= mayor;
empresay$# $$
empresay-# LANGUAGE SQL;

MySQL:
DELIMITER $$
CREATE PROCEDURE listar(menor INTEGER, mayor INTEGER)
BEGIN
SELECT * FROM empleado WHERE id BETWEEN menor AND mayor;
END $$
DELIMITER;

		 * */
		
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		/*
    		//Con persistence:
    		StoredProcedureQuery spq = sesion.createStoredProcedureQuery("listar", Empleado.class);
    		spq.registerStoredProcedureParameter("menor", Integer.class, ParameterMode.IN);
    		spq.registerStoredProcedureParameter("mayor", Integer.class, ParameterMode.IN);
    		spq.setParameter("menor", min);
    		spq.setParameter("mayor", max);
    		List<Empleado> emps2 = spq.getResultList(); 
    		//...
    		*/
    		
    		//Query query = sesion.createNativeQuery("{CALL listar(?, ?)}", Empleado.class);//MySQL
    		Query query = sesion.createNativeQuery("SELECT * FROM listar(?,?)", Empleado.class);
    		query.setParameter(1, min);
    		query.setParameter(2, max);
    		List<Empleado> emps = query.getResultList();
    		for(Empleado em : emps) {
    			System.out.println("Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQuery() {
		//jakarta.persistence
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaQuery<Empleado> cq = sesion.getCriteriaBuilder().createQuery(Empleado.class);
    		cq.from(Empleado.class);
    		List<Empleado> emps = sesion.createQuery(cq).getResultList();
    		for(Empleado em : emps) {
    			System.out.println("Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryConCondiciones(int val) {
		//jakarta.persistence
		//SELECT * FROM empleado WHERE id >= 60;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Empleado> consulta = builder.createQuery(Empleado.class);
    		Root<Empleado> raiz = consulta.from(Empleado.class);
    		consulta.select(raiz).where(builder.ge((Expression)raiz.get("id"), val));
    		List<Empleado> emps = sesion.createQuery(consulta).getResultList();
    		for(Empleado em : emps) {
    			System.out.println("Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryBetween(int inicio, int fin) {
		//SELECT * FROM empleado WHERE id BETWEEN 5 AND 90;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Empleado> consulta = builder.createQuery(Empleado.class);
    		Root<Empleado> raiz = consulta.from(Empleado.class);
    		consulta.select(raiz).where(builder.between((Expression)raiz.get("id"), inicio, fin));
    		List<Empleado> emps = sesion.createQuery(consulta).getResultList();
    		for(Empleado em : emps) {
    			System.out.println("ID: "+ em.getId() +"; Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryLike() {
		//SELECT * FROM empleado WHERE apellido LIKE '%r%';
		//SELECT * FROM empleado WHERE apellido LIKE '___a';
		//SELECT * FROM empleado WHERE apellido LIKE '_o_a%';
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Empleado> consulta = builder.createQuery(Empleado.class);
    		Root<Empleado> raiz = consulta.from(Empleado.class);
    		consulta.select(raiz).where(builder.like((Expression)raiz.get("apellido"), "___a"));// "%r%"
    		List<Empleado> emps = sesion.createQuery(consulta).getResultList();
    		for(Empleado em : emps) {
    			System.out.println("ID: "+ em.getId() +"; Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryOrderBy(String tipo) {
		//SELECT * FROM empleado ORDER BY apellido;
		//SELECT * FROM empleado ORDER BY apellido DESC;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Empleado> consulta = builder.createQuery(Empleado.class);
    		Root<Empleado> raiz = consulta.from(Empleado.class);
    		if(tipo.equals("DESC"))
    			consulta.select(raiz).orderBy(builder.desc(raiz.get("apellido")));
    		else
    			consulta.select(raiz).orderBy(builder.asc(raiz.get("apellido")));
    		
    		List<Empleado> emps = sesion.createQuery(consulta).getResultList();
    		for(Empleado em : emps) {
    			System.out.println("ID: "+ em.getId() +"; Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryOrderByLimit(String tipo, int maximo) {
		//SELECT * FROM empleado ORDER BY apellido LIMIT 3;
		//SELECT * FROM empleado ORDER BY apellido DESC LIMIT 3;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Empleado> consulta = builder.createQuery(Empleado.class);
    		Root<Empleado> raiz = consulta.from(Empleado.class);
    		if(tipo.equals("DESC"))
    			consulta.select(raiz).orderBy(builder.desc(raiz.get("apellido")));
    		else
    			consulta.select(raiz).orderBy(builder.asc(raiz.get("apellido")));
    		
    		List<Empleado> emps = sesion.createQuery(consulta).setMaxResults(maximo).getResultList();
    		for(Empleado em : emps) {
    			System.out.println("ID: "+ em.getId() +"; Nombre: " + em.getNombre() + ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryProyeccion() {
		//SELECT id AS codigo, apellido FROM empleado;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<EmpleadoSimple> consulta = builder.createQuery(EmpleadoSimple.class);
    		Root<Empleado> raiz = consulta.from(Empleado.class);
    		consulta.select(builder.construct(EmpleadoSimple.class, raiz.get("id"), raiz.get("apellido")));
    		List<EmpleadoSimple> emps = sesion.createQuery(consulta).getResultList();
    		for(EmpleadoSimple em : emps) {
    			System.out.println("ID: "+ em.getCodigo()+ ", Apellido: " + em.getApellido());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryInnerJoin() {
		// SELECT E.nombre, V.idprod, V.cantidad FROM empleado E INNER JOIN venta V ON E.id = V.idemp;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<EmpleadoVentaJoin> consulta = builder.createQuery(EmpleadoVentaJoin.class);
    		Root<Venta> raiz = consulta.from(Venta.class);
    		raiz.join("empleado",JoinType.INNER);
    		consulta.select(builder.construct(
    				EmpleadoVentaJoin.class, 
    				raiz.get("empleado").get("nombre"),
    				raiz.get("idprod"),
    				raiz.get("cantidad")
    		));
    		
    		List<EmpleadoVentaJoin> emps = sesion.createQuery(consulta).getResultList();
    		for(EmpleadoVentaJoin em : emps) {
    			System.out.println("Nombre emp: "+ em.getNombre()+ ", Cantidad: " + em.getCantidad());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryGroupBy() {
		/*
		 * 	empresay=# SELECT idemp, COUNT(id), MAX(cantidad), MIN(cantidad), SUM(cantidad), AVG(cantidad)
			empresay-# FROM venta GROUP BY idemp;
		 * */
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<FuncionesGroupBy> consulta = builder.createQuery(FuncionesGroupBy.class);
    		Root<Venta> raiz = consulta.from(Venta.class);
    		consulta.groupBy(raiz.get("empleado").get("id"));
    		consulta.select(builder.construct(
    				FuncionesGroupBy.class, 
    				raiz.get("empleado").get("id"),
    				builder.count(raiz.get("id")),
    				builder.max((Expression)raiz.get("cantidad")),
    				builder.min((Expression)raiz.get("cantidad")),
    				builder.sum((Expression)raiz.get("cantidad")),
    				builder.avg((Expression)raiz.get("cantidad"))
    		));
    		
    		List<FuncionesGroupBy> emps = sesion.createQuery(consulta).getResultList();
    		for(FuncionesGroupBy em : emps) {
    			System.out.println("id: "+ em.getId()+ 
    					", Cantidad: " + em.getConteo()+ 
    					", Maximo: " + em.getMax()+ 
    					", Minimo: " + em.getMin()+ 
    					", Sumatoria: " + em.getSum()+ 
    					", Media: " + em.getMedia());
    		}
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploCriteriaQueryFuncionesAgregacion() {
		//empresay=# SELECT SUM(cantidad * 2) FROM venta;
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		
    		CriteriaBuilder builder = sesion.getCriteriaBuilder();
    		CriteriaQuery<Number> consulta = builder.createQuery(Number.class);
    		Root<Venta> raiz = consulta.from(Venta.class);
    		consulta.select(builder.sum(builder.prod(raiz.<Long>get("cantidad"), 2)));//acá variantes
    		Number valor = sesion.createQuery(consulta).getSingleResult();
    		System.out.println(valor);
    		tx.commit();
    		
    		/*Variantes:
    		 * consulta.select(builder.count(raiz.<Long>get("id")));
    		 * consulta.select(builder.max(raiz.<Long>get("cantidad")));
    		 * consulta.select(builder.avg(raiz.<Long>get("cantidad")));
    		 * */
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploAlcanceDatosUnoMuchos() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		Query consulta = sesion.createQuery("FROM Empleado WHERE id = :id");
    		consulta.setParameter("id", 5);
    		Empleado emp = (Empleado) consulta.uniqueResult();
    		tx.commit();
    		System.out.println("Exito");
    		for(Venta v : emp.getVentas())
    			System.out.println("IDventa: " + v.getId() + 
    					", cantidad: " + v.getCantidad() + ", Empleado: " + v.getEmpleado().getApellido());
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploAlcanceDatosMuchosUno() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		Query consulta = sesion.createQuery("FROM Venta WHERE id = :id");
    		consulta.setParameter("id", 112);
    		Venta v = (Venta) consulta.uniqueResult();
    		tx.commit();
    		System.out.println("Exito");
    		System.out.printf("VentaID %d: %s (%d)\n", v.getId(), v.getEmpleado().getApellido(), v.getCantidad());
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploAlcanceDatosUnoUno() {
		/*empresay=# CREATE TABLE empleado_detalles(
		empresay(#   id_e_det INT NOT NULL PRIMARY KEY,
		empresay(#   email VARCHAR(80) NOT NULL,
		empresay(#   genero VARCHAR(1),
		empresay(#   direccion VARCHAR(50),
		empresay(#   CONSTRAINT fk_empleado_det FOREIGN KEY (id_e_det)
		empresay(#   REFERENCES empleado(id)
		empresay(# );*/
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		Query consulta = sesion.createQuery("FROM Empleado WHERE id = :id");
    		consulta.setParameter("id", 5);
    		Empleado emp = (Empleado) consulta.uniqueResult();
    		tx.commit();
    		System.out.println("Exito");
    		System.out.printf("ID %d: %s (%s)\n", emp.getId(), emp.getApellido(), emp.getEmpleadoDetalles().getEmail());
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploAlcanceDatosMuchosMuchos() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		Query consulta = sesion.createQuery("FROM Empleado WHERE id = :id");
    		consulta.setParameter("id", 5);
    		Empleado emp = (Empleado) consulta.uniqueResult();
    		
    		//otro ejemplo
    		Query consulta2 = sesion.createQuery("FROM Producto WHERE id = :id");
    		consulta2.setParameter("id", 3);
    		Producto prod = (Producto) consulta2.uniqueResult();
    		tx.commit();
    		System.out.println("Exito");
    		System.out.printf("ID %d: %s (%s)\n", emp.getId(), emp.getApellido(), emp.getEmpleadoDetalles().getEmail());
    		for(Producto p : emp.getProductos()) {
    			System.out.printf("Producto: %s (Bs.%f)\n", p.getNombre(),p.getPrecio());
    		}
    		System.out.println("=================");
    		for(Empleado em : prod.getEmpleados()) {
    			System.out.printf("Empleado: %s (ID:%s)\n", em.getApellido(),em.getId());
    		}
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploAlcanceDatosInsertUnoUno() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		EmpleadoDetalles ed = new EmpleadoDetalles();
    		ed.setDireccion("C. avenue #123");
    		ed.setEmail("anonimo@gmail.com");
    		ed.setGenero("M");
    		ed.setId(555);
    		Empleado emp = new Empleado();
    		emp.setId(555);
    		emp.setNombre("Raul");
    		emp.setApellido("Mora");
    		emp.setEmpleadoDetalles(ed);
    		ed.setEmp(emp);
    		sesion.save(emp);
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}
	private static void ejemploAlcanceDatosInsertMuchosMuchos() {
		Transaction tx = null;
    	Session sesion = HibernateUtil.getSessionfactory().openSession();
    	try {
    		//Iniciar la sesión hibernate
    		tx = sesion.beginTransaction();
    		Empleado emp = new Empleado();
    		emp.setId(555);
    		emp.setNombre("Raul");
    		emp.setApellido("Mora");
    		Set<Producto> prods = new HashSet<Producto>();
    		prods.add(obtenerProducto(sesion,5));
    		prods.add(obtenerProducto(sesion,3));
    		prods.add(obtenerProducto(sesion,1));
    		emp.setProductos(prods);
    		sesion.save(emp);
    		
    		Producto pp = new Producto();
    		pp.setId(334);
    		pp.setNombre("Toalla");
    		pp.setPrecio(25);
    		Set<Empleado> emm = new HashSet<Empleado>();
    		emm.add(obtenerEmpleado(sesion,5));
    		emm.add(obtenerEmpleado(sesion,77));
    		emm.add(obtenerEmpleado(sesion,1));
    		pp.setEmpleados(emm);
    		sesion.save(pp);
    		
    		tx.commit();
    	}catch(HibernateException he) {
    		if(tx!=null)
    			tx.rollback();
    		he.printStackTrace();
    	}finally {
    		sesion.close();
    	}
		
	}

	private static Empleado obtenerEmpleado(Session sesion, int i) {
		Query consulta = sesion.createQuery("FROM Empleado WHERE id = :id");
		consulta.setParameter("id", i);
		return (Empleado)consulta.uniqueResult();
	}

	private static Producto obtenerProducto(Session sesion, int i) {
		Query consulta = sesion.createQuery("FROM Producto WHERE id = :id");
		consulta.setParameter("id", i);
		return (Producto)consulta.uniqueResult();
	}
}
