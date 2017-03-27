import java.io.Serializable;

public class HashMap<V> implements Serializable {
    
    // use a static class for objects to be stored in the map
    private static class Entry implements Serializable {
        final String key;
        Object value;
        Entry next;
        int hash;
        
        Entry(String k, Object v, Entry n, int h) {
            key = k;
            value = v;
            next = n;
            hash = h;
        }
    }
    
    // The map where all entries will be stored
    private Entry[] map;
    // The number of entries, used to know when to resize
    int count = 0;
    
    // initialize the map with 16 slots
    public HashMap() {
        map = new Entry[16];
    }
    
    // check if an object with the supplied key is in the map
    public boolean containsKey(String key) {
        int h = key.hashCode();
        
        // Use a bit mask to drop extraneous bits
        // This will give an int corresponding to the slot in the map where
        // the supplied key would go
        int index = h & (map.length - 1);
        
        // look for a matching key in the table slot with a matching hash
        for(Entry e = map[index]; e != null; e = e.next)
            if(e.hash == h && key.equals(e.key))
                return true;
        // If no match was found in the slot, it is not in the map
        return false;
    }
    
    // returns the value stored for the given key
    // or returns null if the key is not found
    public V get(String key) {
        int h = key.hashCode();
        
        // Use a bit mask to drop extraneous bits
        // This will give an int corresponding to the slot in the map where
        // the supplied key would go
        int index = h & (map.length - 1);
        
        // look for a matching key in the table slot with a matching hash
        for(Entry e = map[index]; e != null; e = e.next)
            if(e.hash == h && key.equals(e.key))
                return (V)e.value;
        // If no match was found in the slot, it is not in the map
        return null;
    }
    
    // insert a key, value pair into the map.
    // Will replace the value of an existing pair
    // if the key is already in the map.
    public void put(String key, V value) {
        int h = key.hashCode();
        
        // Use a bit mask to drop extraneous bits
        // This will give an int corresponding to the slot in the map where
        // the supplied key would go
        int index = h & (map.length - 1);
        
        // loop through the entries in the appropriate slot
        // if one has the same key, then simply replace
        // that Entry's value and return
        for(Entry e = map[index]; e != null; e = e.next) {
            if(e.hash == h && key.equals(e.key)) {
                e.value = value;
                return;
            }
        }
        // If no matching key was found, create and add a new Entry
        Entry p = new Entry(key, value, map[index], h);
        map[index] = p;
        
        // Check if the size of the map is large enough to resize
        if(( (++count) / (float)map.length) < 0.75)
            return;
        
        // create a new map, twice the size of the old
        int n = map.length;
        int newN = n<<2;
        Entry[] newMap = new Entry[newN];
        
        // add each element of map to newMap
        for(int i=0; i<n; ++i) {
            Entry e;
            while((e = map[i]) != null) {
                // remove the current e from map, keeping the next values
                map[i] = e.next;
                // get the index for the removed e for the new map
                int newIndex = e.hash & (newN-1);
                // replace the first element in the slot with e, adding the
                // old one as e's next field
                e.next = newMap[newIndex];
                newMap[newIndex] = e;
            }
        }
        // after the newMap has been populated, replace map
        map = newMap;
    }
    
    // removes the entry with the given key if it exists
    public void remove(String key) {
        int h = key.hashCode();
        
        // Use a bit mask to drop extraneous bits
        // This will give an int corresponding to the slot in the map where
        // the supplied key would go
        int index = h & (map.length - 1);
        
        // predecessor will be used to keep the entries on either side
        // of the removed entry connected
        Entry predecessor = null;
        Entry p = map[index];
        while(p != null) {
            // if the key has been found, remove the Entry
            if(h == p.hash && key.equals(p.key)) {
                if(predecessor == null)
                    map[index] = p.next;
                else
                    predecessor.next = p.next;
                --count;
                return;
            } else { // move the predecessor and Entry in question both forward
                predecessor = p;
                p = p.next;
            }
        }
    }
}
