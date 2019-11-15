package model.data_structures;

import api.IVertice;
//Clase auxiliar para las pruebas del grafo
public class VerticeTest implements IVertice<Integer> {

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	public VerticeTest(int id) {
		this.id = id;
		name = "";
	}

	@Override
	public Integer darId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
