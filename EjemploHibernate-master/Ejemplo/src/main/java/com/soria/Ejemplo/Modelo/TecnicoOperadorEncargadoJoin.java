package com.soria.Ejemplo.Modelo;

import java.util.Date;

public class TecnicoOperadorEncargadoJoin {
	private int idtecnico;
	private String nom_tec;
	private String ap_tec;
	private String placa;
	private String tipo;
	private String nom_ope;
	private String ap_ope;
	private Date fecha;
	public TecnicoOperadorEncargadoJoin(int idtecnico, String nom_tec, String ap_tec, String placa, String tipo,
			String nom_ope, String ap_ope, Date fecha) {
		super();
		this.idtecnico = idtecnico;
		this.nom_tec = nom_tec;
		this.ap_tec = ap_tec;
		this.placa = placa;
		this.tipo = tipo;
		this.nom_ope = nom_ope;
		this.ap_ope = ap_ope;
		this.fecha = fecha;
	}
	public TecnicoOperadorEncargadoJoin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIdtecnico() {
		return idtecnico;
	}
	public void setIdtecnico(int idtecnico) {
		this.idtecnico = idtecnico;
	}
	public String getNom_tec() {
		return nom_tec;
	}
	public void setNom_tec(String nom_tec) {
		this.nom_tec = nom_tec;
	}
	public String getAp_tec() {
		return ap_tec;
	}
	public void setAp_tec(String ap_tec) {
		this.ap_tec = ap_tec;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNom_ope() {
		return nom_ope;
	}
	public void setNom_ope(String nom_ope) {
		this.nom_ope = nom_ope;
	}
	public String getAp_ope() {
		return ap_ope;
	}
	public void setAp_ope(String ap_ope) {
		this.ap_ope = ap_ope;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
