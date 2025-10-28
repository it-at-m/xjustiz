package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSAktentyp {

    BUSSGELDAKTE("bussgeldakte");

    private final String descriptor;

    XoevCodeGDSAktentyp(String descriptor) {
        this.descriptor = descriptor;
    }
}
