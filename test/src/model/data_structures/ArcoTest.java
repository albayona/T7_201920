package model.data_structures;

import api.IArco;
import api.IVertice;

//Clase auxiliar para las pruebas del grafo
public class ArcoTest implements IArco{

	private static final long serialVersionUID = 1L;
	private double peso;
	
	public ArcoTest(int peso) {
		this.peso = peso;
	}

	@Override
	public double darPeso() {
		// TODO Auto-generated method stub
		return peso;
	}

	@Override
	public IVertice getVertice1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IVertice getVertice2() {
		// TODO Auto-generated method stub
		return null;
	}

}
