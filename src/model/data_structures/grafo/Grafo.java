

package model.data_structures.grafo;

import api.IArco;
import api.IVertice;
import model.data_structures.HashTables.HashTable;
import model.data_structures.listas.LinkedList;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Sacado de: (http://cupi2.uniandes.edu.co)
 * @author Pablo Barvo - Mar 29, 2006
 *
 * @param <K> Tipo del identificador de un v�rtice
 * @param <V> Tipo de datos del elemento del v�rtice
 * @param <A> Tipo de datos del elemento del arco
 */
public class Grafo<K, V extends IVertice<K>, A extends IArco> implements Serializable
{
	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------

	/**
	 * Constante para la serializaci�n
	 */
	private static final long serialVersionUID = 1L; 

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Matiz de adyacencia encargada de mantener los arcos del grafo no dirigido
	 */
	private MatrizAdyacencia<K, V, A> matrizAdyacencia;

	/**
	 * Vertices del grafo no dirigido.
	 */
	//private LinkedList<VerticeGrafo<K, V>> vertices;
	
	private HashTable<K, VerticeGrafo<K, V>> vertices;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Constructor del grafo no dirigido 
	 */
	public Grafo( )
	{
		// Inicializar los atributos del grafo no dirigido
		matrizAdyacencia = new MatrizAdyacencia<K, V, A>( );
		//vertices = new LinkedList<VerticeGrafo<K, V>>( );
		vertices = new HashTable<K, VerticeGrafo<K, V>>();
	}

	// -----------------------------------------------------------------
	// M�todos modificadores
	// -----------------------------------------------------------------

	/**
	 * Crea un nuevo v�rtice en el grafo
	 * @param elemento Elemento del v�rtice
	 * @throws VerticeYaExisteException Si el v�rtice que se quiere agregar ya existe

	}*/
	
	/**
	 * Crea un nuevo v�rtice en el grafo
	 * @param elemento Elemento del v�rtice
	 * @throws VerticeYaExisteException Si el v�rtice que se quiere agregar ya existe
	 */
	public void addVertex( V elemento ) throws VerticeYaExisteException
	{
		// Verificar que el v�rtice no est� registrado en el grafo
		try
		{
			getInfoVertex( elemento.darId( ) );
			throw new VerticeYaExisteException( "El v�rtice ya existe", elemento.darId( ) );
		}
		catch( VerticeNoExisteException e )
		{
			// Agregar el v�rtice a la LinkedList de vertices
			vertices.put( elemento.darId( ), new VerticeGrafo<K, V>( elemento ) );

			// Registrar el v�rtice en la matriz de adyacencia
			matrizAdyacencia.agregarVertice( elemento.darId( ) );
		}
	}

	/**
	 * Elimina el v�rtice identificado con el Identificador especificado
	 * @param idVertice Identificador del v�rtice
	 * @throws VerticeNoExisteException suando el v�rtice especificado no existe

	}*/

	public void eliminarVertice( K idVertice ) throws VerticeNoExisteException
	{
		// Eliminar el vertice de la matriz de adyacencia
		matrizAdyacencia.eliminarVertice( idVertice );
	// Eliminar el vertice de la LinkedList de vertices
		 vertices.delete(idVertice);

	}

	/**
	 * Agrega un nuevo arco al grafo
	 * @param idVerticeOrigen Identificador del v�rtice desde donde sale el arco
	 * @param idVerticeDestino Identificador del v�rtice hasta donde llega el arco
	 * @param infoArco Elemento del arco
	 * @throws VerticeNoExisteException Si alguno de los v�rtices especificados no existe
	 * @throws ArcoYaExisteException Si ya existe un arco entre esos dos v�rtices
	 */
	public void addEdge( K idVerticeOrigen, K idVerticeDestino, A infoArco ) throws VerticeNoExisteException, ArcoYaExisteException
	{
		matrizAdyacencia.agregarArco( infoArco, idVerticeOrigen, idVerticeDestino );
	}

	/**
	 * Elimina el arco que existe entre dos v�rtices
	 * @throws VerticeNoExisteException Cuando el v�rtice de salida no existe
	 * @throws ArcoNoExisteException Cuando el arco no existe
	 */
	public void eliminarArco( K idV1, K idV2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		matrizAdyacencia.eliminarArco( idV1, idV2 );

	}

	// -----------------------------------------------------------------
	// M�todos consultoress
	// -----------------------------------------------------------------

	/**
	 * Devuelve el v�rtice identificado con el identificador especificado
	 * @param idVertice Identificador del v�rtice
	 * @return V�rtice buscado
	 * @throws VerticeNoExisteException Excepci�n generada cuando el v�rtice buscado no existe en el grafo
	 

	}*/
	
	public V getInfoVertex( K idVertice ) throws VerticeNoExisteException
	{
		VerticeGrafo<K, V> vert = vertices.get(idVertice);
		if(vert != null)
			return vert.darInfoVertice();
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );
	}
	
	
	/**
	 * Modifica la informacion del vertice con el id que se pasa por paramentro
	 * @param idVertice Identificador del v�rtice
	 * @throws VerticeNoExisteException Excepci�n generada cuando el v�rtice buscado no existe en el grafo

	} */
	
	public void setInfoVertex( K idVertice, V infoVertex) throws VerticeNoExisteException
	{
		VerticeGrafo<K, V> v = vertices.get(idVertice);
		
		if(v == null)
			throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );

		 vertices.put(idVertice, new VerticeGrafo<K, V>(infoVertex));
		
	} 
	

	/**
	 * Indica si el v�rtice con el identificador dado existe en el grafo
	 * @param idVertice Identificador del v�rtice
	 * @return <code>true</code> si el v�rtice con el identificador dado existe o <code>false</code> en caso contrario

	}*/
	
	public boolean existeVertice( K idVertice )
	{
		return vertices.existsKey(idVertice);
	}


	/**
	 * Retorna los v�rtices del grafo.
	 * @return Los v�rtices del grafo.

	}*/
	public LinkedList<V> darVertices( )
	{
		// Crear la LinkedList
		LinkedList<V> vs = new LinkedList<V>( );
		Iterator t = vertices.keysIterator();
		K key;
		while(t.hasNext()) {
			key = (K) t.next();
			vs.add(vertices.get(key).darInfoVertice());
		}
		// Retornar la LinkedList
		return vs;
	}

	
	/**
	 * Devuelve el orden del grafo.
	 * </p>
	 * El orden de un grafo se define con el n�mero de v�rtices que tiene este
	 * @return Orden del grafo
	public int darOrden( )
	{
		return vertices.getSize( );
	}	 */

	public int darOrden( )
	{
		return vertices.sizeKeys();
	}	 

	/**
	 * Indica si existe un arco entre los v�rtices ingresados por parametros

	 * @return <code>true</code> si existe un arco entre los v�rtices ingresado o <code>false</code> en caso contrario.
	 * @throws VerticeNoExisteException si alguno de los v�rtices ingresados por parametros no existe en el grafo
	 */
	public boolean existeArco( K v1, K v2 ) throws VerticeNoExisteException
	{
		return matrizAdyacencia.existeArco( v1, v2 );
	}

	/**
	 * Retorna el arco entre los v�rtices ingresados por parametros

	 * @return El arco entre los v�rtices ingresados por parametros
	 * @throws VerticeNoExisteException si alguno de los v�rtices ingresados por parametros no existe en el grafo
	 * @throws ArcoNoExisteException si no existe un arco entre esos v�rtices
	 */
	public A getInfoArc( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		return matrizAdyacencia.darArco( v1, v2 );
	}
	
	

	/**
	 * Modifica el arco entre los v�rtices ingresados por parametros
	 * @throws VerticeNoExisteException si alguno de los v�rtices ingresados por parametros no existe en el grafo
	 * @throws ArcoNoExisteException si no existe un arco entre esos v�rtices
	 */
	public void setInfoArc( K v1, K v2, A infoArc ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		matrizAdyacencia.setInfoArco(v1, v2, infoArc);
	}

	/**
	 * Retorna el n�mero de arcos que tiene el grafo
	 * @return el n�mero de arcos que tiene el grafo
	 */
	public int darNArcos( )
	{
		return matrizAdyacencia.darNArcos( );
	}

	/**
	 * Devuelve todos los arcos del grafo
	 * @return Arcos del grafo
	 */
	public LinkedList<A> darArcos( )
	{
		return matrizAdyacencia.darArcos( );
	}

	/**
	 * El peso de un grafo es la suma de los pesos de todos sus arcos
	 */
	public int darPeso( )
	{
		return matrizAdyacencia.darPeso( );
	}

	/**
	 * Devuelve los id de los v�rtice sucedores a un v�rtice ingresado por par�metro
	 * @param idVertice Identificador del v�rtice
	 * @return Los id de los v�rtice sucedores a un v�rtice ingresado por par�metro
	 * @throws VerticeNoExisteException Si el v�rtice especificado no existe

	} */
	public LinkedList<V> darSucesores( K idVertice ) throws VerticeNoExisteException
	{
		// Crear la LinkedList
		LinkedList<V> sucesores = new LinkedList<V>( );

		// Recorrer todos los vertices verificando si son adyacentes y poblar la LinkedList
		
		Iterator t = vertices.keysIterator();
		K key;
		V vertice2;
		while(t.hasNext()) {
			key = (K) t.next();
			vertice2 = vertices.get(key).darInfoVertice();
			if( matrizAdyacencia.existeArco( idVertice, vertice2.darId( ) ) )
			sucesores.add(vertice2);
		}
		// Retornar la LinkedList
		return sucesores;
	} 

	/**
	 * Devuelve los id de los v�rtice sucedores a un v�rtice ingresado por par�metro
	 * @param idVertice Identificador del v�rtice
	 * @return Los id de los v�rtice sucedores a un v�rtice ingresado por par�metro
	 * @throws VerticeNoExisteException Si el v�rtice especificado no existe

	}*/
	public LinkedList<K> adj( K idVertice ) throws VerticeNoExisteException
	{
		// Crear la LinkedList
		LinkedList<K> sucesores = new LinkedList<K>( );

		// Recorrer todos los vertices verificando si son adyacentes y poblar la LinkedList
		Iterator t = vertices.keysIterator();
		K key;
		V vertice2;
		while(t.hasNext()) {
			key = (K) t.next();
			vertice2 = vertices.get(key).darInfoVertice();
			if( matrizAdyacencia.existeArco( idVertice, vertice2.darId( ) ) )
			sucesores.add(vertice2.darId());
		}

		// Retornar la LinkedList
		return sucesores;
	}

	public class DepthFirstSearch
	{
		private boolean[] marked;
		private int count;
		public DepthFirstSearch(Grafo G, int s) throws VerticeNoExisteException {
			marked = new boolean[G.V()];
			dfs(G, s);
		}
		private void dfs(Grafo G, int v) throws VerticeNoExisteException {
			marked[v] = true;
			count++;
			for (K w : (LinkedList<K>)G.adj(v))
				if (!marked[(Integer) w]) dfs(G, (Integer) w);
		}
		public boolean marked(int w)
		{ return marked[w]; }
		public int count()
		{ return count; }
	}

	public int dfs(K v) throws VerticeNoExisteException {
		DepthFirstSearch dfs = new DepthFirstSearch(this, (Integer) v);

		return dfs.count();

	}


	public class CC
	{
		private boolean[] marked;
		private int[] id;
		private int count;
		public CC(Grafo G) throws VerticeNoExisteException {
			marked = new boolean[G.V()];
			id = new int[G.V()];
			for (int s = 0; s < G.V(); s++)
				if (!marked[s])
				{
					dfs(G, s);
					count++;
				}
		}
		private void dfs(Grafo G, int v) throws VerticeNoExisteException {
			marked[v] = true;
			id[v] = count;
			for (K w : (LinkedList<K>)G.adj(v))
				if (!marked[(Integer) w])
					dfs(G, (Integer) w);
		}
		public boolean connected(int v, int w)
		{ return id[v] == id[w]; }
		public int id(int v)
		{ return id[v]; }
		public int count()
		{ return count; }
	}

	public int cc(K v) throws VerticeNoExisteException {
		CC cc = new CC(this);

		return cc.count();
	}
	/**
	 * Verifica si existe un camino entre los dos v�rtices especificados
	 * @param idVerticeOrigen V�rtice de origen
	 * @param idVerticeDestino V�rtice de destino
	 * @return <code>true</code> si hay camino entre los dos v�rtices especificados o <code>false</code> de lo contrario
	 * @throws VerticeNoExisteException Si no existe alguno de los dos v�rtices dados
	 */
	public boolean hayCamino( K idVerticeOrigen, K idVerticeDestino ) throws VerticeNoExisteException
	{
		reiniciarMarcasArcos( );

		return hayCaminoRecursivo( idVerticeOrigen, idVerticeDestino );
	}

	/**
	 * Retorna el camino m�s corto (de menor longitud) entre el par de v�rtices especificados
	 * @param idVerticeOrigen V�rtice en el que inicia el camino
	 * @param idVerticeDestino V�rtice en el que termina el camino
	 * @return El camino m�s corto entre el par de v�rtices especificados
	 * @throws VerticeNoExisteException Si alguno de los dos v�rtices no existe
	 */
	public Camino<K, V, A> darCaminoMasCorto( K idVerticeOrigen, K idVerticeDestino ) throws VerticeNoExisteException
	{
		reiniciarMarcasVertices( );
		reiniciarMarcasArcos( );

		return darCaminoMasCortoRecursivo( idVerticeOrigen, idVerticeDestino );
	}

	/**
	 * Retorna el camino m�s barato (de menor costo) entre el par de v�rtices especificados
	 * @param idVerticeOrigen V�rtice en el que inicia el camino
	 * @param idVerticeDestino V�rtice en el que termina el camino
	 * @return El camino m�s barato entre el par de v�rtices especificados
	 * @throws VerticeNoExisteException Si alguno de los dos v�rtices no existe
	 */
	public Camino<K, V, A> darCaminoMasBarato( K idVerticeOrigen, K idVerticeDestino ) throws VerticeNoExisteException
	{
		reiniciarMarcasVertices( );
		reiniciarMarcasArcos( );

		return darCaminoMasBaratoRecursivo( idVerticeOrigen, idVerticeDestino );
	}

	/**
	 * Indica si hay un ciclo en el grafo que pase por el v�rtice especificado
	 * @param idVertice El identificador del v�rtice
	 * @return <code>true</code> si existe el ciclo o <code>false</code> en caso contrario
	 * @throws VerticeNoExisteException Si el v�rtice especificado no existe
	 */
	public boolean hayCiclo( K idVertice ) throws VerticeNoExisteException
	{
		reiniciarMarcasArcos( );
		return hayCaminoRecursivo( idVertice, idVertice );
	}



	private void reiniciarMarcasVertices( )
	{
		// Elimina todas las marcas presentes en los v�rtices del grafo
		Iterator t = vertices.keysIterator();
		K key;
		while(t.hasNext()) {
			key = (K) t.next();
			vertices.get(key).desmarcar();
		}

	}	 
	


	/**
	 * Borra las marcas de todos los arcos del grafo
	 */
	private void reiniciarMarcasArcos( )
	{
		matrizAdyacencia.reiniciarMarcas( );
	}

	/**
	 * Marca un v�rtice
	 * @param idVertice Id del v�rtice que se quiere marcar
	 * @throws VerticeNoExisteException Si el v�rtice que se quiere marcar no existe
	private void marcarVertice( K idVertice ) throws VerticeNoExisteException
	{
		for( int i = 0; i < vertices.getSize( ); i++ )
		{
			VerticeGrafo<K, V> vert = vertices.getElement( i );
			if( vert.darInfoVertice( ).darId( ).equals( idVertice ) )
			{
				vert.marcar( );
				return;
			}
		}
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );
	}	 */

	private void marcarVertice( K idVertice ) throws VerticeNoExisteException
	{
		VerticeGrafo<K, V> v = vertices.get(idVertice);
		if(v!=null)
			v.marcar();
		else
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );
	}	

	/**
	 * Marca un arco
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 * @throws ArcoNoExisteException Si no existe ning�n arco entre los v�rtices seleccionados
	 */
	private void marcarArco( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		matrizAdyacencia.marcarArco( v1, v2 );
	}

	/**
	 * Desmarca un v�rtice
	 * @param idVertice Id del v�rtice que se quiere desmarcar
	 * @throws VerticeNoExisteException Si el v�rtice que se quiere desmarcar no existe
	private void desmarcarVertice( K idVertice ) throws VerticeNoExisteException
	{
		for( int i = 0; i < vertices.getSize( ); i++ )
		{
			VerticeGrafo<K, V> vert = vertices.getElement( i );
			if( vert.darInfoVertice( ).darId( ).equals( idVertice ) )
			{
				vert.desmarcar( );
				return;
			}
		}
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );
	}	 */

	private void desmarcarVertice( K idVertice ) throws VerticeNoExisteException
	{
		VerticeGrafo<K, V> v = vertices.get(idVertice);
		if(v!=null)
			v.desmarcar();
		else
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );
	}	 


	/**
	 * Verifica si un v�rtice est� marcado
	 * @param idVertice Id del v�rtice a consultar
	 * @return <code>true</code> si el v�rtice seleccionado est� marcado o <code>false</code> en caso contrario
	 * @throws VerticeNoExisteException Si el v�rtice seleccionado no existe
	private boolean estaMarcadoVertice( K idVertice ) throws VerticeNoExisteException
	{
		for( int i = 0; i < vertices.getSize( ); i++ )
		{
			VerticeGrafo<K, V> vert = vertices.getElement( i );
			if( vert.darInfoVertice( ).darId( ).equals( idVertice ) )
			{
				return vert.estaMarcado( );
			}
		}
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );
	}	 */
	private boolean estaMarcadoVertice( K idVertice ) throws VerticeNoExisteException
	{
		VerticeGrafo<K, V> v = vertices.get(idVertice);
		if(v!=null)
			return v.estaMarcado();
		else
		throw new VerticeNoExisteException( "El v�rtice buscado no existe", idVertice );

	}

	/**
	 * Verifica si un arco est� marcado
	 * @param v1 primer v�rtice conectado por el arco
	 * @param v2 segundo v�rtice conectado por el arco
	 * @return <code>true</code> si el arco que conecta a los v�rtices seleccionados est� marcado o <code>false</code> en caso contrario
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 * @throws ArcoNoExisteException Si no existe ning�n arco entre los v�rtices seleccionados
	 */
	private boolean estaMarcadoArco( K v1, K v2 ) throws VerticeNoExisteException, ArcoNoExisteException
	{
		return matrizAdyacencia.estaMarcado( v1, v2 );
	}

	/**
	 * Cacula, de forma recursiva, el camino m�s corto entre dos v�rtices.
	 * @param v1 id del v�rtice origen del camino
	 * @param v2 id del v�rtice destino del camino
	 * @return El camino m�s corto entre dos v�rtices.
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 */
	private Camino<K, V, A> darCaminoMasCortoRecursivo( K v1, K v2 ) throws VerticeNoExisteException
	{
		try
		{
			if( matrizAdyacencia.existeArco( v1, v2 ) && !estaMarcadoArco( v1, v2 ) )
			{
				Camino<K, V, A> camino = new Camino<K, V, A>( getInfoVertex( v1 ) );

				camino.agregarArcoFinal( getInfoVertex( v2 ), matrizAdyacencia.darArco( v1, v2 ) );

				return camino;
			}
			else
			{
				marcarVertice( v1 );
				LinkedList<V> sucesores = darSucesores( v1 );
				Camino<K, V, A> camino = null;
				V sucesorCorto = null;
				for( int i = 0; i < sucesores.getSize( ); i++ )
				{
					V vert = sucesores.getElement( i );
					if( !estaMarcadoVertice( vert.darId( ) ) )
					{
						marcarArco( v1, vert.darId( ) );
						Camino<K, V, A> camAux = darCaminoMasCortoRecursivo( vert.darId( ), v2 );
						if( camAux != null && ( camino == null || camAux.darLongitud() < camino.darLongitud() ) )
						{
							camino = camAux;
							sucesorCorto = vert;
						}
					}
				}
				desmarcarVertice( v1 );

				if( camino == null )
					return null;
				else
				{
					camino.agregarArcoComienzo( getInfoVertex( v1 ), matrizAdyacencia.darArco( v1, sucesorCorto.darId( ) ) );
					return camino;
				}
			}
		}
		catch( ArcoNoExisteException e )
		{
			// Esto no deber�a suceder
			return null;
		}

	}
	/**
	 * Cacula, de forma recursiva, el camino m�s corto entre dos v�rtices.
	 * @param v1 id del v�rtice origen del camino
	 * @param v2 id del v�rtice destino del camino
	 * @return El camino m�s corto entre dos v�rtices.
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 */
	private Camino<K, V, A> darCaminoMasBaratoRecursivo( K v1, K v2 ) throws VerticeNoExisteException
	{
		try
		{
			if( matrizAdyacencia.existeArco( v1, v2 ) && !estaMarcadoArco( v1, v2 ) )
			{
				Camino<K, V, A> camino = new Camino<K, V, A>( getInfoVertex( v1 ) );

				camino.agregarArcoFinal( getInfoVertex( v2 ), matrizAdyacencia.darArco( v1, v2 ) );

				return camino;
			}
			else
			{
				marcarVertice( v1 );
				LinkedList<V> sucesores = darSucesores( v1 );
				Camino<K, V, A> camino = null;
				V sucesorBarato = null;
				A arcoBarato = null;
				for( int i = 0; i < sucesores.getSize( ); i++ )
				{
					V vert = sucesores.getElement( i );

					if( !estaMarcadoVertice( vert.darId( ) ) )
					{
						marcarArco( v1, vert.darId( ) );
						Camino<K, V, A> camAux = darCaminoMasBaratoRecursivo( vert.darId( ), v2 );
						if( camAux != null && ( camino == null || camAux.darCosto( ) + getInfoArc( v1, vert.darId( ) ).darPeso( ) < camino.darCosto( ) + arcoBarato.darPeso( ) ) )
						{
							camino = camAux;
							sucesorBarato = vert;
							arcoBarato = getInfoArc( v1, vert.darId( ) );
						}
					}
				}
				desmarcarVertice( v1 );

				if( camino == null )
					return null;
				else
				{
					camino.agregarArcoComienzo( getInfoVertex( v1 ), matrizAdyacencia.darArco( v1, sucesorBarato.darId( ) ) );
					return camino;
				}
			}
		}
		catch( ArcoNoExisteException e )
		{
			// Esto no deber�a suceder
			return null;
		}
	}

	/**
	 * Busca recursivamente la existencia de camino entre 2 v�rtices tomando en cuenta que los arcos por los que ya se pas� est�n marcados.
	 * @param vertActual v�rtice a partir del cual se quiere encontrar el camino
	 * @param vertCierre v�rtice al cual hay que llegar
	 * @return <code>true</code> si encontro un camino desde <code>vertActual</code> hasta <code>vertCierre</code> sin pasar por arcos marcados o <code>false</code> en
	 *         caso contrario
	 * @throws VerticeNoExisteException Si alguno de los v�rtices seleccionados no existe
	 */
	private boolean hayCaminoRecursivo( K vertActual, K vertCierre ) throws VerticeNoExisteException
	{
		// Recuperar los sucesores del vertice actual
		LinkedList<V> sucesores = darSucesores( vertActual );

		for( int i = 0; i < sucesores.getSize( ); i++ )
		{
			try
			{
				V sucesor = sucesores.getElement( i );

				// Solo tomo en cuenta los arcos no marcados
				if( !estaMarcadoArco( vertActual, sucesor.darId( ) ) )
				{
					// Si alguno de los sucesores es el v�rtice de cierro del ciclo, y el arco no est� marcado, encontramos el ciclo
					if( sucesor.darId( ).equals( vertCierre ) )
						return true;
					// Si no es as�, sigo buscando el ciclo recursivamente usando los arcos no marcados y voy marcando los arcos por los que paso
					else
					{
						marcarArco( vertActual, sucesor.darId( ) );
						if( hayCaminoRecursivo( sucesor.darId( ), vertCierre ) )
							return true;
					}
				}
			}
			catch( ArcoNoExisteException e )
			{
				// Esto no deberia suceder
			}
		}
		// Si ninguno de mis sucesores puede cerrar el ciclo es porque este no existe
		return false;
	}

	/**
	 * Marca a todos los v�rtices a los que se pueda llegar desde un v�rtice dado.
	 * @param idVertice Id del v�rtice a partir del cual se quiere hacer la marcaci�n.
	 * @throws VerticeNoExisteException Si el v�rtice a partir del cual se quiere hacer la marcaci�n no existe.
	 */
	private void marcarSucesoresAProfundidad( K idVertice ) throws VerticeNoExisteException
	{
		marcarVertice( idVertice );

		LinkedList<V> sucesores = darSucesores( idVertice );
		for( int i = 0; i < sucesores.getSize( ); i++ )
		{
			K idSucesor = sucesores.getElement( i ).darId( );
			if( !estaMarcadoVertice( idSucesor ) )
			{
				marcarSucesoresAProfundidad( idSucesor );
			}
		}

	}
	/**
	 * Retorna el numero de vertices en el grafo 
	 * @return
	 */
	public int V() {
		return this.darOrden();
	}
	
	/**
	 * Retorna el numero de arcos en el grafo
	 * @return
	 */
	public int E() {
		return this.darNArcos();
	}

}