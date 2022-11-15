package com.soria.Ejemplo.Modelo;

public class FuncionesGroupBy {
	private int id;
	private long conteo;
	private int max;
	private int min;
	private long sum;
	private double media;
	
	public FuncionesGroupBy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FuncionesGroupBy(int id, long conteo, int max, int min, long sum, double media) {
		super();
		this.id = id;
		this.conteo = conteo;
		this.max = max;
		this.min = min;
		this.sum = sum;
		this.media = media;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getConteo() {
		return conteo;
	}

	public void setConteo(long conteo) {
		this.conteo = conteo;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}
	
	
	
}
