package com.soria.Ejemplo.Modelo;

public class EmpleadoVentaJoin {
	private String nombre;
	private int idprod;
	private int cantidad;
	public EmpleadoVentaJoin(String nombre, int idprod, int cantidad) {
		super();
		this.nombre = nombre;
		this.idprod = idprod;
		this.cantidad = cantidad;
	}
	public EmpleadoVentaJoin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getIdprod() {
		return idprod;
	}
	public void setIdprod(int idprod) {
		this.idprod = idprod;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}
