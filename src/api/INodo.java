/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id: INodo.java,v 1.4 2008/11/04 03:14:12 alf-mora Exp $
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Framework: Cupi2Collections
 * Autor: Alfredo Morales  - 22-oct-2008
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package api;

/**
 * Métodos básicos que debe ofrecer todo nodo de cupi2 Collections, para una estructura<br>
 * que use un iterador sin memoria
 * @param <T> Tipo de datos que maneja el nodo
 */
public interface INodo<T>
{
    /**
     * Retorna el nodo anterior
     * @return El nodo anterior
     */
    public INodo<T> darAnterior( );

    /**
     * Retorna el nodo siguiente
     * @return El nodo siguiente
     */
    public INodo<T> darSiguiente( );

    /**
     * Retorna el elemento del nodo
     * @return El elemento del nodo
     */
    public T darElemento( );
}
