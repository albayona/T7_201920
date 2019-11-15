package model.data_structures.listas;

public class Node<E> {

	//--------------------------------------------------------
	//Atributos
	//--------------------------------------------------------
	
	/**
	 * Elemento almacenado en el nodo.
	 */
	protected E elemento;
	
	/**
	 * Siguiente nodo.
	 */
	private Node<E> siguiente; 
	
	/**
	 * Anterior nodo.
	 */
	private Node<E> anterior;
	
	//--------------------------------------------------------
	//Constructores
	//--------------------------------------------------------
	
	/**
	 * Constructor del nodo.
	 * @param elemento El elemento que se almacenará en el nodo. elemento != null
	 */
	public Node(E elemento)
	{
		this.elemento = elemento;
	}
	
	//--------------------------------------------------------
	//Metodos
	//--------------------------------------------------------
	
	/**
	 * Método que cambia el siguiente nodo.
	 * <b>post: </b> Se ha cambiado el siguiente nodo
	 * @param siguiente El nuevo siguiente nodo
	 */
	public void cambiarSiguiente(Node<E> siguiente)
	{
		this.siguiente = siguiente;
	}
	
	/**
	 * Método que retorna el elemento almacenado en el nodo.
	 * @return El elemento almacenado en el nodo.
	 */
	public E darElemento()
	{
		return elemento;
	}
	
	/**
	 * Cambia el elemento almacenado en el nodo.
	 * @param elemento El nuevo elemento que se almacenará en el nodo.
	 */
	public void cambiarElemento(E elemento)
	{
		this.elemento = elemento;
	}
	
	
	/**
	 * Método que retorna el siguiente nodo.
	 * @return Siguiente nodo
	 */
	public Node<E> darSiguiente()
	{
		return siguiente;
	}
	
	/**
	 * Método que retorna el nodo anterior.
	 * @return Nodo anterior.
	 */
	public Node<E> darAnterior()
	{
		return anterior;
	}
	
	/**
	 * Método que cambia el nodo anterior por el que llega como parámetro.
	 * @param anterior Nuevo nodo anterior.
	 */
	public void cambiarAnterior(Node<E> anterior)
	{
		this.anterior = anterior;
	}


}
