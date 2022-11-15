package com.soria.Ejemplo.Modelo;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="operador")
public class Operador {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Autoincremental
	private int id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String apellido;
	@Column(nullable = false)
	private String categoria_licencia;
	@Column(nullable = false)
	private Date fecha_vencimiento;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="operador", targetEntity=Revision.class)
	private Set<?> revisiones;
	@OneToOne(fetch=FetchType.LAZY, mappedBy="id_operador", targetEntity= OperadorDetalle.class, cascade = CascadeType.ALL)
	private OperadorDetalle operadorDetalle;
	
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCategoria_licencia() {
		return categoria_licencia;
	}
	public void setCategoria_licencia(String categoria_licencia) {
		this.categoria_licencia = categoria_licencia;
	}
	public Date getFecha_vencimiento() {
		return fecha_vencimiento;
	}
	public void setFecha_vencimiento(Date string) {
		this.fecha_vencimiento = string;
	}
	public Set<?> getRevisiones() {
		return revisiones;
	}
	public void setRevisiones(Set<?> revisiones) {
		this.revisiones = revisiones;
	}
	
}
