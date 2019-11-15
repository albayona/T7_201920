//package model.data_structures;
//
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//
//import junit.framework.TestCase;
//import model.data_structures.grafo.ArcoNoExisteException;
//import model.data_structures.grafo.ArcoYaExisteException;
//import model.data_structures.grafo.Grafo;
//import model.data_structures.grafo.VerticeNoExisteException;
//import model.data_structures.grafo.VerticeYaExisteException;
//
//class GraphTest extends TestCase{
//
//	private Grafo<Integer, VerticeTest, ArcoTest> grafo;
//
//
//	//------------------------------------------------------
//	//Escenarios
//	//------------------------------------------------------
//
//	@Before
//	public void setUpEscenario0() {
//		grafo = new Grafo<Integer, VerticeTest, ArcoTest>();
//	}
//
//	@Before
//	public void setUpEscenario1() {
//		setUpEscenario0();
//
//		VerticeTest v = new VerticeTest(1);
//		VerticeTest v1 = new VerticeTest(2);
//		VerticeTest v2 = new VerticeTest(3);
//		VerticeTest v3 = new VerticeTest(4);
//		VerticeTest v4 = new VerticeTest(5);
//
//		try {
//			grafo.addVertex(v);
//			grafo.addVertex(v1);
//			grafo.addVertex(v2);
//			grafo.addVertex(v3);
//			grafo.addVertex(v4);
//		} catch (VerticeYaExisteException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Before
//	public void setUpEscenario2() {
//
//		setUpEscenario1();
//		ArcoTest a = new ArcoTest(1);
//		ArcoTest a1 = new ArcoTest(2);
//		ArcoTest a2 = new ArcoTest(3);
//		ArcoTest a3 = new ArcoTest(4);
//		ArcoTest a4 = new ArcoTest(5);
//
//		try {
//			grafo.addEdge(1, 2, a);
//			grafo.addEdge(1, 3, a1);
//			grafo.addEdge(2, 4, a2);
//			grafo.addEdge(2, 3, a3);
//			grafo.addEdge(4, 5, a4);
//
//		} catch (VerticeNoExisteException | ArcoYaExisteException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Before
//	public void setUpEscenario3() {
//		setUpEscenario0();
//
//		VerticeTest v;
//
//		for(int i = 0; i < 100; i++) {
//			v = new VerticeTest(i);
//			try {
//				grafo.addVertex(v);
//			} catch (VerticeYaExisteException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	//------------------------------------------------------
//	//Metodos de prueba
//	//------------------------------------------------------
//
//
//	@Test
//	public void testAddVertices() {
//		setUpEscenario0();
//		VerticeTest v = new VerticeTest(1);
//		VerticeTest v1 = new VerticeTest(2);
//		VerticeTest v2 = new VerticeTest(3);
//		VerticeTest v3 = new VerticeTest(4);
//		VerticeTest v4 = new VerticeTest(5);
//
//		try {
//			grafo.addVertex(v);
//			grafo.addVertex(v1);
//			grafo.addVertex(v2);
//			grafo.addVertex(v3);
//			grafo.addVertex(v4);
//		} catch (VerticeYaExisteException e) {
//			e.printStackTrace();
//		}
//		assertTrue(grafo.existeVertice(1));
//		assertTrue(grafo.existeVertice(2));
//		assertTrue(grafo.existeVertice(3));
//		assertTrue(grafo.existeVertice(4));
//		assertTrue(grafo.existeVertice(5));
//
//	}
//
//	@Test
//	public void testAddArcos() {
//		setUpEscenario1();
//		ArcoTest a = new ArcoTest(1);
//		ArcoTest a1 = new ArcoTest(2);
//		ArcoTest a2 = new ArcoTest(3);
//		ArcoTest a3 = new ArcoTest(4);
//		ArcoTest a4 = new ArcoTest(5);
//
//		try {
//			grafo.addEdge(1, 2, a);
//			grafo.addEdge(1, 3, a1);
//			grafo.addEdge(2, 4, a2);
//			grafo.addEdge(2, 3, a3);
//			grafo.addEdge(4, 5, a4);
//
//		} catch (VerticeNoExisteException | ArcoYaExisteException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			assertTrue(grafo.existeArco(1, 2));
//			assertTrue(grafo.existeArco(1, 3));
//			assertTrue(grafo.existeArco(2, 4));
//			assertTrue(grafo.existeArco(2, 3));
//			assertTrue(grafo.existeArco(4, 5));
//		} catch (VerticeNoExisteException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	@Test
//	public void testGetVertice() {
//		setUpEscenario2();
//
//		try {
//			VerticeTest v = grafo.getInfoVertex(1);
//			VerticeTest v1 = grafo.getInfoVertex(2);
//			VerticeTest v2 = grafo.getInfoVertex(3);
//			VerticeTest v3 = grafo.getInfoVertex(4);
//			VerticeTest v4 = grafo.getInfoVertex(5);
//
//			assertTrue(1 == v.darId());
//			assertTrue(2 == v1.darId());
//			assertTrue(3 == v2.darId());
//			assertTrue(4 == v3.darId());
//			assertTrue(5 == v4.darId());
//
//		} catch (VerticeNoExisteException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testGetArc() {
//		setUpEscenario2();
//		try {
//			ArcoTest a1 = grafo.getInfoArc(1, 2);
//			ArcoTest a2 = grafo.getInfoArc(1, 3);
//			ArcoTest a3 = grafo.getInfoArc(2, 4);
//			ArcoTest a4 = grafo.getInfoArc(2, 3);
//			ArcoTest a5 = grafo.getInfoArc(4, 5);
//
//			assertTrue(1 == a1.darPeso());
//			assertTrue(2 == a2.darPeso());
//			assertTrue(3 == a3.darPeso());
//			assertTrue(4 == a4.darPeso());
//			assertTrue(5 == a5.darPeso());
//
//		} catch (VerticeNoExisteException | ArcoNoExisteException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testSetInfoArc() {
//		setUpEscenario2();
//		ArcoTest v1 = new ArcoTest(100);
//		ArcoTest v2 = new ArcoTest(200);
//		ArcoTest v3 = new ArcoTest(300);
//
//		try {
//			grafo.setInfoArc(1, 2,  v1);
//			grafo.setInfoArc(1, 3,  v2);
//			grafo.setInfoArc(4, 5,  v3);
//
//		} catch (VerticeNoExisteException | ArcoNoExisteException e) {
//			e.printStackTrace();
//		}
//
//
//		try {
//			v1 = grafo.getInfoArc(1, 2);
//			v2 = grafo.getInfoArc(1, 3);
//			v3 = grafo.getInfoArc(4, 5);
//		} catch (VerticeNoExisteException | ArcoNoExisteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		assertTrue(v1.darPeso() == 100);
//		assertTrue(v2.darPeso() == 200);
//		assertTrue(v3.darPeso() == 300);
//	}
//
//	@Test
//	public void  testSetInfoVertice() {
//		setUpEscenario2();
//		VerticeTest v = new VerticeTest(1);
//		v.setName("Nuevo1");
//
//		VerticeTest v2 = new VerticeTest(2);
//		v2.setName("Nuevo2");
//
//		VerticeTest v3 = new VerticeTest(3);
//		v3.setName("Nuevo3");
//
//		try {
//			grafo.setInfoVertex(1, v);
//			grafo.setInfoVertex(2, v2);
//			grafo.setInfoVertex(3, v3);
//
//		} catch (VerticeNoExisteException e) {
//			e.printStackTrace();
//		}
//
//
//		try {
//			v = grafo.getInfoVertex(1);
//			v2 = grafo.getInfoVertex(2);
//			v3 = grafo.getInfoVertex(3);
//		} catch (VerticeNoExisteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//		assertTrue(v.getName().equals("Nuevo1"));
//		assertTrue(v2.getName().equals("Nuevo2"));
//		assertTrue(v3.getName().equals("Nuevo3"));
//	}
//
//	@Test
//	public void testNumArcos() {
//
//		setUpEscenario3();
//
//		ArcoTest a;
//
//		for(int i = 0; i < 99; i++) {
//			a = new ArcoTest(i*2);
//
//			try {
//				grafo.addEdge(i, i+1, a);
//			} catch (VerticeNoExisteException | ArcoYaExisteException e) {
//				e.printStackTrace();
//			}
//		}
//
//		assertTrue(grafo.E() == 99);
//
//
//	}
//
//	@Test
//	public void testNumVertices() {
//		setUpEscenario0();
//
//		VerticeTest v;
//
//		for(int i = 0; i < 100; i++) {
//			v = new VerticeTest(i);
//			try {
//				grafo.addVertex(v);
//			} catch (VerticeYaExisteException e) {
//				e.printStackTrace();
//			}
//		}
//
//		assertTrue(grafo.V() == 100);
//	}
//
//
//
//
//}
