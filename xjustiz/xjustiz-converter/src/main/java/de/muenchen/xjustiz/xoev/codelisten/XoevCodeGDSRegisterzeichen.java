package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSRegisterzeichen {

    BUSSGELDVERFAHREN("bussgeldverfahren");

    private final String descriptor;

    XoevCodeGDSRegisterzeichen(String descriptor) {
        this.descriptor = descriptor;
    }
}
