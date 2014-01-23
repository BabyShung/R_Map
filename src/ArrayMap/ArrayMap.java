package ArrayMap;

import java.util.Iterator;

import Interfaces.Entry;
import Interfaces.Map;

public class ArrayMap<K, V> implements Map<K, V> {
	// We'll store the map as a simple array, which we'll grow dynamically
	private Entry<K, V>[] mapArray = (Entry<K, V>[]) new Entry[2];

	// We'll track the size as well
	private int size;

	// Returns the value of the given key, or null if no such entry exists
	@Override
	public V get(K key) {
		int elementIndex = findKeyIndex(key);

		if (elementIndex == -1) {
			return null;
		}

		return mapArray[elementIndex].getValue();
	}

	// Inserts the given key-value pair into the map; if the key already
	// existed, the value is replaced with the given 'key' and the old value is
	// returned. If the key was not present, it is inserted and null is
	// returned.
	@Override
	public V put(K key, V value) {
		// Find the key
		int elementIndex = findKeyIndex(key);

		// If the key was already in the map, update the value of that entry
		// and return the old value
		if (elementIndex != -1) {
			V oldVal = mapArray[elementIndex].getValue();
			mapArray[elementIndex] = new Pair<>(key, value);
			return oldVal;
		}

		// The key was not present, so we will insert it. Make the map grow
		// in size if need be.
		if (size >= mapArray.length) {
			Entry<K, V>[] newMap = (Entry<K, V>[]) new Entry[mapArray.length * 2];
			System.arraycopy(mapArray, 0, newMap, 0, mapArray.length);
			mapArray = newMap;
		}

		// Insert the new key-value pair into the map and return null
		mapArray[size++] = new Pair<>(key, value);
		return null;
	}

	// Returns and removes the value associated with the given key
	@Override
	public V remove(K key) {
		// Get the index of the element
		int elementIndex = findKeyIndex(key);

		// If the element was not found, return null
		if (elementIndex == -1) {
			return null;
		}

		// Save the value, shift the array over, set the last entry (which was
		// moved) to 'null', and return the value
		V oldVal = mapArray[elementIndex].getValue();
		System.arraycopy(mapArray, elementIndex + 1, mapArray, elementIndex,
				size - elementIndex - 1);
		size--;
		mapArray[size] = null;
		return oldVal;
	}

	// Returns an iterable object over the keys of the map
	@Override
	public Iterable<K> keySet() {
		// Create an anonymous class of type Iterable<K> and return a reference
		// to it
		return new Iterable<K>() {
			@Override
			public Iterator<K> iterator() {
				return new KeyIterator();
			}
		};
	}

	// Returns an iterable object over the values of the map
	@Override
	public Iterable<V> values() {
		// Create anonymous Iterable object and return a reference to it
		return new Iterable<V>() {
			@Override
			public Iterator<V> iterator() {
				return new ValueIterator();
			}
		};
	}

	// Returns an iterable object over the entries of the map
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// Create an anonymous class of type Iterable<Entry>, and return a
		// reference to it
		return new Iterable<Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				return new EntryIterator();
			}
		};
	}

	// Returns the number of elements in the map
	@Override
	public int size() {
		return size;
	}

	// returns true exactly when the map has no elements
	@Override
	public boolean isEmpty() {
		return size <= 0;
	}

	// A private helper method for finding the index of the given key
	private int findKeyIndex(K key) {
		// Check every position -- if we find the key, return the index;
		for (int i = 0; i < size; i++) {
			if (mapArray[i].getKey().equals(key)) {
				return i;
			}
		}

		// If we didn't find the key, return -1
		return -1;
	}

	// ---------------------------------------------------
	// INNER CLASSES FOR ITERATORS
	// ---------------------------------------------------

	// Below are implementations for iterators over keys, values, and entries.
	// They are all simple and self-explanatory, and use the index-based
	// cursor notion we have seen in the past.

	private class KeyIterator implements Iterator<K> {
		private int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor < size;
		}

		@Override
		public K next() {
			return mapArray[cursor++].getKey();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class ValueIterator implements Iterator<V> {
		private int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor < size;
		}

		@Override
		public V next() {
			return mapArray[cursor++].getValue();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class EntryIterator implements Iterator<Entry<K, V>> {
		private int cursor = 0;

		@Override
		public boolean hasNext() {
			return cursor < size;
		}

		@Override
		public Entry<K, V> next() {
			return mapArray[cursor++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}