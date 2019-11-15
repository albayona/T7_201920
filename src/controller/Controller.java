package controller;

import api.IVertice;
import model.data_structures.grafo.Grafo;
import model.data_structures.listas.LinkedList;
import model.logic.TripsManager;
import model.vo.VODistance;

public class Controller {

	/**
	 * Reference to the services manager
	 */
	@SuppressWarnings("unused")
	private static TripsManager manager = new TripsManager();
	
	public static void guardarGrafoEnFormatoJSON() {
		manager.guardarGrafoJSON("./data/grafoDefinitivo.json");
	}
	
	public static void cargarGrafoEnFormatoJSON() {
		manager.cargarGrafoJSON("./data/grafoDefinitivo.json");;
	}
	
	public static void loadIntersections() {
		manager.loadIntersection("./data/bogota_vertices2.txt");
	}
	
	public static void loadEdges() {
		manager.loadEdges("./data/bogota_arcos2.txt");
	}
	
	public static LinkedList<IVertice<Integer>> vertices() {
		return manager.getGrafo1().darVertices();
	}
	
	public static LinkedList<VODistance> arcos() {
		return manager.getGrafo1().darArcos();
	}

	public static Grafo<Integer, IVertice<Integer>, VODistance> getGrafo(){ return  manager.getGrafo1();}

	public static int getArcosMixtos() {
		return manager.getArcosMixtos();
	}



}
