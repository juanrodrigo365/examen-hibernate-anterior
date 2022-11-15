package com.soria.Ejemplo.Modelo;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="operador_detalle")
public class OperadorDetalle {
	@Id
	@Column(name="id_operador", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String telefono;
	private String direccion;
	private String email;
	
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Operador id_operador;

	public OperadorDetalle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OperadorDetalle(int id, String telefono, String direccion, String email, Operador id_operador) {
		super();
		this.id = id;
		this.telefono = telefono;
		this.direccion = direccion;
		this.email = email;
		this.id_operador = id_operador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Operador getId_operador() {
		return id_operador;
	}

	public void setId_operador(Operador id_operador) {
		this.id_operador = id_operador;
	}
	
}
