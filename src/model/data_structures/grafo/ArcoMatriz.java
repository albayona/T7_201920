package model.data_structures.grafo;

import api.IArco;

/**
 * Sacado de (http://cupi2.uniandes.edu.co)
 * Representa una casilla de una matriz de adyacencia
 * @param <A> Tipo de datos del elemento del arco
 */
public class ArcoMatriz<K, A extends IArco>
{
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Marca del arco
	 */
	private boolean marcado;

	/**
	 * Arco
	 */
	private A arco;

	/**
	 * Primer vértice unido por el arco
	 */
	private K vertice1;

	/**
	 * Primer vértice unido por el arco
	 */
	private K vertice2;

	// -----------------------------------------------------------------
	// Constructores
	// ----------------------------------------------------------------- 

	/**
	 * Construye una posición de una matriz de ayacencia a partir de un <code>IArco</code>
	 * 
	 * @param arco Información del arco
	 */
	public ArcoMatriz( A arco, K vertice1, K vertice2 )
	{
		marcado = false;
		this.arco = arco;
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
	}

	// -----------------------------------------------------------------
	// Métodos
	// ----------------------------------------------------------------- 

	/**
	 * Retorna la marca del arco
	 * 
	 * @return <code>true</code> si el arco está marcado o <code>false</code> en caso contrario
	 */
	public boolean marcado( )
	{
		return marcado;
	}

	/**
	 * Marca el arco
	 */
	public void marcar( )
	{
		marcado = true;
	}

	/**
	 * Elimina la marca del vertice
	 */
	public void desmarcar( )
	{
		marcado = false;
	}

	/**
	 * Retorna el arco contenido
	 * @return La información de arco contenido
	 */
	public A darArco( )
	{
		return arco;
	}
	/**
	 * Cambia el arco contenido
	 */
	public void setArco( A arco)
	{
		this.arco = arco;
	}

	/**
	 * Retorna el primer vértice unido por el arco.
	 * @return el primer vértice unido por el arco.
	 */
	public K darVertice1() {
		return vertice1;
	}

	/**
	 * Retorna el segundo vértice unido por el arco.
	 * @return el segundo vértice unido por el arco.
	 */
	public K darVertice2() {
		return vertice2;
	}
}