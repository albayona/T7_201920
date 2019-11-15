package model.data_structures.grafo;

/**
 * Excepción lanzada cuando el arco especificado no existe
 * Sacado de: (http://cupi2.uniandes.edu.co)
 */
public class ArcoNoExisteException extends Exception
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
	 * Identificador del vértice desde donde debería salir el arco
	 */
	private Object origen;

	/**
	 * Identificador del vértice hasta donde debería llegar el arco
	 */
	private Object destino;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor de la excepción
	 * @param mensaje Mensaje de error
	 * @param idDesde Identificador del vértice desde donde sale el arco
	 * @param idHasta Identificador del vértice hasta donde llega el arco
	 */
	public ArcoNoExisteException( String mensaje, Object idDesde, Object idHasta )
	{
		super( mensaje );
		origen = idDesde;
		destino = idHasta;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Devuelve el identificador del vértice desde donde debe salir el arco
	 * @return identificador del vértice desde donde debe salir el arco
	 */
	public Object darOrigen( )
	{
		return origen;
	}

	/**
	 * Devuelve el identificador del vértice hasta donde debe llegar el arco
	 * @return identificador del vértice hasta donde debe llegar el arco
	 */
	public Object darDestino( )
	{
		return destino;
	}
}