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
@Table(name="vehiculo")
public class Vehiculo {
	@Id
	private String placa;
	@Column(nullable = false)
	private double odometro;
	@Column(nullable = false)
	private String modelo;
	@Column(nullable = false)
	private String tipo;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="vehiculo", targetEntity=Revision.class)
	private Set<?> revisiones;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public double getOdometro() {
		return odometro;
	}

	public void setOdometro(double odometro) {
		this.odometro = odometro;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Set<?> getRevisiones() {
		return revisiones;
	}

	public void setRevisiones(Set<?> revisiones) {
		this.revisiones = revisiones;
	}
	
	
}
