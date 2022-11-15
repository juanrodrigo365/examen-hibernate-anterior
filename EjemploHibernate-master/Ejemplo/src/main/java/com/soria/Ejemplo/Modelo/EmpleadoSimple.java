package com.soria.Ejemplo.Modelo;

public class EmpleadoSimple {
	private int codigo;
	private String apellido;
	
	public EmpleadoSimple() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmpleadoSimple(int codigo, String apellido) {
		super();
		this.codigo = codigo;
		this.apellido = apellido;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
}
