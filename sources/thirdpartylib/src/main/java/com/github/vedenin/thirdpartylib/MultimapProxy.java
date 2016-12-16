package com.github.vedenin.thirdpartylib;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Set;

/**
 * Proxy object for Multimap object
 *
 * This proxy must provide only method that need in project to light dependency for third party libraries
 *
 * Created by vvedenin on 12/16/2016.
 */
public class MultimapProxy<K, V> {
    private final Multimap<K, V> map;

    public MultimapProxy() {
        this.map = HashMultimap.create();
    }

    public static MultimapProxy create() {
        return new MultimapProxy();
    }

    public Set<K> keySet() {
        return map.keySet();
    }

    public Collection<V> get(K key) {
        return map.get(key);
    }

    public void set(K key, V value) {
        map.put(key, value);
    }
}
