package model.data_structures.HashTables;

import java.util.ArrayList;
import java.util.Iterator;

public class HashTable<K, V> {
	
	 /**
     * Tamanio inicial del arreglo de listas encadenadas
     */
    public static final int TAM_TABLA_HASHING = 50;

	//--------------------------------------------------------
	//Atributos
	//--------------------------------------------------------

	private int keysNumber;

	private int size;

	private K[] keys;

	private V[] values;
	
	private double loadFactor;

	//--------------------------------------------------------
	//Constructores
	//--------------------------------------------------------

	@SuppressWarnings("unchecked")
	public HashTable() {
		this.size = TAM_TABLA_HASHING;
		keys = (K[]) new Object[size];
		values = (V[]) new Object[size]; 
		loadFactor = 0.75;
	}
	
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		this.size = size;
		keys = (K[]) new Object[size];
		values = (V[]) new Object[size]; 
		loadFactor = 0.75;
	}
	

	//--------------------------------------------------------
	//Metodos publicos
	//--------------------------------------------------------

	/**
	 * Retorna el valor asociado a la llave o null si la llave no  tiene ningun valor asociado.  
	 * @param key La llave asociada al valor, key != null
	 * @return el valor buscado, o null si no existe
	 */
	public V get(K key) {
		V valor = null;
		int n = 0;
		for (int i = hash(key); keys[i] != null && valor==null; i = getNextFreeIndex(i)) {
			if (keys[i].equals(key)) {
				valor = (V)values[i];
			}
			if(n>=size)
				break;
			n++;
		}
		return valor;
	}

	/**
	 * Inserta el valor y la llave que se pasan por paramentro en el hashTable. 
	 * @param key Llave asociada al objeto. key != null
	 * @param value Valor que se quiere insertar. value!= null
	 */
	public void put(K key, V value) {
		if ((double)(keysNumber/ size) >= loadFactor) {
			rehash(2*size);     
		}
		int i;  
		i = hash(key);
		while(keys[i] != null) {
			if (keys[i].equals(key)) { 
				values[i] = value; 
				return; 
			}      
			i = getNextFreeIndex(i);
		}
		keys[i] = key;      
		values[i] = value;       
		keysNumber++; 
	}

	/**
	 * Determina si una llave existe en el arreglo keys
	 * @param key. La llave que se desea buscar. key!=null
	 * @return true, si la llave existe, false de lo contrario
	 */
	public boolean existsKey(K key) {
		if(keysNumber == 0)
			return false;

		for(K keyTemp : keys) {
			 if(keyTemp != null) {	
				if(keyTemp.equals(key))
					return true;
			 }
		}
		return false;
	}

	/**
	 * Elimina un valor y su llave asociada del HashTable. Retorna el valor elimiando
	 * @param key La llave asociada al valor que se desea eliminar, key != null
	 * @return el valor eliminado, o null si no existe
	 */
	public V delete(K key) {
		V deleted;;
		
		if (!existsKey(key)) {
			deleted = null;  
		}
		else {
			int i = hash(key);   
			while (!key.equals(keys[i])) {
				i = getNextFreeIndex(i);   
			}
			deleted = values[i];

			changeValuePositions(i);
		}
		return deleted;

	}
	

	public Iterator<K> keysIterator(){
		ArrayList<K> list = new ArrayList<K>();
		for(K key : keys) {
			if(key != null)
			list.add(key);
		}
		return list.iterator();
	}
	
	public int sizeKeys() {
		return keysNumber;
	}
	
	public int maxSize() {
		return size;
	}
	
	public double getLoaFactor() {
		return loadFactor;
	}
	
	public void setLoadFactor(double loadF) {
		this.loadFactor = loadF;
	}

	//--------------------------------------------------------
	//Metodos privados/auxiliares
	//--------------------------------------------------------

	private int hash(K key) {  
		return (key.hashCode() & 0x7fffffff) % size; 
	}

	private void rehash(int newSize) {
		 HashTable<K, V> nuevo = new HashTable<K, V>(newSize);    
		 for (int i = 0; i < size; i++) {
			 if (keys[i] != null)           
				 nuevo.put(keys[i], values[i]);    
		 }
		 keys = nuevo.keys;    
		 values = nuevo.values;    
		 size = nuevo.size;
	}
	
	private int getNextFreeIndex(int i) {
		return (i + 1) % size;
	}
	
	private void changeValuePositions(int i) {
		keys[i] = null;   
		values[i] = null;   
		i = getNextFreeIndex(i);   
		while (keys[i] != null)   {      
			K  keyCambiar = keys[i];      
			V valCambiar = values[i];      
			keys[i] = null;      
			values[i] = null;     
			keysNumber--;        
			put(keyCambiar, valCambiar);      
			i = getNextFreeIndex(i);   
		}   
		keysNumber--;      
		if (keysNumber > 0 && keysNumber == size/8) 
			rehash(size/2); 
	}



}
