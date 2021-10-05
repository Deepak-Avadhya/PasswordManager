package com.manager.password.storage.diskmap;

import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Store<K extends Serializable, V extends Serializable> implements Closeable {
    private Logger log = Logger.getLogger(Store.class.getName());
    private List<Page<K, V>> pages;
    private int magicNumber = 13;

    public Store(Configuration cfg) {
        init(cfg);
    }

    private void init(Configuration cfg) {
        pages = new ArrayList<Page<K, V>>(magicNumber);
        for (int i = 0; i < magicNumber; i++) {
            Configuration config = new Configuration(cfg);
            config.setNumber(i);
            pages.add(new Page<K, V>(config));
        }
    }

    public V save(K key, V value) {
        Page<K, V> kvPage = findPage(key);
        return kvPage.save(key, value);
    }

    public V get(K key) {
        return  findPage(key).load(key);
    }

    private Page<K, V> findPage(K key) {
        int idx = key.hashCode() % magicNumber;
        return pages.get(Math.abs(idx));
    }

    public void remove(K  key) {
        findPage(key).remove(key);
    }

    public int size() {
        int size = 0;
        for (Page<K, V> page : pages) {
            size = page.keyCount();
        }
        return size;
    }

    public synchronized void close() {
        for (Page page : pages) {
            page.close();
        }
    }

    public void vacuum() throws Exception {
        log.log(Level.INFO, "Starting gc process");
        long time = 0;
        for (Page<K, V> page : pages) {
            long pTime = System.currentTimeMillis();
            log.log(Level.INFO, "Started Vacuuming page:" + page.toString());
            page.vacuum();
            pTime = System.currentTimeMillis() - pTime;
            log.log(Level.INFO, "Completed Vacuuming page in :" + pTime + " ms");
            time += pTime;
        }
        log.log(Level.INFO, "Vacuum Complete:" + time + " ms");
    }

    public long sizeOnDisk() {
        long size = 0;
        for (Page<K, V> page : pages) {
            size = page.size();
        }
        return size;
    }

    public synchronized  void clear() {
        for (Page<K, V> page : pages) {
            page.clear();
        }
    }
}