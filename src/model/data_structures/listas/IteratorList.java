package model.data_structures.listas;

import java.util.Iterator;
//Iterator

public class IteratorList<E> implements Iterator<E>{

	/**
	 * Nodo en el que se empieza a iterar
	 */
	private boolean esPrimero;

	/**
	 * Nodo ultimo
	 */
	private boolean esUltimo;

	/**
	 * Nodo anterior al que se encuentra el iterador.
	 */
	private Node<E> anterior;

	/**
	 * Nodo en el que se encuentra el iterador.
	 */
	private Node<E> actual;

	/**
	 * Crea un nuevo iterador de lista iniciando en el nodo que llega por parámetro
	 * @param primerNodo el nodo en el que inicia el iterador. nActual != null
	 */
	public IteratorList(Node<E> primerNodo)
	{
		esPrimero = true;
		esUltimo = false;
		actual = primerNodo;
		anterior = null;
	}



	/**
	 * Indica si hay nodo siguiente
	 * true en caso que haya nodo siguiente o false en caso contrario
	 */
	public boolean hasNext() 
	{
		boolean res = false;
		if(actual != null) {
			if(esPrimero && actual != null)
				res=true;
			else  {
				res = actual.darSiguiente() != null;
			}
		}
		return res;
	}

	/**
	 * Indica si hay nodo anterior
	 * true en caso que haya nodo anterior o false en caso contrario
	 */
	public boolean hasPrevious() 
	{
		boolean res = false;
		if(actual != null) {

			if(esUltimo && actual != null)
				res=true;
			else  {
				res = actual.darAnterior() != null;
			}
		}
		return res;
	}

	/**
	 * Devuelve el elemento siguiente de la iteración y avanza.
	 * @return elemento siguiente de la iteración
	 */
	public E next() 
	{
		E elemento = null;
		if(esPrimero == true) {
			elemento = actual.darElemento();
			esPrimero = false;
		}
		else if(hasNext()) {
			anterior = actual;
			actual = (Node<E>) anterior.darSiguiente();
			elemento = actual.darElemento();
		}
		else {
			esUltimo = true;
		}
		return elemento;
	}

	/**
	 * Devuelve el elemento anterior de la iteración y retrocede.
	 * @return elemento anterior de la iteración.
	 */
	public E previous() 
	{
		E elemento = null;

		if(esUltimo == true) {
			elemento = actual.darElemento();
			esUltimo = false;
		}

		else if(hasPrevious()) {
			anterior = actual;
			actual = anterior.darAnterior();
			elemento = actual.darElemento();
		}
		else {
			esPrimero = true;
		}

		return elemento;
	}


	//=======================================================
	// Métodos que no se implementarán
	//=======================================================

	public int nextIndex() 
	{
		throw new UnsupportedOperationException();
	}

	public int previousIndex() 
	{
		throw new UnsupportedOperationException();
	}

	public void remove() 
	{
		throw new UnsupportedOperationException();
	}

	public void set(E e) 
	{
		throw new UnsupportedOperationException();
	}

	public void add(E e) 
	{
		throw new UnsupportedOperationException();		
	}


}
