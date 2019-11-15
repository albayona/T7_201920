package model.vo;

import api.IArco;
import api.IVertice;

public class VODistance implements IArco {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  Distacia Harvesiana
	 */
	private double distancia;
	
	private IVertice<Integer> vertice1;
	
	private IVertice<Integer> vertice2;

	/**
	 * Constructor---------------------------------------------------
	 */
	public VODistance( double distancia, IVertice<Integer> vertice1, IVertice<Integer> vertice2) {
		super();
		this.distancia = distancia;
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
	}

	
	@Override
	public double darPeso() {
		return distancia;
	}

	@Override
	public IVertice getVertice1() {
		return vertice1;
	}

	@Override
	public IVertice getVertice2() {
		return vertice2;
	}
	
	

}
