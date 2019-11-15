package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import api.IVertice;
import model.vo.VODistance;
import model.vo.VOInterseccion;

import model.data_structures.grafo.ArcoYaExisteException;
import model.data_structures.grafo.Grafo;
import model.data_structures.grafo.VerticeNoExisteException;
import model.data_structures.grafo.VerticeYaExisteException;
import model.data_structures.listas.LinkedList;

public class TripsManager {

    /**
     * EARTH_RADIUS
     */
    private static final int EARTH_RADIUS = 6371;


    private Grafo<Integer, IVertice<Integer>, VODistance> grafo1;
    private int arcosMixtos;
    private int vertices;


    public TripsManager() {
        super();
        grafo1 = new Grafo<>();
        arcosMixtos = 0;
        vertices = 0;
    }


    public Grafo<Integer, IVertice<Integer>, VODistance> getGrafo1() {
        return grafo1;
    }

    public int getArcosMixtos() {
        return arcosMixtos;
    }

    public int getVertices() {
        return vertices;
    }


    public void loadIntersection(String intersectionFile) {
        File f = new File(intersectionFile);
        String[] datos;
        VOInterseccion i1;

        int count = 0;

        try {
            for (String line : Files.readAllLines(Paths.get(f.getPath()))) {

                if (count > 0) {
                    datos = line.split(";");
                    System.out.println(datos[0]);
                    i1 = new VOInterseccion(Integer.parseInt(datos[0]), Double.parseDouble(datos[2]), Double.parseDouble(datos[1]), Integer.parseInt(datos[3]));
                    grafo1.addVertex(i1);
                }
                count++;
            }
        } catch (NumberFormatException | IOException | VerticeYaExisteException e) {
            e.printStackTrace();
        }
    }


    /**
     * Carga los arcos
     */
    public void loadEdges(String edgesFile) {
        String csvFile = edgesFile;
        BufferedReader br = null;
        String line = "";
        //Se define separador ","

        String cvsSplitBy = " ";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            line = br.readLine();
            line = br.readLine();
            line = br.readLine();


            int idInic = 0;
            int idFinal = 0;

            VOInterseccion interInic = null;
            VOInterseccion interFinal = null;
            VODistance arco = null;

            String[] datos;

            double distance;

            while ((line = br.readLine()) != null) {

                datos = line.split(cvsSplitBy);
                idInic = Integer.parseInt(datos[0]);
                interInic = (VOInterseccion) grafo1.getInfoVertex(idInic);


                for (int i = 1; i < datos.length; i++) {

                    idFinal = Integer.parseInt(datos[i]);
                    interFinal = (VOInterseccion) grafo1.getInfoVertex(idFinal);
                    distance = distance(interInic.getLatitud(), interInic.getLongitud(), interFinal.getLatitud(), interFinal.getLongitud());

                    arco = new VODistance(distance, interInic, interFinal);

                    grafo1.addEdge(idInic, idFinal, arco);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (VerticeNoExisteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ArcoYaExisteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //==========================================================
    //GUARDAR GRAFO JSON
    //==========================================================

    /**
     * Guarda el grafo en formato JSon en el archivo que pasa por parametro
     *
     * @param ruta La ruta donde se desea guardar el archivo
     */
    public void guardarGrafoJSON(String ruta) {

        JSONObject js = darJsonObjectDeGrafo(grafo1);

        try {
            FileWriter file = new FileWriter(ruta);
            js.writeJSONString(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo auxiliar para guardar el grafo en un archivo
     */
    @SuppressWarnings("unchecked")
    private JSONObject darJsonObjectDeGrafo(Grafo<Integer, IVertice<Integer>, VODistance> grafo) {
        JSONObject jsGrafo = new JSONObject(); //principal

        JSONArray jsStationsVertices = new JSONArray();
        JSONArray jsIntercecionesVertices = new JSONArray();
        estacionesYIntersecionesVertices(grafo, jsStationsVertices, jsIntercecionesVertices);
        JSONArray jsArcos = darArcos(grafo);

        jsGrafo.put("numVertices", grafo.V());
        jsGrafo.put("estaciones", jsStationsVertices);
        jsGrafo.put("intersecciones", jsIntercecionesVertices);
        jsGrafo.put("arcos", jsArcos);

        return jsGrafo;
    }

    /**
     * Metodo auxiliar para guardar el grafo en un archivo
     */
    @SuppressWarnings("unchecked")
    private JSONArray darArcos(Grafo<Integer, IVertice<Integer>, VODistance> grafo) {
        JSONArray js = new JSONArray();

        LinkedList<VODistance> lista = grafo.darArcos();
        Iterator<VODistance> it = lista.iterator();
        JSONObject arcoJSON;
        VODistance arco = null;

        while (it.hasNext()) {
            arco = it.next();
            arcoJSON = new JSONObject();
            arcoJSON.put("distancia", arco.darPeso());
            arcoJSON.put("id_Vertice_1", arco.getVertice1().darId());
            arcoJSON.put("id_Vertice_2", arco.getVertice2().darId());
            js.add(arcoJSON);
        }

        return js;
    }


    /**
     * Metodo auxiliar para guardar el grafo en un archivo
     */
    @SuppressWarnings("unchecked")
    private void estacionesYIntersecionesVertices(Grafo<Integer, IVertice<Integer>, VODistance> grafo, JSONArray estaciones, JSONArray intersecciones) {

        LinkedList<IVertice<Integer>> lista = grafo.darVertices();

        Iterator<IVertice<Integer>> it = lista.iterator();
        JSONObject verticeAdd;

        while (it.hasNext()) {
            IVertice verticeActual = it.next();

            if (verticeActual instanceof VOInterseccion) {
                verticeAdd = darJsObjectInterseccion((VOInterseccion) verticeActual);
                intersecciones.add(verticeAdd);
            }
        }
    }


    /**
     * Metodo auxiliar para guardar el grafo en un archivo
     */
    @SuppressWarnings("unchecked")
    private JSONObject darJsObjectInterseccion(VOInterseccion vertice) {
        JSONObject js = new JSONObject();

        js.put("id", vertice.darId());
        js.put("latitud", vertice.getLatitud());
        js.put("longitude", vertice.getLongitud());
        js.put("zona", vertice.getZona());
        return js;
    }

    //==========================================================
    //CARGAR GRAFO JSON
    //==========================================================

    /**
     * Carga el grafo del mundo del archivo que se pasa por parametro
     */
    public void cargarGrafoJSON(String jsonRoute) {

        FileReader file;
        try {
            file = new FileReader(jsonRoute);
            JSONParser parser = new JSONParser();
            JSONObject js = (JSONObject) parser.parse(file);

            grafo1 = darGrafoDeJSonObject(js, grafo1);
            file.close();
        } catch (IOException | ParseException | VerticeYaExisteException | VerticeNoExisteException | ArcoYaExisteException e) {

            e.printStackTrace();
        }
    }

    private Grafo<Integer, IVertice<Integer>, VODistance> darGrafoDeJSonObject(JSONObject js, Grafo<Integer, IVertice<Integer>, VODistance> grafo) throws VerticeYaExisteException, VerticeNoExisteException, ArcoYaExisteException {

        JSONArray inters = (JSONArray) js.get("intersecciones");
        JSONArray arcos = (JSONArray) js.get("arcos");
        cargarInterseccionesAGrafo(inters, grafo);
        cargarArcosAGrafo(arcos, grafo);

        return grafo;
    }

    private void cargarArcosAGrafo(JSONArray arcos, Grafo<Integer, IVertice<Integer>, VODistance> grafo) throws VerticeNoExisteException, ArcoYaExisteException {
        VODistance a = null;
        JSONObject obj;
        for (int i = 0; i < arcos.size(); i++) {
            obj = (JSONObject) arcos.get(i);
            cargarArcoAGrafo(obj, grafo, a);
        }
    }

    private void cargarArcoAGrafo(JSONObject obj, Grafo<Integer, IVertice<Integer>, VODistance> grafo, VODistance a) throws VerticeNoExisteException, ArcoYaExisteException {

        Double distancia = (Double) obj.get("distancia");
        int vertice1 = (int) (long) obj.get("id_Vertice_1");
        int vertice2 = (int) (long) obj.get("id_Vertice_2");
        a = new VODistance(distancia, grafo.getInfoVertex(vertice1), grafo.getInfoVertex(vertice2));
        grafo.addEdge(vertice1, vertice2, a);
    }


    private void cargarInterseccionesAGrafo(JSONArray inters, Grafo<Integer, IVertice<Integer>, VODistance> grafo) throws VerticeYaExisteException {

        VOInterseccion vo = null;
        for (int i = 0; i < inters.size(); i++) {
            JSONObject obj = (JSONObject) inters.get(i);
            cargarInterseccionAGrafo(obj, grafo, vo);
        }
    }

    private void cargarInterseccionAGrafo(JSONObject obj, Grafo<Integer, IVertice<Integer>, VODistance> grafo, VOInterseccion vo) throws VerticeYaExisteException {

        double latitud = (double) obj.get("latitud");
        double longitud = (double) obj.get("longitude");
        long id = (long) obj.get("id");
        long zona = (long) obj.get("zona");

        Integer id1 = (int) id;
        Integer z1 = (int) id;
        vo = new VOInterseccion(id1, latitud, longitud, z1);
        grafo.addVertex(vo);
    }


    /**
     * @param startLat
     * @param startLong
     * @param endLat
     * @param endLong
     * @return
     */
    public double distance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    /**
     * Metodos Privados----------------------------------------------
     */

    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }


}