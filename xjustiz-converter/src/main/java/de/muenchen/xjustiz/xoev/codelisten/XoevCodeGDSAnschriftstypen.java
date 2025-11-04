package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSAnschriftstypen {

    TATORTANSCHRIFT("tatortanschrift"),
    DIENST_GESCHAEFTSANSCHRIFT("dienst-geschaeftsanschrift");

    private final String descriptor;

    XoevCodeGDSAnschriftstypen(final String descriptor) {
        this.descriptor = descriptor;
    }
}
