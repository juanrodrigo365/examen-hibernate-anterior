package com.soria.Ejemplo.Modelo;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="revision")
public class Revision {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Autoincremental
	private int id;
	@Column(nullable = false, insertable=false, updatable=false)
	private int encargado;
	@Column(nullable = false, insertable=false, updatable=false)
	//private String vehiculo;
	//@Column(nullable = false, insertable=false, updatable=false)
	private int chofer;
	@Column(nullable = false)
	private Date fecha_revision;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="chofer", nullable=false)
	private Operador operador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="encargado", nullable=false)
	private Tecnico tecnico;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="vehiculo", nullable=false)
	private Vehiculo vehiculo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEncargado() {
		return encargado;
	}
	public void setEncargado(int encargado) {
		this.encargado = encargado;
	}
	//public String getVehiculo() {
	//	return vehiculo;
	//}
	//public void setVehiculo(String vehiculo) {
	//	this.vehiculo = vehiculo;
	//}
	public int getChofer() {
		return chofer;
	}
	public void setChofer(int chofer) {
		this.chofer = chofer;
	}
	public Date getFecha_revision() {
		return fecha_revision;
	}
	public void setFecha_revision(Date fecha_revision) {
		this.fecha_revision = fecha_revision;
	}
	
	
}
