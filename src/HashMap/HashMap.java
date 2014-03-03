package HashMap;

/**
 * HashMap (important)
 * 
 * ---What is a hash table?
 * It is a <key,value> data structure that takes O(1) expected time to read, write and remove.
 * It contains a hash function that maps the key to an array index so that 
 * it can get the value in a bucket array.
 * 
 * hashmap consists of 2 parts: a bucket array and a hash function
 * 
 * A bucket array is an array of objects: Entry, Integer, String, Charater, LinkedList, etc
 * A hash function maps the key to an array index, actually:
 * 1.it maps the key to an integer, called hashcode
 * 2.then maps the hashcode to the index of the bucket array by a compression function
 * 
 * ---types of hashcode:
 * 1.hashcode in Java
 * 2.poly hashcode, etc
 * 
 * ---types of compression function:
 * PS: bucketCapacity should be a prime, which helps get a uniformly distributed index
 * 1.division method: hashcode % bucketCapacity      (not good enough)
 * 2.MAD method: [(a*hashcode + b) % largePrimeNumber] % bucketCapacity
 * 
 * ---Collision
 * Even though you have a good hash function, collision might still occur,
 * thus we need mechanism to handle that.
 * 1.open addressing (whose cell is an entry)
 * 	a. linear probing
 *  b. quadratic probing
 *  c. double hashing
 *
 * 2.separate chaining (whose cell is a linkedlist or an array)
 * 
 * ---Load factor
 * It is a ratio:  n/N	
 * n:  inserted entries in the bucket array
 * N:  the capacity of the bucket array
 * 
 * For Open addressing,the load factor should below 0.5
 * For Separate chaining (much robust) should below 1
 * 
 * Open addressing VS Separate chaining
 * OA: less complicated but become inefficient when load factor >0.5 or 2/3,
 *  so it needs rehashing constantly.
 * SC: involves with other d.s (linkedlist or array). When capacity is not sure, it's a good choice.
 * 
 * 
 */

import java.util.ArrayList;
import java.util.Random;

import Interfaces.Entry;
import Interfaces.Map;

public class HashMap<K, V> implements Map<K, V> {

	protected Entry<K, V> available = new HashEntry<K, V>(null, null);
	protected int size;// how many inserted entries
	protected int prime, capacity;
	protected Entry<K, V>[] bucket;// array to store the elements
	protected long scale, shift;

	public HashMap(int capacity) {
		this(109345121, capacity);// 109345121 is a prime
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
		checkKey(key);// check null
		int i = hashValue(key);// hash function *
		int j = i;
		do {
			Entry<K, V> e = bucket[i];
			if (e == null) {
				if (avail < 0) {
					avail = i;// key not in table
				}
				break;
			} else if (key.equals(e.getKey()))// find the key
				return i;
			else if (e == available) {// bucket is deactivated, from remove
										// method
				if (avail < 0)
					avail = i;// remember this slot is available, helpful for
								// insertion
			}// else, collide, just increment index
			i = (i + 1) % capacity;// linear probing *
		} while (i != j);

		return -(avail + 1);
	}

	@Override
	public V put(K key, V value) {
		int i = findEntry(key);
		if (i >= 0)// replace entries
			return ((HashEntry<K, V>) bucket[i]).setValue(value);
		// insert into new place
		else if (size >= capacity / 2) {// load factor>=0.5, need rehashing
			rehash();
			i = findEntry(key);
		}
		// -(i+1), easily find the available place and insert
		bucket[-i - 1] = new HashEntry<K, V>(key, value);
		size++;
		return null;
	}

	protected void rehash() {
		capacity *= 2;
		Entry<K, V>[] old = bucket;
		bucket = (Entry<K, V>[]) new Entry[capacity];
		setRandomScaleAndShift();// calling hash function index will be
									// different
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
		if (i < 0)// key not in table
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

	/**
	 * a. linear probing b. quadratic probing c. double hashing
	 */
	// public Entry<K,V> find (K key)
	// {
	// int index = hashFunction (key);
	// while( hashArray[index] != null)
	// {
	// if(hashArray[index].getKey() == key)
	// return hashArray[index];
	// index++;
	// index = index % arraySize;
	// }
	// return null;
	// }

	// public Entry<K,V> find (K key)
	// {
	// int increment = 1;
	// int index = hashFunction (key);
	// while( hashArray[index] != null)
	// {
	// if(hashArray[index].getKey() == key)
	// return hashArray[index];
	// index=index+increment;
	// index = index % arraySize;
	// increment*=2;
	// }
	// return null;
	// }

	// public Entry<K,V> find (K key)
	// {
	// int index = hashFunction (key);
	// int stepSize = hashFunction2(key);
	// while( hashArray[index] != null)
	// {
	// if(hashArray[index].getKey() == key)
	// return hashArray[index];
	// index = index + stepSize;
	// index = index % arraySize;
	//
	// }
	// return null;
	// }

}
