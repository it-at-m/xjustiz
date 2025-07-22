package de.muenchen.xjustiz.xoev.codelisten;

import lombok.Getter;

@Getter
public enum XoevGeschlecht {

        UNBEKANNT("0"), MAENNLICH("1"), WEIBLICH("2"), DIVERS("3"), SAECHLICH("4");

    private final String descriptor;

    XoevGeschlecht(String descriptor) {
        this.descriptor = descriptor;
    }
}
