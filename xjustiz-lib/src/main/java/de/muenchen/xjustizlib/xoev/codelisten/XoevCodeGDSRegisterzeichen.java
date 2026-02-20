package de.muenchen.xjustizlib.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSRegisterzeichen {

    BUSSGELDVERFAHREN("bussgeldverfahren");

    private final String descriptor;

    XoevCodeGDSRegisterzeichen(final String descriptor) {
        this.descriptor = descriptor;
    }
}
