package com.github.vedenin.thirdpartylib.collections;

import com.github.vedenin.thirdpartylib.annotations.Atom;
import com.github.vedenin.thirdpartylib.annotations.BoilerPlate;
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
@Atom
public class MultimapAtom<K, V> {
    private final Multimap<K, V> map;

    public Set<K> keySet() {
        return map.keySet();
    }

    public Collection<V> get(K key) {
        return map.get(key);
    }

    public void set(K key, V value) {
        map.put(key, value);
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private MultimapAtom() {
        this.map = HashMultimap.create();
    }

    @BoilerPlate
    public static MultimapAtom create() {
        return new MultimapAtom();
    }
}
