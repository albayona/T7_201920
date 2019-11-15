package model.logic;

import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.swing.MapView;

import api.IVertice;

import com.teamdev.jxmaps.Rectangle;

import model.data_structures.grafo.Grafo;
import model.data_structures.listas.LinkedList;
import model.vo.VODistance;
import model.vo.VOInterseccion;


import javax.swing.*;
import java.awt.*;


/**
 *
 * @author sebastiangarcia
 *
 */
public class JxMaps extends MapView {

	private static final long serialVersionUID = 1L;


	/**
	 * Constructor
	 * @param options
	 */
	public JxMaps(MapViewOptions options, LinkedList<IVertice<Integer>> vertices, LinkedList<VODistance> arcos) {
		super(options);

		setOnMapReadyHandler(new MapReadyHandler() {
			@Override
			public void onMapReady(MapStatus status) {
				if (status == MapStatus.MAP_STATUS_OK) {

					// defincion del mapa y posicionamiento del centro en Chicago 41.8781, -87.6298
					LatLng coord1 = null;
					LatLng coord2 = null;
					LatLng[] coords;
					IVertice<Integer> v;
					IVertice<Integer> v2;

					final Map map = getMap();
					map.setZoom(15.0);
					map.setCenter(new LatLng(41.8781, -87.6298));

					// Cada estacion es marcada en el mapa. El radio del circulo es determinado por la capacidad de la estacion.
					VOInterseccion intersc;

					for( IVertice<Integer> ver : vertices ) {

                         if(ver instanceof VOInterseccion) {
							intersc = (VOInterseccion) ver;
							 coord1 = new LatLng(intersc.getLatitud(), intersc.getLongitud());
							drawCircle(map, coord1, 100); // mostrar la marca y su circulo
						}
					}
					for(VODistance d : arcos) {
						v = d.getVertice1();
						v2 = d.getVertice2();

						 if(v instanceof VOInterseccion) {
							intersc = (VOInterseccion) v;
							coord1 = new LatLng(intersc.getLatitud(), intersc.getLongitud());
						}

                         if(v2 instanceof VOInterseccion) {
							intersc = (VOInterseccion) v2;
							coord2 = new LatLng(intersc.getLatitud(), intersc.getLongitud());
						}
						coords = new LatLng[2];
						coords[0] = coord1;
						coords[1] = coord2;
						polylinePath(map, coords);
					}
			}
			}
		});

	}



	/**
	 * Grafica un pointer en el mapa en las coordenas dadas y dibuja un circulo de circunferencia dada
	 * @param coords Latitude, Longitude
	 * @param diameter del circulo a dibujar al rededor de la coordenada
	 */
	private void drawMarker( Map map, LatLng coords, int diameter) {
		Marker marker = new Marker(map);
		marker.setPosition(coords);

		Circle circle = new Circle(map);
		circle.setCenter(coords);
		circle.setRadius(diameter);
	}

	/**
	 * Grafica un circulo en el mapa en las coordenas dadas
	 * @param coords Latitude, Longitude
	 * @param diameter del circulo a dibujar al rededor de la coordenada
	 */
	private void drawCircle( Map map, LatLng coords, int diameter) {
		Circle circle = new Circle(map);
		circle.setCenter(coords);
		circle.setRadius(diameter);
	}


	/**
	 * Grafica un circulo en el mapa en las coordenas dadas
	 * @param coords Latitude, Longitude
	 * @param diameter del circulo a dibujar al rededor de la coordenada
	 */
	private void drawRectangle( Map map, LatLng coords, int diameter) {
		RectangleOptions options = new RectangleOptions();
		options.setFillOpacity(0);
		options.setStrokeColor(Color.BLUE.toString());
		Rectangle rectangulo = new Rectangle (map);
		LatLngBounds bounds = new LatLngBounds (new LatLng (coords.getLat() - 0.0004, coords.getLng() - 0.0006), new LatLng (coords.getLat() + 0.0004, coords.getLng() + 0.0006));
		rectangulo.setBounds(bounds);
		rectangulo.setOptions(options);
	}
	/**
	 * Grafica un conjunto de coordenadas y las une mediante rectas para generar una ruta
	 * @param path Array of LatLng
	 */
	private void polylinePath(Map map, LatLng[] path) {
		Polyline polyline = new Polyline(map);
		polyline.setPath(path);

		PolylineOptions options = new PolylineOptions();
		options.setGeodesic(true);
		options.setStrokeColor("#00aafd");
		options.setStrokeOpacity(1.0);
		polyline.setOptions(options);
	}


	/**
	 * Calcula y grafica las direcciones para dos localizaciones dadas
	 * @param origin direccion de partida e.g: Desplaines St & Jackson Blvd
	 * @param destination direccion de llegada e.g: Desplaines St & Kinzie St
	 */
	private void calculateAddressDirections(String origin, String destination) {
		final Map map = getMap();
		DirectionsRequest request = new DirectionsRequest();

		request.setOriginString(origin);
		request.setDestinationString(destination);

		request.setTravelMode(TravelMode.DRIVING);
		getServices().getDirectionService().route(request, new DirectionsRouteCallback(map) {
			@Override
			public void onRoute(DirectionsResult result, DirectionsStatus status) {
				if (status == DirectionsStatus.OK) {
					map.getDirectionsRenderer().setDirections(result);
				} else {
					System.out.println("Error. Route cannot be calculated.\nPlease correct input data.");
				}
			}
		});
	}

	/**
	 * Calcula y grafica las direcciones para dos conjuntos de coordenadas dadas
	 * @param origin
	 * @param destination
	 */
	private void calculateCoordsDirections(LatLng origin, LatLng destination) {
		final Map map = getMap();
		DirectionsRequest request = new DirectionsRequest();

		request.setOrigin(origin);
		request.setDestination(destination);

		request.setTravelMode(TravelMode.DRIVING);
		getServices().getDirectionService().route(request, new DirectionsRouteCallback(map) {
			@Override
			public void onRoute(DirectionsResult result, DirectionsStatus status) {
				if (status == DirectionsStatus.OK) {
					map.getDirectionsRenderer().setDirections(result);
				} else {
					System.out.println("Error. Route cannot be calculated.\nPlease correct input data.");
				}
			}
		});
	}

	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {

		MapViewOptions options = new MapViewOptions();
		options.importPlaces();
		TripsManager manager = new TripsManager();
		manager.cargarGrafoJSON("./data/grafoPrueba.json");
		Grafo<Integer, IVertice<Integer>, VODistance>  g = manager.getGrafo1();
		LinkedList<IVertice<Integer>> vertices = g.darVertices();
		LinkedList<VODistance> arcos = g.darArcos();

		options.setApiKey("AIzaSyDJwifnoYwfUZulqwxFJpMe6M2m7q8FZLc");
		final JxMaps mapView = new JxMaps(options, vertices, arcos);

		JFrame frame = new JFrame("Estructuras de Datos 201920 - JxMaps");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(mapView, BorderLayout.CENTER);
		frame.setSize(900, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}








}