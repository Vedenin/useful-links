package com.github.vedenin.thirdpartylib.collections;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Set;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@Atom(HashMultimap.class)
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
