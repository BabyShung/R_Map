package ArrayMap;

import Interfaces.Entry;

public class Pair<K,V> implements Entry<K,V>
{
    private K key;
    private V value;
    
    public Pair( K key, V val )
    {
        this.key = key;
        value = val;
    }
    
    @Override
    public K getKey( ) { return key; }
    
    @Override
    public V getValue( ) { return value; }
    
    @Override
    public String toString( )
    {
        return "Key: " + key + "; Value: " + value;
    }
}