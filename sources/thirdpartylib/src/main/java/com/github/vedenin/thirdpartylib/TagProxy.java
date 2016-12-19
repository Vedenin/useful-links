package com.github.vedenin.thirdpartylib;

import org.jsoup.parser.Tag;

/**
 * Created by vvedenin on 12/19/2016.
 */
public class TagProxy {
    private final Tag tag;

    public TagProxy(Tag tag) {
        this.tag = tag;
    }

    public static TagProxy valueOf(String tagString) {
        return new TagProxy(Tag.valueOf(tagString));
    }

    public static TagProxy getProxy(Tag tag) {
        return new TagProxy(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagProxy tagProxy = (TagProxy) o;

        return !(tag != null ? !tag.equals(tagProxy.tag) : tagProxy.tag != null);

    }

    @Override
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }
}
