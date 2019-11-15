
package model.data_structures.grafo;

import java.util.Iterator;

import api.IArco;
import api.IVertice;
import model.data_structures.listas.LinkedList;

/**
 * Sacado de (http://cupi2.uniandes.edu.co)
 * @author J. Villalobos - Abril 14, 2006
 *
 * @param <K>
 * @param <V>
 * @param <A>
 */
public class Camino<K, V extends IVertice<K>, A extends IArco>
{
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Lista con los arcos del camino
	 */
	private LinkedList<A> arcos;

	/**
	 * Lista con los vértices del camino
	 */
	private LinkedList<V> vertices;

	/**
	 * Origen del camino
	 */
	private V origen;

	/**
	 * Costo total del camino
	 */
	private int costo;

	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	/**
	 * Constructor del camino
	 * @param origen Vértice de origen del camino
	 */
	public Camino( V origen )
	{
		// Inicializar los atributos del camino
		vertices = new LinkedList<V>( );
		arcos = new LinkedList<A>( );
		costo = 0;
		this.origen = origen;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Agrega un arco al final del camino
	 * @param arco Arco a agregar
	 */
	public void agregarArcoFinal( V vertice, A arco )
	{
		arcos.addAtEnd(arco );
		vertices.addAtEnd( vertice );
		costo += arco.darPeso( );
	}

	/**
	 * Agrega un arco al comienzo del camino.
	 * @param nuevoOrigen Nuevo origen del camino
	 * @param arco Arco que va del nuevo origen al antiguo origen del camino
	 */
	public void agregarArcoComienzo( V nuevoOrigen, A arco )
	{
		arcos.add( arco );
		vertices.add( origen);
		origen = nuevoOrigen;
		costo += arco.darPeso( );
	}

	/**
	 * Concatena todos los arcos del camino especificado al final del camino
	 * @param camino Camino a concatenar
	 */
	public void concatenar( Camino<K, V, A> camino )
	{
		// Agregar los arcos y vertices del camino a concatenar ignorando el origen del camino ingresado por parámetro
		for( int i = 0; i < camino.arcos.getSize(); i++ )
			agregarArcoFinal( camino.vertices.getElement( i ), camino.arcos.getElement( i ) );
	}

	/**
	 * Elimina el último arco
	 */
	public void eliminarUltimoArco( )
	{
		if( arcos.getSize() >= 1 )
		{
			A arco = arcos.getElement( arcos.getSize( ) - 1 );
			arcos.delete(arcos.getSize( ) - 1 );
			vertices.delete( vertices.getSize( ) - 1 );
			costo -= arco.darPeso( );
		}
	}

	/**
	 * Reinicia el camino conservando el origen
	 */
	public void reiniciar( )
	{
		arcos = new LinkedList<>();
		vertices = new LinkedList<>();
		costo = 0;
	}

	/**
	 * Devuelve la longitud del camino
	 * @return Longitud del camino
	 */
	public int darLongitud( )
	{
		return arcos.getSize();
	}

	/**
	 * Devuelve el costo del camino
	 * @return Costo del camino
	 */
	public int darCosto( )
	{
		return costo;
	}

	/**
	 * Devuelve los vértices por los cuales pasa el camino
	 * @return Iterador sobre los vértices
	 */
	public Iterator<V> darSecuenciaVertices( )
	{
		// Crear una lista auxiliar y agregarle el origen
		LinkedList<V> aux = new LinkedList<V>( );
		aux.add( origen );

		// Poblara la lista auxiliar con los vértices del camino
		for( int i = 0; i < vertices.getSize( ); i++ )
		{
			aux.add( vertices.getElement( i ) );
		}

		// Retornar el iterador
		return aux.iterator();
	}

	/**
	 * Retorna el origen del camino
	 * @return El vertice desde el que se origina el camino
	 */
	public V darOrigen( )
	{
		return origen;
	}
}