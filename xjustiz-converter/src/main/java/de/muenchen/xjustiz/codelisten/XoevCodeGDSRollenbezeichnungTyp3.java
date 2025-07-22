package de.muenchen.xjustiz.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSRollenbezeichnungTyp3 {

    BETROFFENER("betroffener"), ANTRAGSTELLER("antragsteller");

    private final String descriptor;

    XoevCodeGDSRollenbezeichnungTyp3(String descriptor) {
        this.descriptor = descriptor;
    }
}
