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
	 * Primer v�rtice unido por el arco
	 */
	private K vertice1;

	/**
	 * Primer v�rtice unido por el arco
	 */
	private K vertice2;

	// -----------------------------------------------------------------
	// Constructores
	// ----------------------------------------------------------------- 

	/**
	 * Construye una posici�n de una matriz de ayacencia a partir de un <code>IArco</code>
	 * 
	 * @param arco Informaci�n del arco
	 */
	public ArcoMatriz( A arco, K vertice1, K vertice2 )
	{
		marcado = false;
		this.arco = arco;
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
	}

	// -----------------------------------------------------------------
	// M�todos
	// ----------------------------------------------------------------- 

	/**
	 * Retorna la marca del arco
	 * 
	 * @return <code>true</code> si el arco est� marcado o <code>false</code> en caso contrario
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
	 * @return La informaci�n de arco contenido
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
	 * Retorna el primer v�rtice unido por el arco.
	 * @return el primer v�rtice unido por el arco.
	 */
	public K darVertice1() {
		return vertice1;
	}

	/**
	 * Retorna el segundo v�rtice unido por el arco.
	 * @return el segundo v�rtice unido por el arco.
	 */
	public K darVertice2() {
		return vertice2;
	}
}