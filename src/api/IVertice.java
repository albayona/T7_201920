package api;


import java.io.Serializable;

/**
 * Sacado de (http://cupi2.uniandes.edu.co)
 * Interfaz utilizada para implementar el elemento de un vértice
 * @param <ID_VERT> Tipo del id del vértice
 */
public interface IVertice<K> extends Serializable
{
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Devuelve el ID del vértice
	 * @return Identificador del vértice
	 */
	public K darId( );
}