package HashMap;

import java.util.ArrayList;
import java.util.Random;

import Interfaces.Entry;
import Interfaces.Map;

public class HashMap<K, V> implements Map<K, V> {

	protected Entry<K, V> available = new HashEntry<K, V>(null, null);
	protected int size;
	protected int prime, capacity;
	protected Entry<K, V>[] bucket;// array to store the elements
	protected long scale, shift;

	public HashMap(int capacity) {
		this(109345121, capacity);
	}

	public HashMap(int prime, int capacity) {
		this.prime = prime;
		this.capacity = capacity;
		bucket = (Entry<K, V>[]) new Entry[capacity];
		setRandomScaleAndShift();
	}

	protected void checkKey(K key) {
		if (key == null)
			throw new Error("Invalid key:null.");
	}

	public int hashValue(K key) { // MAD method to get the index
		return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
	}

	@Override
	public V get(K key) {
		int i = findEntry(key);
		if (i < 0)
			return null;
		return bucket[i].getValue();
	}

	private int findEntry(K key) {
		int avail = -1;
		checkKey(key);
		int i = hashValue(key);
		int j = i;
		do {
			Entry<K, V> e = bucket[i];
			if (e == null) {
				if (avail < 0) {
					avail = i;
				}
				break;
			}
			if (key.equals(e.getKey()))
				return i;
			if (e == available) {// bucket is deactivated
				if (avail < 0)
					avail = i;
			}
			i = (i + 1) % capacity;
		} while (i != j);
		return -(avail + 1);
	}

	@Override
	public V put(K key, V value) {
		int i = findEntry(key);
		if (i >= 0)
			return ((HashEntry<K, V>) bucket[i]).setValue(value);
		if (size >= capacity / 2) {
			rehash();
			i = findEntry(key);
		}
		bucket[-i - 1] = new HashEntry<K, V>(key, value);
		size++;
		return null;
	}

	protected void rehash() {
		capacity *= 2;
		Entry<K, V>[] old = bucket;
		bucket = (Entry<K, V>[]) new Entry[capacity];
		setRandomScaleAndShift();
		for (int i = 0; i < old.length; i++) {
			Entry<K, V> e = old[i];
			if ((e != null) && (e != available)) {
				int j = -1 - findEntry(e.getKey());
				bucket[j] = e;
			}
		}

	}

	private void setRandomScaleAndShift() {
		Random r = new Random();
		scale = r.nextInt(prime - 1) + 1;
		shift = r.nextInt(prime);
	}

	@Override
	public V remove(K key) {
		int i = findEntry(key);
		if (i < 0)
			return null;
		V value = bucket[i].getValue();
		bucket[i] = available;
		size--;
		return value;
	}

	@Override
	public Iterable<K> keySet() {
		ArrayList<K> keys = new ArrayList<>();// return container
		for (int i = 0; i < capacity; i++) {
			if ((bucket[i] != null) && (bucket[i] != available))
				keys.add(bucket[i].getKey());
		}
		return keys;
	}

	@Override
	public Iterable<V> values() {
		ArrayList<V> values = new ArrayList<>();// return container
		for (int i = 0; i < capacity; i++) {
			if ((bucket[i] != null) && (bucket[i] != available))
				values.add(bucket[i].getValue());
		}
		return values;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> entries = new ArrayList<>();// return container
		for (int i = 0; i < capacity; i++) {
			if ((bucket[i] != null) && (bucket[i] != available))
				entries.add(bucket[i]);
		}
		return entries;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

}
