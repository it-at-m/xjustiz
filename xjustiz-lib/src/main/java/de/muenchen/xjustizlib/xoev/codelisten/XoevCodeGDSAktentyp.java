package de.muenchen.xjustizlib.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSAktentyp {

    BUSSGELDAKTE("bussgeldakte");

    private final String descriptor;

    XoevCodeGDSAktentyp(final String descriptor) {
        this.descriptor = descriptor;
    }
}
