import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashMap<K, V> implements HashMapInterface<K, V>, Gradable<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    public HashMap() {
        this.size = 0;
        this.table = (MapEntry<K, V>[]) (new MapEntry[STARTING_SIZE]);
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        if ((((double) (this.size() + 1)) / table.length) > MAX_LOAD_FACTOR) {
            expandTable();
        }
        int hashed = hash(key);
        V oldVal = null;
        if (table[hashed] == null || table[hashed].isRemoved()) {
            table[hashed] = new MapEntry(key, value);
            size++;
        } else if ((table[hashed].getKey()).equals(key)) {
            oldVal = table[hashed].getValue();
            table[hashed].setValue(value);
        } else {
            int counter = hashed;
            boolean added = false;
            while (table[counter] != null
                    && !table[counter].isRemoved() && !added) {
                if (table[counter].getKey().equals(key))  {
                    oldVal = table[counter].getValue();
                    table[counter].setValue(value);
                    size++;
                    added = true;
                } else if (counter == table.length - 1) {
                    counter = 0;
                } else {
                    counter++;
                }
            }
            if (!added) {
                table[counter] = new MapEntry(key, value);
                size++;
            }
        }
        return oldVal;
    }

    /**
     * Hashes a key to an index in the table
     * @param key, the key to be hashed
     * @return the index to place the key
     */
    private int hash(K key) {
        return (Math.abs(key.hashCode()) % table.length);
    }

    /**
     * Doubles the capacity of the table, copying and rehashing all elements
     */
    private void expandTable() {
        MapEntry<K, V>[] bigTable =
                (MapEntry<K, V>[]) (new MapEntry[table.length * 2]);
        MapEntry<K, V>[] oldTable = table;
        table = bigTable;
        size = 0;
        for (MapEntry<K, V> m : oldTable) {
            if (m != null && !m.isRemoved()) {
                this.add(m.getKey(), m.getValue());
            }
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (size() == 0) {
            return null;
        }
        int hashed = this.hash(key);
        V val = null;
        if (table[hashed] == null) {
            return null;
        } else if ((table[hashed].getKey()).equals(key)) {
            val = table[hashed].getValue();
            table[hashed].setRemoved(true);
            size--;
        } else if (table[hashed].isRemoved()) {
            int counter = hashed;
            while (table[counter] != null && !table[counter].isRemoved()) {
                if (table[counter].getKey().equals(key))  {
                    val = table[counter].getValue();
                    table[counter].setValue(null);
                    size--;
                } else if (counter == table.length - 1) {
                    counter = 0;
                } else {
                    counter++;
                }
            }
        }
        return val;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (size() == 0) {
            return null;
        }
        V val = null;
        int hashed = this.hash(key);
        if (table[hashed] == null || table[hashed].isRemoved()) {
            return null;
        } else if (table[hashed].getKey().equals(key)) {
            val = table[hashed].getValue();
        } else {
            int counter = hashed;
            while (table[counter] != null && !table[counter].isRemoved()) {
                if (table[counter].getKey().equals(key))  {
                    val = table[counter].getValue();
                    counter++;
                } else if (counter == table.length - 1) {
                    counter = 0;
                } else {
                    counter++;
                }
            }
        }
        return val;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (size() == 0) {
            return false;
        }
        int hashed = this.hash(key);
        boolean check = false;
        if (table[hashed] == null
                || (table[hashed].getKey().equals(key))
                && table[hashed].isRemoved()) {
            check = false;
        } else if ((table[hashed].getKey().equals(key))
                && !table[hashed].isRemoved()) {
            check = true;
        } else {
            int counter = hashed;
            while (table[counter] != null) {
                if (table[counter].getKey().equals(key)) {
                    check = true;
                    counter++;
                } else if (counter == table.length - 1) {
                    counter = 0;
                } else {
                    counter++;
                }
            }
        }
        return check;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.table = (MapEntry<K, V>[]) (new MapEntry[STARTING_SIZE]);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        Set<K> mySet = new HashSet<K>();
        for (MapEntry<K, V> m : table) {
            if (m != null) {
                mySet.add(m.getKey());
            }
        }
        return mySet;
    }

    @Override
    public List<V> values() {
        List<V> myList = new ArrayList<V>();
        for (MapEntry<K, V> m: table) {
            if (m != null) {
                myList.add(m.getValue());
            }
        }
        return myList;
    }
}
