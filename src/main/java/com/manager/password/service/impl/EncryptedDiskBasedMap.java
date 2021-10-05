//package com.manager.password.service.impl;
//
//import com.manager.password.service.IEncryption;
//import com.manager.password.storage.diskmap.Configuration;
//import com.manager.password.storage.diskmap.Store;
//
//import java.io.Closeable;
//import java.io.File;
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.*;
//import java.util.logging.Logger;
//
//public class EncryptedDiskBasedMap<K extends Serializable, V extends Serializable> implements Map<K, V>, Closeable {
//    private Logger log = Logger.getLogger(EncryptedDiskBasedMap.class.getName());
//    private Store<Serializable, Serializable> store;
//    private IEncryption iEncryption;
//
//    public EncryptedDiskBasedMap(String dataDir, IEncryption iEncryption) {
//        this.store = new Store<>(new Configuration().setDataDir(new File(dataDir)));
//        if(iEncryption==null)throw new NullPointerException("Encryption class is null");
//        this.iEncryption=iEncryption;
//    }
//
//    public EncryptedDiskBasedMap(Configuration config,IEncryption iEncryption) {
//        this.store = new Store<>(config);
//        if(iEncryption==null)throw new NullPointerException("Encryption class is null");
//        this.iEncryption=iEncryption;
//    }
//
//    @Override
//    public void clear() {
//        store.clear();
//    }
//
//    @Override
//    public boolean containsKey(Object key) {
//        try {
//            return store.get(iEncryption.encrypt((Serializable) key)) != null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public boolean containsValue(Object value) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    @Override
//    @SuppressWarnings("element-type-mismatch")
//    public V get(Object key) {
//        try {
//            return (V)iEncryption.decrypt(store.get(iEncryption.encrypt((Serializable) key)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return store.size() == 0;
//    }
//
//    @Override
//    public Set<K> keySet() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public V put(K key, V value) {
//        try {
//            return (V) iEncryption.decrypt(store.save(iEncryption.encrypt(key),iEncryption.encrypt(value)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void putAll(Map<? extends K, ? extends V> m) {
//        for (K key : m.keySet()) {
//            try {
//                put(key,m.get(key));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public V remove(Object key) {
//        V value = null;
//        try {
//            value = (V) iEncryption.decrypt(store.get(iEncryption.encrypt((Serializable) key)));
//            store.remove(iEncryption.encrypt((Serializable) key));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return value;
//    }
//
//    @Override
//    public int size() {
//        return store.size();
//    }
//
//    @Override
//    public Collection<V> values() {
//        throw new UnsupportedOperationException();
//    }
//
//    public long sizeOnDisk(){
//        return store.sizeOnDisk();
//    }
//
//    public void close() throws IOException {
//        store.close();
//    }
//
//    public void gc() throws Exception {
//        store.vacuum();
//    }
//
//    @Override
//    public void finalize() throws Throwable {
//        this.close();
//        super.finalize();
//    }
//
//}