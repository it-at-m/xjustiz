package de.muenchen.xjustizlib.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSGerichteTyp3 {

    AMTSGERICHT_MUENCHEN("amtsgericht-muenchen");

    private final String descriptor;

    XoevCodeGDSGerichteTyp3(final String descriptor) {
        this.descriptor = descriptor;
    }

}
