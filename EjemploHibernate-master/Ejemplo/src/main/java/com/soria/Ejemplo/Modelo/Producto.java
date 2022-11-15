package com.soria.Ejemplo.Modelo;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="producto")
public class Producto {
	@Id
	private int id;
	private String nombre;
	private double precio;
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
		name="venta",
		catalog="abcdef",
		joinColumns= {
			@JoinColumn(name="idprod", nullable=false, updatable=false)
		},
		inverseJoinColumns= {
			@JoinColumn(name="idemp", nullable=false,updatable=false)
		}
	)
	private Set<Empleado> empleados;
	public Producto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Producto(int id, String nombre, double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Set<Empleado> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}
	
}
