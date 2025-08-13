package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSAktenzeichenart {
    AKTUELL("aktuell") ;

    private final String descriptor;

    XoevCodeGDSAktenzeichenart(String descriptor) {
        this.descriptor = descriptor;
    }
}
