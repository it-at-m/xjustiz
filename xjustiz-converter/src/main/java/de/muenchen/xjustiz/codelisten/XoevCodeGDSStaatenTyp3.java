package de.muenchen.xjustiz.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSStaatenTyp3 {

    DEUTSCHLAND("deutschland");

    private final String descriptor;

    XoevCodeGDSStaatenTyp3(String descriptor) {
        this.descriptor = descriptor;
    }
}
