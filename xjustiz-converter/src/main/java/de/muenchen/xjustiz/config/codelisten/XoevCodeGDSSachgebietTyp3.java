package de.muenchen.xjustiz.config.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDSSachgebietTyp3 {

    OWI_SACHEN("owi-sachen");

    private final String descriptor;

    XoevCodeGDSSachgebietTyp3(String descriptor) {
        this.descriptor = descriptor;
    }


}
