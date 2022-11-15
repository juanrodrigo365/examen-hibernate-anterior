package com.soria.Ejemplo.Modelo;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tecnico")
public class Tecnico {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Autoincremental
	private int id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String apellido;
	@Column(nullable = false)
	private String contacto;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tecnico", targetEntity=Revision.class)
	private Set<?> revisiones;

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

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public Set<?> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(Set<?> revisiones) {
		this.revisiones = revisiones;
	}
	
	
}
