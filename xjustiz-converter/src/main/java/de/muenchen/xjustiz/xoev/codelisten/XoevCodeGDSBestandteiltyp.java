package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSBestandteiltyp {

    ORIGINAL("original"),
    REPRAESENTANT("repraesentant");

    private final String descriptor;

    XoevCodeGDSBestandteiltyp(final String descriptor) {
        this.descriptor = descriptor;
    }
}
