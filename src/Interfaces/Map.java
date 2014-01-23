package Interfaces;

public interface Map<K, V> {
	// Returns the value of the given key, or null if no such entry exists
	V get(K key);

	// Inserts the given key-value pair into the map; if the key already
	// existed, the value is replaced with the given 'key' and the old value is
	// returned. If the key was not present, it is inserted and null is
	// returned.
	V put(K key, V value);

	// Returns and removes the value associated with the given key
	V remove(K key);

	// Returns an iterable object over the keys of the map
	Iterable<K> keySet();

	// Returns an iterable object over the values of the map
	Iterable<V> values();

	// Returns an iterable object over the entries of the map
	Iterable<Entry<K, V>> entrySet();
 
	int size();
 
	boolean isEmpty();
}