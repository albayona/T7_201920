package model.data_structures.listas;

import java.lang.reflect.Array;
import java.util.Iterator;

public class LinkedList<E> implements Iterable<E>{

	//=======================================================
	// Atributos
	//=======================================================


	private Node<E> headNode;
	private Node<E> lastNode;


	private int size;

	//=======================================================
	// Constructores
	//=======================================================


	//Creates an empty list
	public LinkedList(){
		size = 0;
		headNode = null;
		lastNode = null;
	}

	//Creates a list with one node(with element e)
	public LinkedList(E e) {
		headNode = new Node<E>(e);
		lastNode = headNode;
	}

	//=======================================================
	// Métodos 
	//=======================================================

	//Retorna un iterador ue comienza en el primer nodo de la lista
	public Iterator<E> iterator() {
		return new IteratorList<E>(headNode);
	}

	//Retorna un iterador ue comienza en el ultimo nodo de la lista
	public Iterator<E> iteratorLastNode(){
		return new IteratorList<E>(lastNode);
	}

	//Adds the element at the beginning of the list
	public boolean add(E e) {
		boolean res = false;

		if(e!=null) {
			Node<E> nuevo = new Node<E>(e);
			if(size == 0) {
				headNode = nuevo;
				lastNode = nuevo;
				res=true;
			}
			else {
				headNode.cambiarAnterior(nuevo);
				nuevo.cambiarSiguiente(headNode);
				headNode = nuevo;
			}
			size++;
		}
		else {
			throw new NullPointerException("El elemento es nulo");
		}
		return res;
	}

	public void addAll(int index, LinkedList<E> doublyLinkedList)
	{
		for(Object o : doublyLinkedList)
		{
			this.add((E) o );
			++index;
		}
	}

	//Adds the element at the end of the list
	public boolean addAtEnd(E e) {
		boolean res = false;

		if(e!= null) {
			Node<E> nuevo = new Node<E>(e);
			if(size == 0) {
				headNode = nuevo;
				lastNode = nuevo;
				res=true;
			}
			else {
				lastNode.cambiarSiguiente(nuevo);
				nuevo.cambiarAnterior(lastNode);
				lastNode = nuevo;
				res=true;
			}
			size ++;
		}
		else {
			throw new NullPointerException("El elemento es nulo");
		}
		return res;
	}

	//0 basado
	public E getElement(int index) {
		if(index < 0 || index >= size)  
		{
			throw new IndexOutOfBoundsException("Se está pidiendo el indice: " + index + " y el tamaño de la lista es de " + size);
		}
		Node<E> actual = headNode; 
		int posActual = 0; 
		while(actual != null && posActual < index)
		{
			actual = actual.darSiguiente();
			posActual ++; 
		}
		return actual.darElemento();
	}

	public Node<E> getHeadNode() {
		return headNode;
	}
	public Node<E> getTailNode() {
		return lastNode;
	}

	public E element() {
		return headNode.darElemento();
	}

	public int getSize() {
		return size;
	}


	public E delete(int index) {
		E eliminado = null;
		Node<E> actual = (Node<E>) this.headNode;

		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Se está pidiendo el indice: " + index + " y el tamaño de la lista es de " + size);
		}

		if(index == 0) {
			eliminado = headNode.darElemento();
			headNode = headNode.darSiguiente();
			this.size --;
		}
		else {
			int contador = 0;
			while(contador + 1 < index){
				actual = (Node<E>) actual.darSiguiente();
				contador++;
			}
			eliminado = actual.darSiguiente().darElemento();
			actual.cambiarSiguiente(actual.darSiguiente().darSiguiente());

			if(actual.darSiguiente() == null) {
				lastNode = actual;
			}
			else {
				Node<E> c = (Node<E>) actual.darSiguiente();
				c.cambiarAnterior(actual);
			}
			this.size --;

		}
		return eliminado;
	}

	public boolean contains(Object o) {
		for (E e : this) {
			if(o==null ? e==null : o.equals(e))
				return true;
		}	
		return false;
	}

	public boolean isEmpty() {
		return headNode == null;
	}

	public E[] toArray(Class<E> type) {

		@SuppressWarnings("unchecked")
		E[] elementos = (E[]) Array.newInstance(type, this.size);
		int i = 0;
		Node<E> actual = headNode;
		while(i < size) {
			elementos[i] = actual.darElemento();
			actual = actual.darSiguiente();
			i++;
		}

		return elementos;
	}

}
