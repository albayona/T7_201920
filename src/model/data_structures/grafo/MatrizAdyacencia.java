package model.data_structures.grafo;

import api.IArco;
import api.IVertice;
import model.data_structures.HashTables.HashTable;
import model.data_structures.listas.LinkedList;

/**
 * Sacado de (http://cupi2.uniandes.edu.co)
 * @author Juan Erasmo G�mez - Mar 5, 2008
 *
 * @param <K>
 * @param <V>
 * @param <A>
 */
public class MatrizAdyacencia<K, V extends IVertice<K>, A extends IArco>
{

	// -----------------------------------------------------------------
	// Atributos
	// ------------------------------------------------------------------

	/**
	 * Capacidad actual de la matriz.
	 */
	private int tamMatriz;

	/**
	 * Matriz de arcos.
	 */
	private ArcoMatriz<K,A>[][] matriz;

	/**
	 * N�mero de casillas que van a aumentar en la matriz a medida que esta llega a su l�mite de espacio
	 */
	private int delta;

	/**
	 * Tabla de hashing que permite encontrar, en O(1), el �ndice de un v�rtice en la matriz
	 * 
	 */
	private HashTable<K, Integer> posiciones;

	// -----------------------------------------------------------------
	// Constructores
	// ------------------------------------------------------------------

	/**
	 * Constructor por defecto
	 */
	@SuppressWarnings("unchecked")
	public MatrizAdyacencia( )
	{
		// Inicializar los atributos de la clase
		tamMatriz = 10;
		delta = 20;

		// iniciarlizar la matriz
		matriz = new ArcoMatriz[10][10];

		// inicializar la tabla de hashing
		posiciones = new HashTable<K, Integer>( );
	}

	/**
	 * Constructor por par�metros
	 * @param tamInicial
	 * @param delta
	 */
	@SuppressWarnings("unchecked")
	public MatrizAdyacencia( int tamInicial, int delta )
	{
		// Inicializar los atributos de la clase
		tamMatriz = tamInicial;
		this.delta = delta;

		// Inicializar la matriz
		matriz = new ArcoMatriz[tamInicial][tamInicial];

		// inicializar la tabla de hashing
		posiciones = new HashTable<K, Integer>( );
	}

	// -----------------------------------------------------------------
	// M�todos modificadores
	// ------------------------------------------------------------------

	/**
	 * Registra un v�rtice en la matriz de adyacencia
	 */
	public void agregarVertice( K idVertice )
	{
		// Agregar el v�rtice a la tabla de hashing
		posiciones.put( idVertice, darNVertices( ) );

		// Ajustar el tama�o de la tabla de ser necesario
		if( tamMatriz < darNVertices( ) )
		{
			redimencionarMatriz( );
		}
	}

	/**
	 * Elimina de la matriz la informaci�n relativa a un v�rtice.
	 * @param idVertice Id del v�rtice que se quiere eliminar.
	 * @throws VerticeNoExisteException Si el v�rtice seleccionado no existe.
	 */
	public void eliminarVertice( K idVertice ) throws VerticeNoExisteException
	{
		// Recuperar la posici�n del v�rtice dentro de la matriz
		Integer v = posiciones.get( idVertice );

		// Verificar que el v�rtice est� registrado en la matriz
		if( v == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v );

		// Eliminar la columna que corresponde a idVertice en la matriz
		for( int c = v; c < darNVertices( ) - 1; c++ )
		{
			for( int f = 0; f < darNVertices( ); f++ )
			{
				matriz[ f ][ c ] = matriz[ f ][ c + 1 ];
			}
		}

		// Eliminar la fila que corresponde a idVertice en la matriz
		for( int f = v; f < darNVertices( ) - 1; f++ )
		{
			for( int c = 0; c < darNVertices( ); c++ )
			{
				matriz[ f ][ c ] = matriz[ f + 1 ][ c ];
			}
		}

		// Eliminar el vertice de la tabla de hashing
		posiciones.delete(idVertice );
	}

	/**
	 * Agrega un arco a la matriz de adyacencia
	 * @param arco Arco que se quiere agregar
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @throws VerticeNoExisteException Si alguno de los v�rtices conectados por el arco no est� registrado en la matriz
	 * @throws ArcoYaExisteException Si ya existe un arco conectando esos dos v�rtices
	 */
	@SuppressWarnings("unchecked")
	public void agregarArco( A arco, K v1, K v2 ) throws VerticeNoExisteException, ArcoYaExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Verificar que no exista un arco entre esos v�rtices
		if( matriz[ posV1 ][ posV2 ] != null )
			throw new ArcoYaExisteException( "Ya existe un arco entre esos dos v�rtices", v1, v2 );

		// Ingresar el arco en la matriz manteniendo la simetr�a de esta
		matriz[ posV1 ][ posV2 ] = matriz[ posV2 ][ posV1 ] = new ArcoMatriz( arco, v1, v2 );
	}

	/**
	 * Elimina un arco de la matriz de adyacencia
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @throws VerticeNoExisteException Si alguno de los v�rtices conectados por el arco no est� registrado en la matriz
	 * @throws ArcoNoExisteException Si no existe un arco conectando esos dos v�rtices
	 */
	public void eliminarArco( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Verificar que exista un arco entre esos v�rtices
		if( matriz[ posV1 ][ posV2 ] == null )
			throw new ArcoNoExisteException( "No existe un arco entre esos dos v�rtices", v1, v2 );

		// Ingresar el arco en la matriz manteniendo la simetr�a de esta
		matriz[ posV1 ][ posV2 ] = matriz[ posV2 ][ posV1 ] = null;

	}

	/**
	 * Marca un arco
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 * @throws ArcoNoExisteException Si no existe ning�n arco entre los v�rtices seleccionados
	 */
	public void marcarArco( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Marca el arco
		if( matriz[ posV1 ][ posV2 ].darArco( ) != null )
			matriz[ posV1 ][ posV2 ].marcar( );
		else
			throw new ArcoNoExisteException( "No existe un arco entre los v�rtices seleccionados", v1, v2 );
	}

	/**
	 * Desmarca un arco
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 * @throws ArcoNoExisteException Si no existe ning�n arco entre los v�rtices seleccionados
	 */
	public void desmarcarArco( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Desmarca el arco
		if( matriz[ posV1 ][ posV2 ].darArco( ) != null )
			matriz[ posV1 ][ posV2 ].desmarcar( );
		else
			throw new ArcoNoExisteException( "No existe un arco entre los v�rtices seleccionados", v1, v2 );
	}

	/**
	 * Desmarca todos los arcos
	 */
	public void reiniciarMarcas( )
	{
		for( int c = 0; c < darNVertices( ) - 1; c++ )
		{
			for( int f = 0; f < darNVertices( ); f++ )
			{
				if( matriz[ f ][ c ] != null )
					matriz[ f ][ c ].desmarcar( );
			}
		}
	}

	// -----------------------------------------------------------------
	// M�todos consultores
	// ------------------------------------------------------------------

	/**
	 * Verifica si existe un arco entre dos v�rtices
	 * @param v1 id del primer v�rtice
	 * @param v2 id del segundo v�rtice
	 * @return <code>true</code> si existe un arco entre los v�rtices o <code>false</code> en caso contrario
	 * @throws VerticeNoExisteException Si alguno de los v�rtices ingresados por parametros no est� registrado en la matriz
	 */
	public boolean existeArco( K v1, K v2 ) throws VerticeNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Retornar la existencia del v�rtice
		return matriz[ posV1 ][ posV2 ] != null;
	}

	/**
	 * Retorna el arco que une dos v�rtices del grafo
	 * @param v1 id del primer v�rtice conectado por el grafo
	 * @param v2 id del segundo v�rtice conectado por el grafo
	 * @return el arco que une dos v�rtices del grafo
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no esta registrado en la matriz
	 * @throws ArcoNoExisteException Si no existe un arco entre los v�rtices ingresados por parametro
	 */
	public A darArco( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Retornar la existencia del v�rtice
		if( matriz[ posV1 ][ posV2 ].darArco( ) != null )
			return ( A )matriz[ posV1 ][ posV2 ].darArco( );
		else
			throw new ArcoNoExisteException( "No existe un arco entre los v�rtices seleccionados", v1, v2 );

	}

	
	public void setInfoArco( K v1, K v2, A arco ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Retornar la existencia del v�rtice
		if( matriz[ posV1 ][ posV2 ].darArco( ) != null )
			matriz[ posV1 ][ posV2 ].setArco(arco);
		else
			throw new ArcoNoExisteException( "No existe un arco entre los v�rtices seleccionados", v1, v2 );

	}
	
	/**
	 * Retorna los arcos presentes en la matriz
	 * @return Los arcos presentes en la matriz
	 */
	public LinkedList<A> darArcos( )
	{
		// Crear la lista
		LinkedList<A> arcos = new LinkedList<A>( );

		// Recorrer la matriz y poblar la lista
		for( int f = 0; f < darNVertices( ); f++ )
		{
			for( int c = f; c < darNVertices( ); c++ )
			{
				if( matriz[ f ][ c ] != null )
					arcos.add( ( A )matriz[ f ][ c ].darArco( ) );
			}
		}

		// Retornar la lista
		return arcos;
	}

	/**
	 * Retorna una lista con los objetos que representan un arco en la matriz.
	 * @return una lista con los objetos que representan un arco en la matriz.
	 */
	protected LinkedList<ArcoMatriz<K, A>> darArcosMatriz( )
	{
		// Crear la lista
		LinkedList<ArcoMatriz<K, A>> arcos = new LinkedList<ArcoMatriz<K, A>>( );

		// Recorrer la matriz y poblar la lista
		for( int f = 0; f < darNVertices( ); f++ )
		{
			for( int c = f; c < darNVertices( ); c++ )
			{
				if( matriz[ f ][ c ] != null )
					arcos.add( (model.data_structures.grafo.ArcoMatriz<K, A> )matriz[ f ][ c ] );
			}
		}

		// Retornar la lista
		return arcos;
	}

	/**
	 * Retorna el n�mero de arcos que existen en la matriz
	 * @return el n�mero de arcos que existen en la matriz
	 */
	public int darNArcos( )
	{
		return darArcos( ).getSize( );
	}

	/**
	 * Retorna la suma de los pesos de todos los arcos presentes en la matriz
	 * @return La suma de los pesos de todos los arcos presentes en la matriz
	 */
	public int darPeso( )
	{
		int peso = 0;
		for( int f = 0; f < darNVertices( ); f++ )
		{
			for( int c = f; c < darNVertices( ); c++ )
			{
				if( matriz[ f ][ c ] != null )
					peso += matriz[ f ][ c ].darArco( ).darPeso( );
			}
		}
		return peso;
	}

	/**
	 * Verifica si un arco est� marcado
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @return <code>true</code> si el arco que conecta a los v�rtices seleccionados est� marcado o <code>false</code> en caso contrario
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 * @throws ArcoNoExisteException Si no existe ning�n arco entre los v�rtices seleccionados
	 */
	public boolean estaMarcado( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		// Recuperar las posiciones de los v�rtices dentro de la matriz
		Integer posV1 = posiciones.get( v1 );
		Integer posV2 = posiciones.get( v2 );

		// Verificar la existencia de los v�rtices
		if( posV1 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v1 );
		if( posV2 == null )
			throw new VerticeNoExisteException( "El v�rtice seleccionado no existe", v2 );

		// Retornar la existencia del v�rtice
		if( matriz[ posV1 ][ posV2 ].darArco( ) != null )
			return matriz[ posV1 ][ posV2 ].marcado( );
		else
			throw new ArcoNoExisteException( "No existe un arco entre los v�rtices seleccionados", v1, v2 );
	}

	// -----------------------------------------------------------------
	// M�todos privados
	// ------------------------------------------------------------------

	/**
	 * Aumenta la capacidad de la matriz
	 */
	@SuppressWarnings("unchecked")
	private void redimencionarMatriz( )
	{
		// Instanciar la nueva matriz en una variable auxiliar
		ArcoMatriz[][] aux = matriz;
		matriz = new ArcoMatriz[tamMatriz + delta][tamMatriz + delta];

		// Copiar valores de la matriz actual en la matriz auxiliar
		for( Integer f = 0; f < tamMatriz; f++ )
		{
			for( Integer c = 0; c < tamMatriz; c++ )
			{
				matriz[ f ][ c ] = aux[ f ][ c ];
			}
		}

		// Actualizar la capacidad de la matriz
		tamMatriz += delta;
	}

	/**
	 * Retorna el n�mero de v�rtices registrados en la matriz
	 * @return El n�mero de v�rtices registrados en la matriz
	 */
	private int darNVertices( )
	{
		return posiciones.sizeKeys();
	}

}
