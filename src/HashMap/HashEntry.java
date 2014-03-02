package HashMap;

import Interfaces.Entry;

public class HashEntry<K, V> implements Entry<K, V> {

	protected K key;
	protected V value;

	public HashEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		this.value = value;
		return value;
	}

}
