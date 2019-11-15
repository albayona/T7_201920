package model.data_structures.grafo;

/**
 * Excepción utilizada para informar que el vértice especificado ya existe en el grafo
 * Sacado de (http://cupi2.uniandes.edu.co)
 */
public class VerticeYaExisteException extends Exception
{
	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	/**
	 * Constante para la serialización
	 */
	private static final long serialVersionUID = 1L; 

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Identificador del vértice que ya existe en el grafo
	 */
	private Object idVertice;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor de la excepción
	 * @param mensaje Mensaje de error
	 * @param id Identificador del vértice que ya existe
	 */
	public VerticeYaExisteException( String mensaje, Object id )
	{
		super( mensaje );
		idVertice = id;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Devuelve el identificador del vértice que ya existe
	 * @return identificador del vértice que ya existe
	 */
	public Object darIdentificador( )
	{
		return idVertice;
	}
}
