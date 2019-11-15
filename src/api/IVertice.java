package api;


import java.io.Serializable;

/**
 * Sacado de (http://cupi2.uniandes.edu.co)
 * Interfaz utilizada para implementar el elemento de un v�rtice
 * @param <ID_VERT> Tipo del id del v�rtice
 */
public interface IVertice<K> extends Serializable
{
	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------

	/**
	 * Devuelve el ID del v�rtice
	 * @return Identificador del v�rtice
	 */
	public K darId( );
}