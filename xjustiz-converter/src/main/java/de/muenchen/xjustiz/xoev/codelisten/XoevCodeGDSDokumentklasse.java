package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSDokumentklasse {

    ANTRAG("antrag-behoerde"),
    BESCHEID("bescheid");

    private final String descriptor;

    XoevCodeGDSDokumentklasse(String descriptor) {
        this.descriptor = descriptor;
    }
}
