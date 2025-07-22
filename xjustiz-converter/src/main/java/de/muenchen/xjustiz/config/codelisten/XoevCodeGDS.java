package de.muenchen.xjustiz.config.codelisten;

import lombok.Getter;

@Getter
public enum XoevCodeGDS {

        CODE_GDS_ANSCHRIFTSTYP("gds-anschriftstyp"),
        CODE_GDS_SACHGEBIET_TYP_3("gds-sachgebiet"),
        CODE_GDS_GERICHTE_TYP_3("gds-gerichte"),
        CODE_GDS_ROLLENBEZEICHNUNG_TYP_3("gds-rollenbezeichnung"),
        CODE_GDS_STAATEN_TYP_3("bjf-staat"),
        CODE_GDS_EREIGNIS_TYP_3("gds-ereignis"),
        GESCHLECHT("gds-geschlecht"),;

        private final String descriptor;

        XoevCodeGDS(String value) {
            this.descriptor = value;
        }
}
