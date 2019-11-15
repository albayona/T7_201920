package model.vo;

import api.IVertice;
 	
public class VOInterseccion implements IVertice<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private double latitud;
	private double longitud;
	private int zona;
	
	
	public VOInterseccion(int id, double latitud, double longitud, int zona) {
		super();
		this.id = id;
		this.latitud = latitud;
		this.longitud = longitud;
		this.zona = zona;
	}

	public int getZona() {
		return zona;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}


	@Override
	public Integer darId() {
		Integer I = (Integer) new java.lang.Integer(id);
		return I;
	}

	

}
