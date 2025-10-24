package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSBestandteiltyp {

    ORIGINAL("original"),
    REPRAESENTANT("repraesentant");

    private final String descriptor;

    XoevCodeGDSBestandteiltyp(String descriptor) {
        this.descriptor = descriptor;
    }
}
