package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSAktenzeichenart {
    AKTUELL("aktuell");

    private final String descriptor;

    XoevCodeGDSAktenzeichenart(final String descriptor) {
        this.descriptor = descriptor;
    }
}
