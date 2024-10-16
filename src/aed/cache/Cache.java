package aed.cache;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Position;
import es.upm.aedlib.map.*;
import es.upm.aedlib.positionlist.*;

public class Cache<Key, Value> {

	// Tamano de la cache
	private int maxCacheSize;

	// NO MODIFICA ESTOS ATTRIBUTOS, NI CAMBIA SUS NOMBRES: mainMemory,
	// cacheContents, keyListLRU

	// Para acceder a la memoria M
	private Storage<Key, Value> mainMemory;
	// Un 'map' que asocia una clave con un ``CacheCell''
	private Map<Key, CacheCell<Key, Value>> cacheContents;
	// Una PositionList que guarda las claves en orden de
	// uso -- la clave mas recientemente usado sera el keyListLRU.first()
	private PositionList<Key> keyListLRU;

	// Constructor de la cache. Especifica el tamano maximo
	// y la memoria que se va a utilizar
	public Cache(int maxCacheSize, Storage<Key, Value> mainMemory) {
		this.maxCacheSize = maxCacheSize;

		// NO CAMBIA
		this.mainMemory = mainMemory;
		this.cacheContents = new HashTableMap<Key, CacheCell<Key, Value>>();
		this.keyListLRU = new NodePositionList<Key>();
	}

	// Devuelve el valor que corresponde a una clave "Key"
	public Value get(Key key) {
		// CAMBIA este metodo
		Value res = null;
		if (this.cacheContents.size() > 0 && this.cacheContents.containsKey(key)) {
			ordenar(key);
			res = this.cacheContents.get(key).getValue();
		} else {
			res = mainMemory.read(key);
			if (res != null) {
				this.keyListLRU.addFirst(key);
				this.cacheContents.put(key, new CacheCell<Key, Value>(res, false, keyListLRU.first()));
			}
		}
		if (this.cacheContents.size() > this.maxCacheSize) {			
			borraUlt();

		}

		return res;
	}

	// Establece un valor nuevo para la clave en la memoria cache
	public void put(Key key, Value value) {
		// CAMBIA este metodo
		if (this.cacheContents.size() > 0 && this.cacheContents.containsKey(key)) {

			this.cacheContents.get(key).setValue(value);
			ordenar(key);
			this.cacheContents.get(key).setDirty(true);
		}

		else {
			this.keyListLRU.addFirst(key);
			this.cacheContents.put(key, new CacheCell<Key, Value>(value, true, keyListLRU.first()));
		}
		if (this.cacheContents.size() > this.maxCacheSize) {			
			borraUlt();
		}
	}
	
	private void ordenar(Key key) {
		this.keyListLRU.remove(cacheContents.get(key).getPos());
		this.keyListLRU.addFirst(key);
		this.cacheContents.get(key).setPos(keyListLRU.first());
	}
	
	private void borraUlt() {
		if (this.cacheContents.get(this.keyListLRU.last().element()).getDirty()) {

			mainMemory.write(keyListLRU.last().element(),
					cacheContents.get(keyListLRU.last().element()).getValue());
		}
		this.cacheContents.remove(keyListLRU.last().element());
		this.keyListLRU.remove(keyListLRU.last());
	}
	// NO CAMBIA
	public String toString() {
		return "cache";
	}
}

