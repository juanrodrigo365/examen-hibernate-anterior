package com.soria.Ejemplo.Modelo;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="empleado_detalles")
public class EmpleadoDetalles {
	@Id
	@Column(name="id_e_det", unique=true, nullable=false)
	@GenericGenerator(name="miGen", strategy="foreign", parameters = @Parameter(name="property", value="emp"))
	@GeneratedValue(generator="miGen")
	private int id;
	private String email;
	private String genero;
	private String direccion;
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Empleado emp;
	public EmpleadoDetalles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmpleadoDetalles(int id, String email, String genero, String direccion) {
		super();
		this.id = id;
		this.email = email;
		this.genero = genero;
		this.direccion = direccion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Empleado getEmp() {
		return emp;
	}
	public void setEmp(Empleado emp) {
		this.emp = emp;
	}
	
}
