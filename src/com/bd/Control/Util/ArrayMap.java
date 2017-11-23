/**
 * Created by Xiaojun Chen at 2011-9-22
 */
package com.bd.Control.Util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author xjc
 * @version 0.1
 * 
 */
/**
 * Memory-efficient map of keys to values with list-style random-access
 * semantics.
 * <p>
 * Conceptually, the keys and values are stored in a simpler array in order to
 * minimize memory use and provide for fast access to a key/value at a certain
 * index (for example {@link #getKeyAt(int)}). However, traditional mapping
 * operations like {@link #get(Object)} and {@link #put(Object, Object)} are
 * slower because they need to look up all key/value pairs in the worst case.
 */
public class ArrayMap<K, V> extends AbstractMap<K, V> implements Cloneable {

	public static int DEFAULT_CAPACITY = 16;
	private int size;
	private Object[] data;
	protected volatile EntrySet entrySet;

	/**
	 * The number of times this HashMap has been structurally modified
	 * Structural modifications are those that change the number of mappings in
	 * the HashMap or otherwise modify its internal structure (e.g., rehash).
	 * This field is used to make iterators on Collection-views of the HashMap
	 * fail-fast. (See ConcurrentModificationException).
	 */
	transient int modCount;

	/**
	 * Returns a new instance of an array map with initial capacity of zero.
	 * Equivalent to calling the default constructor, except without the need to
	 * specify the type parameters. For example: {@code ArrayMap<String, String>
	 * map = ArrayMap.create();}.
	 */
	public ArrayMap() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Returns a new instance of an array map of the given initial capacity. For
	 * example: {@code ArrayMap<String, String> map = ArrayMap.create(8);}.
	 */
	public ArrayMap(int initialCapacity) {
		ensureCapacity(initialCapacity);
	}

	/**
	 * Returns a new instance of an array map of the given key value pairs in
	 * alternating order. For example: {@code ArrayMap<String, String> map =
	 * ArrayMap.of("key1", "value1", "key2", "value2", ...);}.
	 * <p>
	 * WARNING: there is no compile-time checking of the {@code keyValuePairs}
	 * parameter to ensure that the keys or values have the correct type, so if
	 * the wrong type is passed in, any problems will occur at runtime. Also,
	 * there is no checking that the keys are unique, which the caller must
	 * ensure is true.
	 */
	public ArrayMap(Object... keyValuePairs) {
		int length = keyValuePairs.length;
		if (1 == (length % 2)) {
			throw new IllegalArgumentException("missing value for last key: "
					+ keyValuePairs[length - 1]);
		}
		ensureCapacity(keyValuePairs.length >> 1);
		System.arraycopy(keyValuePairs, 0, data, 0, length);
		size = keyValuePairs.length >> 1;
	}

	public ArrayMap(ArrayMap<K, V> map) {
		ensureCapacity(map.size);
		System.arraycopy(map.data, 0, data, 0, map.size << 1);
		this.size = map.size;
	}

	/** Returns the number of key-value pairs set. */
	@Override
	public int size() {
		return this.size;
	}

	/** Returns the key at the given index or {@code null} if out of bounds. */
	public K getKeyAt(int index) {
		if (index < 0 || index >= this.size) {
			return null;
		}
		@SuppressWarnings("unchecked")
		K result = (K) this.data[index << 1];
		return result;
	}

	/** Returns the value at the given index or {@code null} if out of bounds. */
	public V getValueAt(int index) {
		if (index < 0 || index >= this.size) {
			return null;
		}
		return valueAtDataIndex(1 + (index << 1));
	}

	/**
	 * Sets the key/value mapping at the given index, overriding any existing
	 * key/value mapping.
	 * <p>
	 * There is no checking done to ensure that the key does not already exist.
	 * Therefore, this method is dangerous to call unless the caller can be
	 * certain the key does not already exist in the map.
	 * 
	 * @return previous value or {@code null} for none
	 * @throws IndexOutOfBoundsException
	 *             if index is negative
	 */
	protected V set(int index, K key, V value) {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		int minSize = index + 1;
		ensureCapacity(minSize);
		int dataIndex = index << 1;
		V result = valueAtDataIndex(dataIndex + 1);
		setData(dataIndex, key, value);
		if (minSize > this.size) {
			this.size = minSize;
		}
		return result;
	}

	/**
	 * Sets the value at the given index, overriding any existing value mapping.
	 * 
	 * @return previous value or {@code null} for none
	 * @throws IndexOutOfBoundsException
	 *             if index is negative or {@code >=} size
	 */
	protected V set(int index, V value) {
		int size = this.size;
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		int valueDataIndex = 1 + (index << 1);
		V result = valueAtDataIndex(valueDataIndex);
		this.data[valueDataIndex] = value;
		return result;
	}

	/**
	 * Removes the key/value mapping at the given index, or ignored if the index
	 * is out of bounds.
	 * 
	 * @return previous value or {@code null} for none
	 */
	protected V removeAt(int index) {
		return removeFromDataIndexOfKey(index << 1);
	}

	/** Returns whether there is a mapping for the given key. */
	@Override
	public boolean containsKey(Object key) {
		return -2 != getDataIndexOfKey(key);
	}

	/**
	 * Returns the index of the given key or {@code -1} if there is no such key.
	 */
	protected int getIndexOfKey(K key) {
		return getDataIndexOfKey(key) >> 1;
	}

	@SuppressWarnings("unchecked")
	public K getKey(Object key) {
		int keyIndex = getDataIndexOfKey(key);
		if (keyIndex >= 0) {
			return (K) data[keyIndex];
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public K getKeyForValue(V value) {
		int keyIndex = getDataIndexOfValue(value);
		if (keyIndex >= 0) {
			return (K) data[keyIndex];
		} else {
			return null;
		}
	}

	/**
	 * Returns the value set for the given key or {@code null} if there is no
	 * such mapping or if the mapping value is {@code null}.
	 */
	@Override
	public V get(Object key) {
		return valueAtDataIndex(getDataIndexOfKey(key) + 1);
	}

	/**
	 * Sets the value for the given key, overriding any existing value.
	 * 
	 * @return previous value or {@code null} for none
	 */
	@Override
	public V put(K key, V value) {
		int index = getIndexOfKey(key);
		if (index == -1) {
			index = this.size;
		}
		return set(index, key, value);
	}

	/**
	 * Removes the key-value pair of the given key, or ignore if the key cannot
	 * be found.
	 * 
	 * @return previous value or {@code null} for none
	 */
	@Override
	public V remove(Object key) {
		return removeFromDataIndexOfKey(getDataIndexOfKey(key));
	}

	public V removeValue(Object value) {
		return removeFromDataIndexOfKey(getDataIndexOfValue(value));
	}

	public V removeValueAt(int index) {
		return removeFromDataIndexOfKey(index << 1);
	}

	/** Trims the internal array storage to minimize memory usage. */
	public void trim() {
		setDataCapacity(this.size << 1);
	}

	/**
	 * Ensures that the capacity of the internal arrays is at least a given
	 * capacity.
	 */
	protected void ensureCapacity(int minCapacity) {
		Object[] data = this.data;
		int minDataCapacity = minCapacity << 1;
		int oldDataCapacity = data == null ? 0 : data.length;
		if (minDataCapacity > oldDataCapacity) {
			int newDataCapacity = oldDataCapacity / 2 * 3 + 1;
			if (newDataCapacity % 2 == 1) {
				newDataCapacity++;
			}
			if (newDataCapacity < minDataCapacity) {
				newDataCapacity = minDataCapacity;
			}
			setDataCapacity(newDataCapacity);
		}
	}

	private void setDataCapacity(int newDataCapacity) {
		if (newDataCapacity == 0) {
			this.data = null;
			return;
		}
		int size = this.size;
		Object[] oldData = this.data;
		if (size == 0 || newDataCapacity != oldData.length) {
			Object[] newData = this.data = new Object[newDataCapacity];
			if (size != 0) {
				System.arraycopy(oldData, 0, newData, 0, size << 1);
			}
		}
	}

	private void setData(int dataIndexOfKey, K key, V value) {
		Object[] data = this.data;
		data[dataIndexOfKey] = key;
		data[dataIndexOfKey + 1] = value;
		modCount++;
	}

	private V valueAtDataIndex(int dataIndex) {
		if (dataIndex < 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		V result = (V) this.data[dataIndex];
		return result;
	}

	/**
	 * Returns the data index of the given key or {@code -2} if there is no such
	 * key.
	 */
	private int getDataIndexOfKey(Object key) {
		int dataSize = this.size << 1;
		Object k;
		for (int i = 0; i < dataSize; i += 2) {
			k = data[i];
			if (key == null ? k == null : k != null && k.equals(key)) {
				return i;
			}
		}
		return -2;
	}

	private int getDataIndexOfValue(Object value) {
		int dataSize = this.size << 1;
		Object v;
		for (int i = 1; i < dataSize; i += 2) {
			v = data[i];
			if (value == null ? v == null : v != null && v.equals(value)) {
				return i - 1;
			}
		}
		return -2;
	}

	/**
	 * Removes the key/value mapping at the given data index of key, or ignored
	 * if the index is out of bounds.
	 */
	private V removeFromDataIndexOfKey(int dataIndexOfKey) {
		int dataSize = this.size << 1;
		if (dataIndexOfKey < 0 || dataIndexOfKey >= dataSize) {
			return null;
		}
		V result = valueAtDataIndex(dataIndexOfKey + 1);
		Object[] data = this.data;
		int moved = dataSize - dataIndexOfKey - 2;
		if (moved != 0) {
			System.arraycopy(data, dataIndexOfKey + 2, data, dataIndexOfKey,
					moved);
		}
		this.size--;
		modCount++;
		setData(dataSize - 2, null, null);
		return result;
	}

	@Override
	public void clear() {
		this.size = 0;
	}

	public void destroy() {
		this.size = 0;
		data = null;
	}

	@Override
	public boolean containsValue(Object value) {
		int dataSize = this.size << 1;
		Object[] data = this.data;
		for (int i = 1; i < dataSize; i += 2) {
			Object v = data[i];
			if (value == null ? v == null : value.equals(v)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		EntrySet entrySet = this.entrySet;
		if (entrySet == null) {
			entrySet = this.entrySet = new EntrySet();
		}
		return entrySet;
	}

	@Override
	public ArrayMap<K, V> clone() {
		return new ArrayMap<K, V>(this);
	}

	final class EntrySet extends AbstractSet<Map.Entry<K, V>> {

		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			return new EntryIterator();
		}

		@Override
		public int size() {
			return ArrayMap.this.size;
		}
	}

	final class EntryIterator implements Iterator<Map.Entry<K, V>> {

		private int expectedModCount; // For fast-fail
		private int nextIndex;

		EntryIterator() {
			expectedModCount = modCount;
		}

		public final boolean hasNext() {
			return this.nextIndex < ArrayMap.this.size;
		}

		public final Map.Entry<K, V> next() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			int index = this.nextIndex;
			if (index == ArrayMap.this.size) {
				throw new NoSuchElementException();
			}
			this.nextIndex++;
			return new Entry(index);
		}

		public final void remove() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			int index = this.nextIndex - 1;
			ArrayMap.this.removeAt(index);
			nextIndex = index;
			expectedModCount = modCount;
		}
	}

	final class Entry implements Map.Entry<K, V> {

		private int index;

		Entry(int index) {
			this.index = index;
		}

		public K getKey() {
			return ArrayMap.this.getKeyAt(this.index);
		}

		public V getValue() {
			return ArrayMap.this.getValueAt(this.index);
		}

		public V setValue(V value) {
			return ArrayMap.this.set(this.index, value);
		}
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof ArrayMap)) {
			return false;
		}

		@SuppressWarnings("rawtypes")
		ArrayMap map = (ArrayMap) obj;
		if (size != map.size) {
			return false;
		}
		K key;
		V value;
		for (int i = 0; i < size; i++) {
			key = getKeyAt(i);
			value = getValueAt(i);
			if (value == null) {
				if (!map.containsKey(key) || map.get(key) != null) {
					return false;
				}
			} else {
				if (!value.equals(map.get(key))) {
					return false;
				}
			}
		}
		return true;
	}
}
