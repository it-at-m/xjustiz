package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSStaatenTyp3 {

    DEUTSCHLAND("deutschland");

    private final String descriptor;

    XoevCodeGDSStaatenTyp3(final String descriptor) {
        this.descriptor = descriptor;
    }
}
