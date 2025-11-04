package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSEreignisTyp3 {

    NEUEINGANG_E_HAFT("neueingang-e-haft");

    private final String descriptor;

    XoevCodeGDSEreignisTyp3(final String descriptor) {
        this.descriptor = descriptor;
    }
}
