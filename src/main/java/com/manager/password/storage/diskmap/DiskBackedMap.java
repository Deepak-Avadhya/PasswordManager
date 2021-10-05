package com.manager.password.storage.diskmap;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiskBackedMap<K extends Serializable, V extends Serializable> implements Map<K, V>, Closeable {
    private Logger log = Logger.getLogger(DiskBackedMap.class.getName());
    private Store<K, V> store;

    public DiskBackedMap(String dataDir) {
        this.store = new Store<K, V>(new Configuration().setDataDir(new File(dataDir)));
    }

    public DiskBackedMap(Configuration config) {
        this.store = new Store<K, V>(config);
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return store.get((K) key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public V get(Object key) {
        return store.get((K) key);
    }

    @Override
    public boolean isEmpty() {
        return store.size() == 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V put(K key, V value) {
        return store.save(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    @Override
    public V remove(Object key) {
        V value = store.get((K) key);
        store.remove((K) key);
        return value;
    }

    @Override
    public int size() {
        return store.size();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    public long sizeOnDisk(){
        return store.sizeOnDisk();
    }

    public void close() throws IOException {
        store.close();
    }

    public void gc() throws Exception {
        store.vacuum();
    }

    @Override
    public void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

}