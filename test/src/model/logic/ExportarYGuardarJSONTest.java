package model.logic;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import api.IArco;
import api.IVertice;
import junit.framework.TestCase;
import model.data_structures.grafo.ArcoYaExisteException;
import model.data_structures.grafo.Grafo;
import model.data_structures.grafo.VerticeNoExisteException;
import model.data_structures.grafo.VerticeYaExisteException;
import model.data_structures.listas.LinkedList;
import model.vo.VODistance;
import model.vo.VOInterseccion;
import model.vo.VOStation;

public class ExportarYGuardarJSONTest extends TestCase {
	
	private TripsManager manager;
	
	//------------------------------------------------------
	//Escenarios
	//------------------------------------------------------

	@Before
	public void setUpEscenario0() {
		manager = new TripsManager();
	}

	@Before
	public void setUpEscenario1() {
		setUpEscenario0();
		
		Grafo<Integer, IVertice<Integer>, VODistance> grafo = manager.getGrafo1();
		VOStation s;
		VODistance a = null;
		VOInterseccion t;
		VOInterseccion t2;
		Random r = new Random();
		for(int i = 0; i < 100; i++) {
			s = new VOStation(i, "name" + i, "city" +i, r.nextInt(100000), r.nextInt(100000), i*2, "date000");
			t = new VOInterseccion(100+i, r.nextInt(100000), r.nextInt(100000));
			
			if(i>2) {
				try {
					t2 = (VOInterseccion) grafo.getInfoVertex(100+i-1);
					a =  new VODistance(r.nextInt(10000), t, t2);
				} catch (VerticeNoExisteException e) {	e.printStackTrace();}
			}

			try {
				grafo.addVertex(s);
				grafo.addVertex(t);
				
				if(i>2) {
					grafo.addEdge(100+i, 100+i-1, a);
					if(i%2==0) {
						try {
							t2 = (VOInterseccion) grafo.getInfoVertex(100+i);
							a =  new VODistance(r.nextInt(10000), s, t2);
						} catch (VerticeNoExisteException e) {	e.printStackTrace();}
						grafo.addEdge(i, 100+i, a);
					}

				}
	
			} catch (VerticeYaExisteException | VerticeNoExisteException | ArcoYaExisteException e) {e.printStackTrace();}
		}
		
	}
	
	//------------------------------------------------------
	//Metodos de prueba
	//------------------------------------------------------


	@Test
	public void testGuardarJSON() {
		setUpEscenario1();
		
		manager.guardarGrafoJSON("./data/grafoPrueba.json");
		
	}
	

	@Test
	public void testCargarJSON() {
		setUpEscenario0();
		//TODO json
		manager.cargarGrafoJSON("./data/grafoPrueba.json");
		
		assertEquals(manager.getGrafo1().V(), 200);
		
		LinkedList<VODistance> list = manager.getGrafo1().darArcos();
		LinkedList<IVertice<Integer>> list2 = manager.getGrafo1().darVertices();

		System.out.println("--------------Arcos------------");
		for(IArco v : list) {
			System.out.println(v.darPeso());
		}
		System.out.println("--------------Vertices------------");
		for(IVertice v : list2) {
			System.out.println(v.darId());
		}
		
		
	}


}
